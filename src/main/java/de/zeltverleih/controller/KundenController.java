package de.zeltverleih.controller;

import de.zeltverleih.model.datenbank.Kunde;
import de.zeltverleih.service.BuchungServiceImpl;
import de.zeltverleih.service.KundenService;
import de.zeltverleih.service.excel.ExcelReiheBuchung;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/kunde")
@CrossOrigin
public class KundenController {
    @Autowired
    private KundenService kundenService;

    @Autowired
    private ExcelReiheBuchung excelReihe;

    @Autowired
    private BuchungServiceImpl buchungService;


    @PostMapping("add")
    public Kunde add(@RequestBody Kunde kunde) {
        return kundenService.saveKunde(kunde);
    }


    @PutMapping("update")
    public Kunde update(@RequestBody Kunde kunde) {
        getKunde(kunde.getId());
        return kundenService.saveKunde(kunde);
    }

    @GetMapping("get/{id}")
    public Kunde getKunde(@PathVariable int id) {
        Optional<Kunde> kundeOptional = kundenService.getKundeById(id);
        return kundeOptional.orElseThrow(() ->
                new IllegalArgumentException("Kunde mit passender ID: "+ id + " nicht gefunden"));
    }

    @GetMapping("/getAll")
    public List<Kunde> getAll(){
        return kundenService.getAllKunden();
    }


    @DeleteMapping("/deleteAll")
    public void deleteAll(){
        for (Kunde k: getAll()) {
            kundenService.deleteKunde(k);
        }
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id){
        Kunde kunde = getKunde(id);
        kundenService.deleteKunde(kunde);
    }

    @GetMapping("/getByName/{name}")
    public List<Kunde> getKundeByName(@PathVariable String name) {
        System.out.println(kundenService.getKundeByName(name));
        return kundenService.getKundeByName(name);
    }

}
