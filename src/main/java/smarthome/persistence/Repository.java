package smarthome.persistence;

import smarthome.domain.AggregateRoot;
import smarthome.domain.DomainID;

public interface Repository<ID extends DomainID, T extends AggregateRoot> {

    /**
     * Saves a domainEntity unto the repository implementation
     * @return True or False
     */
    boolean save(T entity);

    /**
     * Finds all entities saved unto the repository;
     * @return Iterable.
     */
    Iterable<T> findAll();

    /**
     * Finds Entity by IDVO
     * @param id IDVO
     * @return Entity matching the inserted IDVO
     */
    T findById(ID id);

    /**
     * Verifies if an entity is present in the repository, queries by ID;
     * @param id IDVO
     * @return True or False
     */
    boolean isPresent(ID id);
}
