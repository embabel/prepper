/*
 * Copyright 2024-2026 Embabel Pty Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.embabel.prepper.agent.agent;

import com.embabel.agent.api.common.Actor;
import com.embabel.agent.prompt.persona.RoleGoalBackstory;
import com.embabel.common.ai.model.LlmOptions;
import com.embabel.prepper.agent.PrepperConfig;
import org.junit.jupiter.api.Test;

class PrepperAgentTest {

    final LlmOptions llm = LlmOptions.withAutoLlm();

    final PrepperConfig config = new PrepperConfig(
            new Actor<>(new RoleGoalBackstory("researcher", "perform reseearch", "i am clever"), llm),
            new Actor<>(new RoleGoalBackstory("industry analyzer", "goal", "i am thorough"), llm),
            new Actor<>(new RoleGoalBackstory("meeting strategist", "goal", "i am strategic"), llm),
            new Actor<>(new RoleGoalBackstory("briefing writer", "goal", "i am eloquent"), llm),
            2
    );

    @Test
    void testPrepper() {
//        var context = FakeOperationContext.create();
//        var promptRunner = (FakePromptRunner) context.promptRunner();
//        context.expectResponse(new Domain.Participants(List.of()));
//
//        var agent = new PrepperAgent(config);
//        var meeting = new Domain.Meeting(
//                "context", "to rule the world", List.of("Tony Blair")
//        );
//        agent.researchParticipants(meeting, context);
//
//        String prompt = promptRunner.getLlmInvocations().getFirst().getPrompt();
//        assertTrue(prompt.contains(meeting.objective()), "Expected prompt to contain objective:\n" + prompt);
    }


}
