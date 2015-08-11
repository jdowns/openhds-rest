package org.openhds.service.impl.census;

import org.openhds.domain.model.census.Individual;
import org.openhds.errors.model.ErrorLog;
import org.openhds.repository.concrete.census.IndividualRepository;
import org.openhds.service.contract.AbstractAuditableExtIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

/**
 * Created by Wolfe on 7/13/2015.
 */
@Service
public class IndividualService extends AbstractAuditableExtIdService<Individual, IndividualRepository>{

    @Autowired
    public IndividualService(IndividualRepository repository) {
        super(repository);
    }

    @Override
    public Individual makePlaceHolder(String id, String name) {
        Individual individual = new Individual();
        individual.setUuid(id);
        individual.setFirstName(name);
        individual.setExtId(name);

        initPlaceHolderCollectedFields(individual);

        return individual;
    }

    public Individual recordIndividual(Individual individual, String fieldWorkerId) {
        individual.setCollectedBy(fieldWorkerService.findOrMakePlaceHolder(fieldWorkerId));

        //TODO: Handle side effect creation for things like socialgroup and membership etc.

        return createOrUpdate(individual);
    }

  @Override
  public void validate(Individual entity, ErrorLog errorLog) {
    super.validate(entity, errorLog);

  }
}
