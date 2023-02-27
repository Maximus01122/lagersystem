package de.zeltverleih.service;

import de.zeltverleih.model.datenbank.AufbauService;
import de.zeltverleih.model.datenbank.Buchung;
import de.zeltverleih.model.datenbank.BuchungMaterial;
import de.zeltverleih.model.datenbank.Ladepauschale;
import de.zeltverleih.repository.BuchungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BuchungServiceImpl {

    @Autowired
    private BuchungRepository buchungRepository;

    public Buchung saveBuchung(Buchung buchung){
        buchung.checkAttribute();
        return buchungRepository.save(buchung);
    }

    public List<Buchung> getAllBuchung(){
        return buchungRepository.findAll();
    }

    public void deleteBuchung(Buchung buchung){
        buchungRepository.delete(buchung);
    }

    public Optional<Buchung> getById(int id){
        return buchungRepository.findById(id);
    }

    public List<BuchungMaterial> getMaterialienByBuchung_Id(int id){
        return buchungRepository.findMaterialienByBuchung_Id(id);
    }

    public Ladepauschale getLadepauschale(int id) {
        return buchungRepository.getLadepauschale(id);
    }

    public List<AufbauService> getAufbauService(int id) {
        return buchungRepository.getAufbauService(id);
    }
}
