package de.zeltverleih.service;

import de.zeltverleih.model.KonstantenMaterialien;
import de.zeltverleih.model.datenbank.Kategorie;
import de.zeltverleih.model.datenbank.Material;
import de.zeltverleih.repository.BuchungMaterialRepository;
import de.zeltverleih.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MaterialServiceImpl {
    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private BuchungMaterialRepository buchungMaterialRepository;

    public Material saveMaterial(Material material) {
        material.checkAttribute();
        return materialRepository.save(material);
    }

    public List<Material> getAllMaterialien() {
        return materialRepository.findAll();
    }

    public void deleteMaterial(Material material) {
        materialRepository.delete(material);
    }

    public Optional<Material> getMaterialById(int id) {
        return materialRepository.findById(id);
    }

    public Material getByName(String name) {
        return materialRepository.findByName(name).orElseThrow(()->
                new RuntimeException("Kein Material mit dem Namen \"" + name + "\" gefunden"));
    }

    public List<Kategorie> getMaterialKategorien() {
        return materialRepository.getAllKategorien();
    }

    public List<Material> getByKategorie(String kategorie) {
        return materialRepository.getByKategorie(kategorie);
    }

    public Material getTisch(){return getByName(KonstantenMaterialien.getTischName());}
    public Material getBank(){return getByName(KonstantenMaterialien.getBankName());}
    public Material getGarnitur(){return getByName(KonstantenMaterialien.getGarniturName());}

    public List<Material> getBestuhlung(){
        return List.of(getTisch(), getBank(), getGarnitur());
    }

    public List<Material> getConnectedZelte(){
        return KonstantenMaterialien.getConnectedZelte().stream().map(this::getByName).toList();
    }

}
