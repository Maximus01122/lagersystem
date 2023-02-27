package de.zeltverleih.repository;

import de.zeltverleih.model.datenbank.Kunde;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface KundenRepository extends JpaRepository<Kunde,Integer> {
    @Query("SELECT k from Kunde k where k.name like ?1%")
    List<Kunde> findByName(String name);
    Optional<Kunde> findByEmail(String email);
    List<Kunde> findByTelefonnummer(String telefonnummer);
    Optional<Kunde> findById(int id);
    boolean existsByEmail(String email);
}

