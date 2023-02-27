package de.zeltverleih.service;



import de.zeltverleih.model.datenbank.Ladepauschale;
import de.zeltverleih.repository.LadepauschaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LadepauschaleServiceImpl{

    @Autowired
    private LadepauschaleRepository ladepauschaleRepository;

    public Ladepauschale saveLadepauschale(Ladepauschale adresse) {
        return ladepauschaleRepository.save(adresse);
    }

    public List<Ladepauschale> getAllLadepauschalen() {
        return ladepauschaleRepository.findAll();
    }

    public void deleteLadepauschale(Ladepauschale adresse) {
        ladepauschaleRepository.delete(adresse);
    }

    public Optional<Ladepauschale> getById(int id) {
        return ladepauschaleRepository.findById(id);
    }
}
