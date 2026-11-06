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
