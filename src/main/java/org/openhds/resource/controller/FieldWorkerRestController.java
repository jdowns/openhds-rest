package org.openhds.resource.controller;

import org.openhds.domain.model.FieldWorker;

import org.openhds.repository.queries.QueryValue;
import org.openhds.resource.contract.AuditableRestController;
import org.openhds.resource.registration.FieldWorkerRegistration;
import org.openhds.service.impl.FieldWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Ben on 5/18/15.
 */
@RestController
@RequestMapping("/fieldWorkers")
@ExposesResourceFor(FieldWorker.class)
public class FieldWorkerRestController extends AuditableRestController<
        FieldWorker,
        FieldWorkerRegistration,
        FieldWorkerService> {

    private final FieldWorkerService fieldWorkerService;

    @Autowired
    public FieldWorkerRestController(FieldWorkerService fieldWorkerService) {
        super(fieldWorkerService);
        this.fieldWorkerService = fieldWorkerService;
    }

    @Override
    protected FieldWorkerRegistration makeSampleRegistration(FieldWorker entity) {
        FieldWorkerRegistration registration = new FieldWorkerRegistration();
        registration.setFieldWorker(entity);
        registration.setPassword("password");
        return registration;
    }

    @Override
    protected FieldWorker register(FieldWorkerRegistration registration) {
        return fieldWorkerService.recordFieldWorker(registration.getFieldWorker(), registration.getPassword());
    }

    @Override
    protected FieldWorker register(FieldWorkerRegistration registration, String id) {
        registration.getFieldWorker().setUuid(id);
        return register(registration);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public List<FieldWorker> search(@RequestParam Map<String, String> fields) {
        List<QueryValue> collect = fields.entrySet().stream().map(f -> new QueryValue(f.getKey(), f.getValue())).collect(Collectors.toList());
        QueryValue[] queryFields = {};
        queryFields = collect.toArray(queryFields);
        return fieldWorkerService.findByMultipleValues(new Sort("firstName"), queryFields).toList();
    }
}
