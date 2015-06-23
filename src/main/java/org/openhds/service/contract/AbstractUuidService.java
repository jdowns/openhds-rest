package org.openhds.service.contract;

import org.openhds.domain.contract.UuidIdentifiable;
import org.openhds.repository.UuidIdentifiableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Created by wolfe on 6/11/15.
 */
public abstract class AbstractUuidService <T extends UuidIdentifiable, V extends UuidIdentifiableRepository<T>>{

    protected final V repository;

    public AbstractUuidService(V repository) {
        this.repository = repository;
    }

}
