package de.zeltverleih.repository;

import de.zeltverleih.model.datenbank.AufbauService;
import de.zeltverleih.model.datenbank.Buchung;
import de.zeltverleih.model.datenbank.BuchungMaterial;
import de.zeltverleih.model.datenbank.Ladepauschale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface BuchungRepository extends JpaRepository<Buchung, Integer> {
    @Query("select b from Buchung b where b.startdatum between ?1 and ?2")
    List<Buchung> findByStartdatumBetween(Date startdatumStart, Date startdatumEnd);

    @Query("select b from BuchungMaterial b where b.buchung.id = ?1")
    List<BuchungMaterial> findMaterialienByBuchung_Id(int id);

    @Query("SELECT b FROM Buchung b " +
            "where b.startdatum between ?1 and ?2 ")
    List<Buchung> findByDatum(LocalDate start, LocalDate ende);

    @Query("SELECT bl.ladepauschale FROM BuchungLadepauschale bl " +
            "where bl.buchung.id = ?1 ")
    Ladepauschale getLadepauschale(int id);

    @Query("SELECT ba.aufbauService FROM BuchungAufbauService ba " +
            "where ba.buchung.id = ?1")
    List<AufbauService> getAufbauService(int id);
}