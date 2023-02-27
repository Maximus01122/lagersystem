package de.zeltverleih.repository;

import de.zeltverleih.model.datenbank.Ladepauschale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LadepauschaleRepository extends JpaRepository<Ladepauschale,Integer> {
}

