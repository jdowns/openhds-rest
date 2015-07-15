package org.openhds.service.impl.census;

import org.openhds.domain.model.census.Relationship;
import org.openhds.service.AuditableCollectedServiceTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;

/**
 * Created by Wolfe on 7/13/2015.
 */
public class RelationshipServiceTest extends AuditableCollectedServiceTest<Relationship, RelationshipService> {

    @Autowired
    private IndividualService individualService;

    @Override
    protected Relationship makeInvalidEntity() {
        return new Relationship();
    }

    @Override
    protected Relationship makeValidEntity(String name, String id) {
        Relationship relationship = new Relationship();
        relationship.setUuid(id);
        relationship.setRelationshipType(name);
        relationship.setStartDate(ZonedDateTime.now().minusYears(1));
        relationship.setIndividualA(individualService.findAll(UUID_SORT).toList().get(0));
        relationship.setIndividualB(individualService.findAll(UUID_SORT).toList().get(0));

        initCollectedFields(relationship);

        return relationship;
    }

    @Override
    @Autowired
    protected void initialize(RelationshipService service) {
        this.service = service;
    }
}
