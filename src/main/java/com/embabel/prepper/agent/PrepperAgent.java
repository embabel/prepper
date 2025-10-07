package com.embabel.prepper.agent;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.annotation.Export;
import com.embabel.agent.api.common.Ai;
import com.embabel.agent.api.common.OperationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;


@Agent(description = "A meeting prepper agent that helps users prepare for meetings ")
public class PrepperAgent {

    private final static Logger logger = LoggerFactory.getLogger(PrepperAgent.class);
    
    private final PrepperConfig config;
    private final ContactService contactService;

    public PrepperAgent(PrepperConfig config, ContactService contactService) {
        this.config = config;
        this.contactService = contactService;
        logger.info("Initialized PrepperAgent with config: {}", config);
    }
    
    public PrepperConfig config() {
        return config;
    }
    
    public ContactService contactService() {
        return contactService;
    }

    @Transactional
    @Action
    public Domain.Participants researchParticipants(Domain.Meeting meeting, OperationContext embabel) {
        var researcher = config.researcher()
                .promptRunner(embabel)
                .creating(Domain.NewContact.class);
        var contacts = embabel.parallelMap(
                meeting.participants(),
                config.maxConcurrency(),
                participant ->
                        contactService.resolveContact(participant)
                                .orElseGet(() -> {
                                    var newContact = researcher.fromPrompt("""
                                            Conduct comprehensive research on this individual and company
                                            involved in the upcoming meeting. Gather information on recent
                                            news, achievements, professional background, and any relevant
                                            business activities.
                                            
                                            Do your best to populate email address.
                                            
                                            Participant: %s
                                            %s
                                            """.formatted(participant, meeting.purpose()));
                                    return contactService.createContact(newContact);
                                })
        );
        return new Domain.Participants(contacts);
    }

    @Action
    public Domain.IndustryAnalysis analyzeIndustry(Domain.Meeting meeting, Domain.Participants participants, Ai ai) {
        return config.industryAnalyzer()
                .promptRunner(ai)
                .createObject("""
                                Analyze the current industry trends, challenges, and opportunities
                                relevant to the meeting's context. Consider market reports, recent
                                developments, and expert opinions to provide a comprehensive
                                overview of the industry landscape.
                                
                                Identify major trends, potential
                                challenges, and strategic opportunities.
                                
                                Participants: %s
                                %s
                                """.formatted(participants.contribution(), meeting.purpose()),
                        Domain.IndustryAnalysis.class);
    }

    @Action
    public Domain.MeetingStrategy formulateMeetingStrategy(
            Domain.Meeting meeting,
            Domain.Participants participants,
            Domain.IndustryAnalysis industryAnalysis,
            Ai ai) {
        return config.meetingStrategist()
                .promptRunner(ai)
                .createObject("""
                                Develop strategic talking points, questions, and discussion angles
                                for the meeting based on the research and industry analysis conducted
                                
                                Participants: %s
                                
                                %s),
                                """.formatted(participants.contribution(), meeting.purpose()),
                        Domain.MeetingStrategy.class);
    }

    @Action
    @AchievesGoal(description = "Produce a briefing for the meeting",
            export = @Export(remote = true, startingInputTypes = {Domain.Meeting.class}))
    public Domain.Briefing produceBriefing(
            Domain.Meeting meeting,
            Domain.Participants participants,
            Domain.IndustryAnalysis industryAnalysis,
            Domain.MeetingStrategy meetingStrategy,
            Ai ai) {
        var briefing = config.briefingWriter()
                .promptRunner(ai)
                .generateText("""
                        Compile all the information given into a briefing for the meeting
                        Consolidate research, analysis, and strategic insights.
                        
                        %s
                        Participants: %s
                        """.formatted(meeting.purpose(), participants.contribution()
                ));
        return new Domain.Briefing(
                meeting,
                participants,
                industryAnalysis,
                meetingStrategy,
                briefing
        );
    }
}
