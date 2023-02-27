package de.zeltverleih.controller;

import de.zeltverleih.model.datenbank.Adresse;
import de.zeltverleih.service.AdresseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/adresse")
@CrossOrigin
public class AdresseController {
    @Autowired
    private AdresseService adresseService;

    public Adresse add(@RequestBody Adresse adresse) throws Exception {
        return adresseService.saveAdresse(adresse);
    }

    public Adresse getAdresse(@PathVariable int id){
        Optional<Adresse> adresseOptional = adresseService.getAdresseById(id);
        return adresseOptional.orElseThrow(
                () -> new IllegalArgumentException("Adresse mit passender ID: "+ id + " nicht gefunden"));
    }

    public Adresse update(@RequestBody Adresse adresse){
        getAdresse(adresse.getId());
        return adresseService.saveAdresse(adresse);
    }

    public List<Adresse> getAll(){
        return adresseService.getAllAdressen();
    }

    public void deleteAll(){
        for (Adresse adresse: getAll()) {
            adresseService.deleteAdresse(adresse);
        }
    }

    public void delete(@PathVariable int id){
        Adresse adresse = getAdresse(id);
        adresseService.deleteAdresse(adresse);
    }
}
