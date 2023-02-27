package de.zeltverleih.repository;

import de.zeltverleih.model.datenbank.BuchungMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface BuchungMaterialRepository extends JpaRepository<BuchungMaterial, Integer> {
    @Query("select COALESCE(sum(b.anzahl),0) from BuchungMaterial b " +
            "where b.material.id = ?1 " +
            "and (b.buchung.startdatum between ?2 and ?3 " +
            "or b.buchung.enddatum between ?2 and ?3 " +
            "or ?2 between b.buchung.startdatum and b.buchung.enddatum)")
    int getOccupiedNumbers(int materialId, LocalDate startdatum, LocalDate enddatum);
}
