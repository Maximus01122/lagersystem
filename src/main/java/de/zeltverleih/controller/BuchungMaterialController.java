package de.zeltverleih.controller;

import de.zeltverleih.model.*;
import de.zeltverleih.model.datenbank.Buchung;
import de.zeltverleih.model.datenbank.BuchungMaterial;
import de.zeltverleih.model.datenbank.Material;
import de.zeltverleih.service.BuchungMaterialServiceImpl;
import de.zeltverleih.service.BuchungServiceImpl;
import de.zeltverleih.service.MaterialServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buchungmaterial")
@CrossOrigin
public class BuchungMaterialController {

    @Autowired
    private BuchungMaterialServiceImpl buchungMaterialService;

    @Autowired
    BuchungController buchungController;

    @Autowired
    MaterialServiceImpl materialService;


    public boolean isValid(PlatzMaterial platzMaterial, LocalDate startdatum, LocalDate enddatum) {
        int frei = buchungMaterialService.getFreeNumbers(platzMaterial.getMaterial(),
                startdatum, enddatum);
        return frei >= platzMaterial.getAnzahl();
    }

    private boolean checkBestuhlung(List<PlatzMaterial> bestuhlung, LocalDate startdatum, LocalDate enddatum){
        int freiBank = buchungMaterialService.getFreeNumbers(materialService.getBank(),startdatum,enddatum);
        int freiTisch = buchungMaterialService.getFreeNumbers(materialService.getTisch(),startdatum,enddatum);

        for (PlatzMaterial bm : bestuhlung){
            if (bm.getMaterial().equals(materialService.getBank()))
                freiBank -= bm.getAnzahl();
            else if (bm.getMaterial().equals(materialService.getTisch()))
                freiTisch -= bm.getAnzahl();
            else if (bm.getMaterial().equals(materialService.getGarnitur())){
                freiBank -= bm.getAnzahl()*2;
                freiTisch -= bm.getAnzahl();
            }
            else throw new RuntimeException(bm.getMaterial().getName() + " ist keine Bestuhlung");
        }
        return freiBank >= 0 && freiTisch >= 0;
    }

    @PutMapping("checkBestellung")
    public boolean isValidBestellung(@RequestBody HolderDatumMaterial holder) {
        if (!holder.getPlatzMaterialList().isEmpty()) {

            //Trennung der Liste in Bestuhlung und Rest
            List<PlatzMaterial> notBestuhlung = holder.getPlatzMaterialList().stream().filter(bm ->
                    !materialService.getBestuhlung().contains(bm.getMaterial())).toList();
            List<PlatzMaterial> bestuhlung = holder.getPlatzMaterialList().stream()
                    .filter(element -> !notBestuhlung.contains(element)).toList();

            //Enhält 4x6 und 4x10
            HashSet<Integer> h = new HashSet<>(notBestuhlung.stream().map(PlatzMaterial::getMaterial).map(Material::getId).toList());
            if (h.containsAll(materialService.getConnectedZelte().stream().map(Material::getId).toList()))
                throw new RuntimeException("Es können nicht beide Zelte 4x6m und 4x10m" +
                        " gleichzeitig gebucht werden.");

            if (!checkBestuhlung(bestuhlung, holder.getStartdatum(), holder.getEnddatum()))
                throw new RuntimeException("Zu viele Tische, Bänke oder Garnituren vermietet");
            for (PlatzMaterial bm : notBestuhlung) {
                if (!isValid(bm, holder.getStartdatum(), holder.getEnddatum())){
                    throw new RuntimeException(bm.getMaterial().getName() +
                            " ist nicht mehr oft genug vorhanden.");
                }
            }
        }
        else System.out.println("leere Bestellung zum Testen");
        return true;
    }


    @PutMapping("getAllFreePlaces")
    public List<PlatzMaterial> getAllFreePlaces(@RequestBody HolderDates startUndEnddatum){
        List<Material> allMaterialien = materialService.getAllMaterialien();
        List<PlatzMaterial> freeList = new ArrayList<>();
        LocalDate startdatum = startUndEnddatum.getStartdatum();
        LocalDate enddatum = startUndEnddatum.getEnddatum();

        testDates(startdatum, enddatum);

        allMaterialien.forEach(material -> {
            int frei = buchungMaterialService.getFreeNumbers(material, startdatum, enddatum);
            freeList.add(new PlatzMaterial(frei, material));
        });

        return freeList;
    }

    private void testDates(LocalDate startdatum, LocalDate enddatum){
        if (startdatum == null || enddatum == null)
            throw new RuntimeException("Es müssen beide Daten gesetzt sein");
        if (enddatum.isBefore(startdatum))
            throw new RuntimeException("Das Enddatum muss gleich oder größer sein");
    }

    @PutMapping("getAllFreePlacesWithoutSelf")
    public List<PlatzMaterial> getAllFreePlacesWithoutSelf(@RequestBody Holder holder){
        List<Material> allMaterialien = materialService.getAllMaterialien();
        List<PlatzMaterial> freeList = new ArrayList<>();
        LocalDate startdatum = holder.getStartdatum();
        LocalDate enddatum = holder.getEnddatum();
        int buchungsId = holder.getBuchungsId();
        testDates(startdatum, enddatum);

        allMaterialien.forEach(material -> {
            int frei = buchungMaterialService.getFreeNumbersWithoutSelf(
                    material, buchungsId,startdatum, enddatum);
            freeList.add(new PlatzMaterial(frei, material));
        });
        return freeList;
    }

    @PostMapping("addAll")
    public List<BuchungMaterial> addAll(@RequestBody List<BuchungMaterial> bestellung) {
        HashSet<Buchung> b = new HashSet<>(bestellung.stream().map(BuchungMaterial::getBuchung).toList());

        if(b.size() != 1)
            throw new IllegalArgumentException("Die Buchungsmaterialien haben nicht alle die gleiche Buchung!");

        List<BuchungMaterial> bmList = new ArrayList<>();
        for (BuchungMaterial bm : bestellung) {
            buchungController.getBuchung(bm.getBuchung().getId());
            BuchungMaterial savedBM =buchungMaterialService.saveBuchungMaterial(bm);
            bmList.add(savedBM);
        }
        return bmList;
    }


    @GetMapping("get/{id}")
    public BuchungMaterial getBuchungMaterial(@PathVariable int id) {
        Optional<BuchungMaterial> buchungMaterial = buchungMaterialService.getById(id);
        return buchungMaterial.orElseThrow(() ->
                new IllegalArgumentException("BuchungMaterial mit passender ID: "+ id + " nicht gefunden"));
    }


    @PutMapping("update")
    public BuchungMaterial update(@RequestBody BuchungMaterial buchungMaterial) {
        getBuchungMaterial(buchungMaterial.getId());
        return buchungMaterialService.saveBuchungMaterial(buchungMaterial);
    }

    @GetMapping("/getAll")
    public List<BuchungMaterial> getAll(){
        return buchungMaterialService.getAllBuchungMaterial();
    }


    @DeleteMapping("/deleteAll")
    public void deleteAll(){
        for (BuchungMaterial bm: getAll()) {
            buchungMaterialService.deleteBuchungMaterial(bm);
        }
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id){
        BuchungMaterial buchungMaterial = getBuchungMaterial(id);
        buchungMaterialService.deleteBuchungMaterial(buchungMaterial);
    }
}
