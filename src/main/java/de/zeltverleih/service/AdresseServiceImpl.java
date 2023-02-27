package de.zeltverleih.service;



import de.zeltverleih.model.datenbank.Adresse;
import de.zeltverleih.repository.AdresseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdresseServiceImpl implements AdresseService{

    @Autowired
    private AdresseRepository adresseRepository;
    @Override
    public Adresse saveAdresse(Adresse adresse) {
        adresse.checkAttribute();
        return adresseRepository.save(adresse);
    }

    @Override
    public List<Adresse> getAllAdressen() {
        return adresseRepository.findAll();
    }

    @Override
    public void deleteAdresse(Adresse adresse) {
        adresseRepository.delete(adresse);
    }

    @Override
    public List<Adresse> getById(int id) {
        return adresseRepository.findById(id);
    }

    @Override
    public List<Adresse> getByPlz(String plz) {
        return adresseRepository.findByPlz(plz);
    }

    @Override
    public List<Adresse> getByOrt(String ort) {
        return adresseRepository.findByOrt(ort);
    }

    @Override
    public List<Adresse> getByStrasse(String strasse) {
        return adresseRepository.findByStrasse(strasse);
    }

    @Override
    public Optional<Adresse> getAdresseById(int id) {
        return adresseRepository.findById(Integer.valueOf(id));
    }

}
