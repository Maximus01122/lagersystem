package de.zeltverleih.service;

import de.zeltverleih.model.datenbank.BuchungMaterial;
import de.zeltverleih.model.KonstantenMaterialien;
import de.zeltverleih.model.datenbank.Material;
import de.zeltverleih.repository.BuchungMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class BuchungMaterialServiceImpl {

    @Autowired
    private BuchungMaterialRepository buchungMaterialRepository;

    @Autowired
    private MaterialServiceImpl materialService;

    @Autowired
    private BuchungServiceImpl buchungService;


    public BuchungMaterial saveBuchungMaterial(BuchungMaterial bm){
        bm.checkAttribute();
        return buchungMaterialRepository.save(bm);
    }
    public List<BuchungMaterial> getAllBuchungMaterial(){
        return buchungMaterialRepository.findAll();
    }


    public void deleteBuchungMaterial(BuchungMaterial buchungMaterial) {
        buchungMaterialRepository.delete(buchungMaterial);
    }

    /**Bestimmt für übergebenes Material den Anzahl an verfügbaren Stücken im angegebenen Zeitraum.
     * Dabei gibt es für Garnituren und das 4x6 und 4x10 Zelt eine gesonderte Berechnung.**/
    public int getFreeNumbers(Material material, LocalDate startdatum, LocalDate enddatum){
        boolean isBestuhlung = KonstantenMaterialien.getBestuhlung().contains(material.getName());
        boolean isZelt = KonstantenMaterialien.getConnectedZelte().contains(material.getName());
        if (isBestuhlung){
            return getFreePlacesOfBestuhlung(material, startdatum, enddatum);
        }
        else if (isZelt){
            return getFreeNumbersOf4x6And4x10(startdatum, enddatum);
        }
        else return getFreeNumbers(material.getId(), startdatum, enddatum);
    }

    public int getFreeNumbersWithoutSelf(Material material, int buchungsId, LocalDate startdatum, LocalDate enddatum){
        List<BuchungMaterial> bmList = buchungService.getMaterialienByBuchung_Id(buchungsId);
        List<Material> mList = bmList.stream().map(BuchungMaterial::getMaterial).toList();
        if (mList.contains(material)){
            List<BuchungMaterial> bmGesucht = bmList.stream().filter(bm -> bm.getMaterial().equals(material)).toList();
            return getFreeNumbers(material, startdatum, enddatum) + bmGesucht.get(0).getAnzahl();
        }
        else return getFreeNumbers(material, startdatum, enddatum);
    }

    private int getFreeNumbers(int materialId, LocalDate startdatum, LocalDate enddatum){
        Material m = getElseThrow(materialId);
        int besetzt = getOccupiedNumbers(materialId,startdatum, enddatum);
        return m.getAnzahl() - besetzt;
    }

    public int getFreeNumbersOf4x6And4x10 (LocalDate startdatum, LocalDate enddatum){
        Material Zelt4x6 = materialService.getByName(KonstantenMaterialien.getZelt4x6Name());
        Material Zelt4x10 = materialService.getByName(KonstantenMaterialien.getZelt4x10Name());
        int frei4x6 = getFreeNumbers(Zelt4x6.getId(), startdatum, enddatum);
        int frei4x10 = getFreeNumbers(Zelt4x10.getId(), startdatum, enddatum);

        return Math.min(frei4x6,frei4x10);
    }

    private Material getElseThrow(int materialId){
        return materialService.getMaterialById(materialId).orElseThrow(() ->
                new RuntimeException("Kein Material mit der ID: " + materialId + " gefunden"));
    }

    public int getOccupiedNumbers(int materialId, LocalDate startdatum, LocalDate enddatum){
        Material m = getElseThrow(materialId);
        return Math.min(buchungMaterialRepository.getOccupiedNumbers(materialId,startdatum, enddatum), m.getAnzahl());
    }

    private int getFreePlacesOfBestuhlung(Material material, LocalDate startdatum, LocalDate enddatum) {

        List<Material> bestuhlungsList = new ArrayList<>();
        KonstantenMaterialien.getBestuhlung().forEach(
                name -> bestuhlungsList.add(materialService.getByName(name))
        );

        Map<Material, Integer> freeMap = getFreeForMaterialList(bestuhlungsList, startdatum, enddatum);

        Material Garnituren = materialService.getByName(KonstantenMaterialien.getGarniturName());
        int besetzeGarnituren = getOccupiedNumbers(Garnituren.getId(), startdatum, enddatum);
        int freiTische = 0;
        int freiBaenke = 0;

        for (Material m: bestuhlungsList){
            if(m.getName().equals(KonstantenMaterialien.getTischName())){
                freiTische = freeMap.get(m) - besetzeGarnituren;

            }
            if(m.getName().equals(KonstantenMaterialien.getBankName())){
                freiBaenke = freeMap.get(m) - besetzeGarnituren*2;
            }
        }

        int freiGarnituren = Math.min(freiTische, freiBaenke/2);



        if(material.getName().equals(KonstantenMaterialien.getTischName())){
            return freiTische;
        }
        if(material.getName().equals(KonstantenMaterialien.getBankName())){
            return freiBaenke;
        }
        else return freiGarnituren;
    }

    private Map<Material, Integer> getFreeForMaterialList(List<Material> materialList, LocalDate startdatum, LocalDate enddatum) {
        Map<Material, Integer> materialMap = new HashMap<>();
        for (Material m: materialList){
            int besetzt = getFreeNumbers(m.getId(), startdatum, enddatum);
            materialMap.put(m, besetzt);
        }
        return materialMap;
    }

    public Optional<BuchungMaterial> getById(int id){
        return buchungMaterialRepository.findById(id);
    }
}
