package de.zeltverleih.service;

import de.zeltverleih.model.datenbank.Adresse;
import de.zeltverleih.model.datenbank.Kategorie;
import de.zeltverleih.repository.AdresseRepository;
import de.zeltverleih.repository.KategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KategorieServiceImpl {

    @Autowired
    private KategorieRepository kategorieRepository;

    public Kategorie saveKategorie(Kategorie kategorie) {
        return kategorieRepository.save(kategorie);
    }

    public List<Kategorie> getAllKategorie() {
        return kategorieRepository.findAll();
    }

    public void deleteKategorie(Kategorie kategorie) {
        kategorieRepository.delete(kategorie);
    }

    public Optional<Kategorie> getById(int id) {
        return kategorieRepository.findById(id);
    }
}
