package org.openhds.service.impl;


import org.openhds.domain.model.Location;
import org.openhds.service.AuditableExtIdServiceTest;
import org.openhds.service.impl.LocationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;

/**
 * Created by wolfe on 6/17/15.
 */
public class LocationServiceTest extends AuditableExtIdServiceTest<Location, LocationService> {

    @Autowired
    FieldWorkerService fieldWorkerService;

    @Autowired
    LocationHierarchyService locationHierarchyService;

    @Override
    protected Location makeInvalidEntity() {
        return new Location();
    }

    @Override
    protected Location makeValidEntity(String name, String id) {
        Location location = new Location();
        location.setUuid(id);
        location.setName(name);
        location.setExtId(name);
        location.setCollectedBy(fieldWorkerService.findAll(null).toList().get(0));
        location.setLocationHierarchy(locationHierarchyService.findAll(null).toList().get(0));
        return location;
    }

    @Override
    @Autowired
    protected void initialize(LocationService service) {
        this.service = service;
    }

}