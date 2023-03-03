package de.zeltverleih.repository;

import de.zeltverleih.model.datenbank.Kategorie;
import de.zeltverleih.model.datenbank.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaterialRepository extends JpaRepository<Material,Integer> {
    Optional<Material> findByName(String name);
    List<Material> findByAnzahl(int anzahl);

    @Query("select k from Kategorie k")
    List<Kategorie> getAllKategorien();

    @Query("select m from Material m where m.kategorie.name = ?1")
    List<Material> getByKategorie(String kategorie);
}

