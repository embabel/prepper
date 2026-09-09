package com.embabel.prepper.agent;

import com.embabel.agent.api.common.Actor;
import com.embabel.agent.prompt.persona.RoleGoalBackstory;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;


@ConfigurationProperties(prefix = "prepper")
@Validated
public record PrepperConfig(
        @NotNull Actor<RoleGoalBackstory> researcher,
        @NotNull Actor<RoleGoalBackstory> industryAnalyzer,
        @NotNull Actor<RoleGoalBackstory> meetingStrategist,
        @NotNull Actor<RoleGoalBackstory> briefingWriter,
        @DefaultValue("8") int maxConcurrency
) {
}
