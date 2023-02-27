package de.zeltverleih.service;

import de.zeltverleih.model.datenbank.Kunde;
import de.zeltverleih.repository.KundenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KundenServiceImpl implements KundenService {
    @Autowired
    private KundenRepository kundenRepository;

    @Override
    public Kunde saveKunde(Kunde kunde) {
        //kunde.checkAttribute();
        return kundenRepository.save(kunde);
    }

    @Override
    public List<Kunde> getAllKunden() {
        return kundenRepository.findAll();
    }

    @Override
    public Kunde getKundeByEmail(String email) {
        return kundenRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Teilnehmer mit dieser Email nicht vorhanden"));
    }

    public Optional<Kunde> getOptKundeByEmail(String email) {
        return kundenRepository.findByEmail(email);
    }

    @Override
    public List<Kunde> getKundeByName(String name) {
        return kundenRepository.findByName(name);
    }

    @Override
    public void deleteKunde(Kunde kunde) {
        kundenRepository.delete(kunde);
    }

    @Override
    public Optional<Kunde> getKundeById(int id) {
        return kundenRepository.findById(id);
    }
}
