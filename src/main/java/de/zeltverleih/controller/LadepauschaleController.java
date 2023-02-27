package de.zeltverleih.controller;

import de.zeltverleih.model.datenbank.Ladepauschale;
import de.zeltverleih.service.LadepauschaleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/ladepauschale")
@CrossOrigin
public class LadepauschaleController {
    @Autowired
    private LadepauschaleServiceImpl ladepauschaleService;

    @PostMapping("add")
    public Ladepauschale add(@RequestBody Ladepauschale ladepauschale) {
        return ladepauschaleService.saveLadepauschale(ladepauschale);
    }


    @PutMapping("update")
    public Ladepauschale update(@RequestBody Ladepauschale ladepauschale) {
        getLadepauschale(ladepauschale.getId());
        return ladepauschaleService.saveLadepauschale(ladepauschale);
    }

    @GetMapping("get/{id}")
    public Ladepauschale getLadepauschale(@PathVariable int id) {
        Optional<Ladepauschale> ladepauschaleOptional = ladepauschaleService.getById(id);
        return ladepauschaleOptional.orElseThrow(() ->
               new IllegalArgumentException("Ladepauschale mit passender ID: "+ id + " nicht gefunden"));
    }

    @GetMapping("/getAll")
    public List<Ladepauschale> getAll(){
        return ladepauschaleService.getAllLadepauschalen();
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id){
        Ladepauschale ladepauschale = getLadepauschale(id);
        ladepauschaleService.deleteLadepauschale(ladepauschale);
    }
}
