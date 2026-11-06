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

import com.embabel.common.ai.prompt.PromptContributor;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.List;

public abstract class Domain {

    public record Meeting(
            String context,
            String objective,
            @JsonPropertyDescription("A list of participant email addresses or however else we identify them, e.g. 'Roger Daltrey The Who' or 'roger@who.com'")
            List<String> participants
    ) {

        @NonNull
        public String purpose() {
            return "Meeting:\nContext: %s\nObjective: %s\n".formatted(context, objective);
        }
    }

    public record NewContact(
            String name,
            @Nullable String email,
            String writeup
    ) {
    }

    /**
     * Fleshed out participant
     */
    @Entity
    @EntityListeners(AuditingEntityListener.class)
    public static class Contact implements PromptContributor {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String name;

        @Column
        private String email;

        @Column(columnDefinition = "TEXT")
        private String writeup;

        @CreatedDate
        private LocalDateTime createdAt;

        @LastModifiedDate
        private LocalDateTime updatedAt;

        protected Contact() {
        }

        public Contact(NewContact newContact) {
            this.name = newContact.name;
            this.email = newContact.email;
            this.writeup = newContact.writeup;
        }

        @NotNull
        @Override
        public String contribution() {
            return "- %s: %s".formatted(email, writeup);
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getWriteup() {
            return writeup;
        }

        public void setWriteup(String writeup) {
            this.writeup = writeup;
        }

        public String getName() {
            return name;
        }

        public String email() {
            return email;
        }

        public String writeup() {
            return writeup;
        }

        @Override
        public String toString() {
            return "Contact{id=%d, name='%s', email='%s', writeup:\n%s}".formatted(id, name, email, writeup);
        }
    }

    public record Participants(
            List<Contact> participants
    ) implements PromptContributor {

        @NotNull
        @Override
        public String contribution() {
            return participants.stream()
                    .map(Contact::contribution)
                    .collect(java.util.stream.Collectors.joining("\n"));
        }
    }

    public record IndustryAnalysis(
            String analysis
    ) {
    }

    public record MeetingStrategy(
            @JsonPropertyDescription("Complete report with a list of key talking points and strategic questions" +
                    " to ask, to help achieve the meeting's objective")
            String strategy
    ) {
    }

    public record Briefing(
            Meeting meeting,
            Participants participants,
            IndustryAnalysis industryAnalysis,
            MeetingStrategy meetingStrategy,
            String briefing
    ) {
    }

}
