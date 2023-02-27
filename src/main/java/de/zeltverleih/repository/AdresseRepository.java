package de.zeltverleih.repository;

import de.zeltverleih.model.datenbank.Adresse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdresseRepository extends JpaRepository<Adresse,Integer> {
    List<Adresse> findById(int id);
    List<Adresse> findByPlz(String plz);
    List<Adresse> findByOrt(String ort);
    List<Adresse> findByStrasse(String strasse);
}

