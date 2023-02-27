package de.zeltverleih.service;


import de.zeltverleih.model.datenbank.Kunde;

import java.util.List;
import java.util.Optional;

public interface KundenService {
    Kunde saveKunde(Kunde kunde);
    List<Kunde> getAllKunden();
    Kunde getKundeByEmail(String email);
    List<Kunde> getKundeByName(String vorname);
    void deleteKunde(Kunde kunde);
    Optional<Kunde> getKundeById(int id);

    Optional<Kunde> getOptKundeByEmail(String email);
}
