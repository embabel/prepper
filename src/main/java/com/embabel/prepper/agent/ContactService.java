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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ContactService {

    private final Logger logger = LoggerFactory.getLogger(ContactService.class);
    private final ResearchedParticipantRepository repository;

    public ContactService(ResearchedParticipantRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Optional<Domain.Contact> resolveContact(String identification) {
        // TODO real implementation might use others means of identification,
        // including full text search, phone number, etc,
        // but we rely on email
        var found = repository.findByEmail(identification);
        logger.info("Resolved contact for {}: {}", identification, found);
        return found;
    }

    @Transactional
    public Domain.Contact createContact(Domain.NewContact newContact) {
        var saved = repository.save(new Domain.Contact(newContact));
        logger.info("Created new contact: {}", saved);
        return saved;
    }
}
