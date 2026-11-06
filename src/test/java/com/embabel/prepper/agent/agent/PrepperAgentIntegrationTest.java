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

import com.embabel.agent.test.integration.EmbabelMockitoIntegrationTest;
import org.junit.jupiter.api.BeforeAll;

/**
 * Use framework superclass to test the complete workflow of writing and reviewing a story.
 * This will run under Spring Boot against an AgentPlatform instance
 * that has loaded all our agents.
 */
class PrepperAgentIntegrationTest extends EmbabelMockitoIntegrationTest {

    @BeforeAll
    static void setUp() {
        // Set shell configuration to non-interactive mode
        System.setProperty("embabel.agent.shell.interactive.enabled", "false");
    }

//    @Test
//    void shouldExecuteCompleteWorkflow() {
//        var input = new UserInput("Write about artificial intelligence");
//
//
//        whenCreateObject(prompt -> prompt.contains("Craft a short story"), WriteAndReviewAgent.Story.class)
//                .thenReturn(story);
//
//        // The second call uses generateText
//        whenGenerateText(prompt -> prompt.contains("You will be given a short story to review"))
//                .thenReturn(reviewedStory.review());
//
//        var invocation = AgentInvocation.create(agentPlatform, WriteAndReviewAgent.ReviewedStory.class);
//        var reviewedStoryResult = invocation.invoke(input);
//
//        assertNotNull(reviewedStoryResult);
//        assertTrue(reviewedStoryResult.getContent().contains(story.text()),
//                "Expected story content to be present: " + reviewedStoryResult.getContent());
//        assertEquals(reviewedStory, reviewedStoryResult,
//                "Expected review to match: " + reviewedStoryResult);
//
//        verifyCreateObjectMatching(prompt -> prompt.contains("Craft a short story"), WriteAndReviewAgent.Story.class,
//                llm -> llm.getLlm().getTemperature() == 0.7 && llm.getToolGroups().isEmpty());
//        verifyGenerateTextMatching(prompt -> prompt.contains("You will be given a short story to review"));
//        verifyNoMoreInteractions();
//    }
}
