package de.zeltverleih.controller;

import de.zeltverleih.model.datenbank.Kategorie;
import de.zeltverleih.model.datenbank.Material;
import de.zeltverleih.service.BuchungMaterialServiceImpl;
import de.zeltverleih.service.MaterialServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/material")
public class MaterialController {
    @Autowired
    private MaterialServiceImpl materialService;

    @Autowired
    private BuchungMaterialServiceImpl buchungMaterialService;

    @GetMapping("/getKategorien")
    public List<Kategorie> getAllKategorien(){
        return materialService.getMaterialKategorien();
    }

    @GetMapping("/getByKategorie/{kategorie}")
    public List<Material> getKategorie(@PathVariable String kategorie){
        return materialService.getByKategorie(kategorie);
    }

    @PostMapping("/add")
    public Material add(@RequestBody Material material) {
        try{
            getByName(material.getName());
            throw new IllegalStateException("Name für Material bereits vergeben");
        }
        catch (RuntimeException ex){
            return materialService.saveMaterial(material);
        }
    }

    @PostMapping("addAll")
    public List<Material> add(@RequestBody List<Material> materialList) {
        List<Material> mList = new ArrayList<>();
        for(Material m: materialList){
            mList.add(materialService.saveMaterial(m));
        }
        return mList;
    }

    @PostMapping("/getByName/{name}")
    public Material getByName(@RequestBody String name){
        return materialService.getByName(name);
    }

    @PutMapping("update")
    public Material update(@RequestBody Material material) {
        getMaterial(material.getId());
        return materialService.saveMaterial(material);
    }

    @GetMapping("get/{id}")
    public Material getMaterial(@PathVariable int id){
        Optional<Material> materialOptional = materialService.getMaterialById(id);
        return materialOptional.orElseThrow(() ->
                new IllegalArgumentException("Material mit passender ID: "+ id + " nicht gefunden"));
    }

    @GetMapping("getAll")
    public List<Material> getAll(){
        return materialService.getAllMaterialien();
    }

    @DeleteMapping("/deleteAll")
    public void deleteAll(){
        for (Material m: getAll()) {
            materialService.deleteMaterial(m);
        }
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id){
        Material material = getMaterial(id);
        materialService.deleteMaterial(material);
    }
}
