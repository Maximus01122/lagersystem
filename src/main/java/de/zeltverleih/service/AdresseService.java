package de.zeltverleih.service;


import de.zeltverleih.model.datenbank.Adresse;

import java.util.List;
import java.util.Optional;

public interface AdresseService {
    Adresse saveAdresse(Adresse adresse);
    List<Adresse> getAllAdressen();
    void deleteAdresse(Adresse adresse);
    List<Adresse> getById(int id);
    List<Adresse> getByPlz(String plz);
    List<Adresse> getByOrt(String ort);
    List<Adresse> getByStrasse(String strasse);
    Optional<Adresse> getAdresseById(int id);
}
