package de.zeltverleih.repository;

import de.zeltverleih.model.datenbank.Kategorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AufbauServiceRepository extends JpaRepository<Kategorie,Integer> {
}

