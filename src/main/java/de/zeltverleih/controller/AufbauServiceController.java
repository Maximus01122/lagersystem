package de.zeltverleih.controller;

import de.zeltverleih.model.datenbank.AufbauService;
import de.zeltverleih.service.AufbauServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/aufbauService")
@CrossOrigin
public class AufbauServiceController {

    @Autowired
    private AufbauServiceService AufbauServiceService;

    @PostMapping("add")
    public AufbauService add(@RequestBody AufbauService aufbauService) {
        return AufbauServiceService.saveService(aufbauService);
    }


    @PutMapping("update")
    public AufbauService update(@RequestBody AufbauService aufbauService) {
        getAufbauService(aufbauService.getId());
        return AufbauServiceService.saveService(aufbauService);
    }

    @GetMapping("get/{id}")
    public AufbauService getAufbauService(@PathVariable int id) {
        Optional<AufbauService> ladepauschaleOptional = AufbauServiceService.getById(id);
        return ladepauschaleOptional.orElseThrow(() ->
                new IllegalArgumentException("AufbauService mit passender ID: "+ id + " nicht gefunden"));
    }

    @GetMapping("/getAll")
    public List<AufbauService> getAll(){
        return AufbauServiceService.getAllService();
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id){
        AufbauService aufbauService = getAufbauService(id);
        AufbauServiceService.deleteService(aufbauService);
    }
}
