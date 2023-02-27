package de.zeltverleih.repository;

import de.zeltverleih.model.datenbank.Kategorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KategorieRepository extends JpaRepository<Kategorie,Integer> {
}

