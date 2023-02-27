package de.zeltverleih.repository;

import de.zeltverleih.model.datenbank.AufbauService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<AufbauService,Integer> {
}

