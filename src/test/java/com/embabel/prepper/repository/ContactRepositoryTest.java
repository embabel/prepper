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
package com.embabel.prepper.repository;

import com.embabel.prepper.agent.Domain;
import com.embabel.prepper.agent.ResearchedParticipantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ContactRepositoryTest {

    @Autowired
    private ResearchedParticipantRepository repository;

    @Test
    void shouldSaveAndFindResearchedParticipant() {
        Domain.Contact participant = new Domain.Contact(
                new Domain.NewContact("Test", "Participant", "Tester")
        );
        participant.setEmail("test@example.com");
        participant.setWriteup("Test participant writeup");

        Domain.Contact saved = repository.save(participant);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getEmail()).isEqualTo("test@example.com");
        assertThat(saved.getWriteup()).isEqualTo("Test participant writeup");
    }

    @Test
    void shouldFindByEmail() {
        Domain.Contact participant = new Domain.Contact(new Domain.NewContact("x", "y", ""));
        participant.setEmail("find@example.com");
        participant.setWriteup("Findable participant");
        repository.save(participant);

        assertThat(repository.findByEmail("find@example.com")).isPresent();
        assertThat(repository.findByEmail("notfound@example.com")).isEmpty();
    }

    @Test
    void shouldCheckExistsByEmail() {
        Domain.Contact participant = new Domain.Contact(
                new Domain.NewContact("Exists", "Participant", "Tester")
        );
        participant.setEmail("exists@example.com");
        participant.setWriteup("Existing participant");
        repository.save(participant);

        assertThat(repository.existsByEmail("exists@example.com")).isTrue();
        assertThat(repository.existsByEmail("notexists@example.com")).isFalse();
    }
}
