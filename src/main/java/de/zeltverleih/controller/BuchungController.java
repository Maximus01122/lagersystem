package de.zeltverleih.controller;

import de.zeltverleih.model.*;
import de.zeltverleih.model.datenbank.*;
import de.zeltverleih.repository.BuchungRepository;
import de.zeltverleih.service.BuchungServiceImpl;
import de.zeltverleih.service.CreatePDF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/buchung")
@CrossOrigin
public class BuchungController {
    @Autowired
    private BuchungServiceImpl buchungService;

    @Autowired
    BuchungRepository buchungRepository;

    @PutMapping("getByDatum")
    public List<Buchung> getAllFreePlaces(@RequestBody Map<String, LocalDate> userMap){
        LocalDate startdatum = userMap.get("startdatum");
        LocalDate enddatum = userMap.get("enddatum");
        return buchungRepository.findByDatum(startdatum,enddatum);
    }


    @PostMapping("add")
    public Buchung add(@RequestBody Buchung buchung) {
        return buchungService.saveBuchung(buchung);
    }

    @PostMapping("addAll")
    public List<Buchung> add(@RequestBody List<Buchung> buchung) {
        List<Buchung> bList = new ArrayList<>();
        for(Buchung b: buchung){
            bList.add(buchungService.saveBuchung(b));
        }
        return bList;
    }


    @PutMapping("update")
    public Buchung update(@RequestBody Buchung buchung) {
        getBuchung(buchung.getId());
        return buchungService.saveBuchung(buchung);
    }

    @GetMapping("get/{id}")
    public Buchung getBuchung(@PathVariable int id) {
        Optional<Buchung> buchungOptional = buchungService.getById(id);
        return buchungOptional.orElseThrow(() ->
                new IllegalArgumentException("Buchung mit passender ID: "+ id + " nicht gefunden"));
    }

    @PostMapping("createRechnung/{id}")
    public void createRechnung(@PathVariable int id,@RequestBody RechnungInfos rechnungInfos) {
        Buchung b = getBuchung(id);
        List<BuchungMaterial> list = getMaterialienByBuchung_Id(id);
        TreeMap<BuchungMaterial, Double> bestellung = new TreeMap<>();
        for (BuchungMaterial m : list) {
            double preis = rechnungInfos.getAnzahlWochendmiete() * m.getMaterial().getWochenendMiete();
            preis+= rechnungInfos.getAnzahlTagesmiete()*m.getMaterial().getTagesMiete();
            preis*=m.getAnzahl();
            bestellung.put(m,preis);
        }
        CreatePDF.createPDF(b.getKunde(), bestellung, rechnungInfos);
    }

    @PostMapping("createAngebot/{id}")
    public void createAngebot(@PathVariable int id,@RequestBody AngebotInfos angebotInfos) {
        Buchung b = getBuchung(id);
        List<BuchungMaterial> list = getMaterialienByBuchung_Id(id);
        TreeMap<BuchungMaterial, Double> bestellung = new TreeMap<>();
        for (BuchungMaterial m : list) {
            double preis = angebotInfos.getAnzahlWochendmiete() * m.getMaterial().getWochenendMiete();
            preis+= angebotInfos.getAnzahlTagesmiete()*m.getMaterial().getTagesMiete();
            preis*=m.getAnzahl();
            bestellung.put(m,preis);
        }
        CreatePDF.createPDF(b.getKunde(), bestellung, angebotInfos);
    }


    @GetMapping("/getAll")
    public List<Buchung> getAll(){
        return buchungService.getAllBuchung();
    }

    @GetMapping("getMaterialien/{id}")
    public List<BuchungMaterial> getMaterialienByBuchung_Id(@PathVariable int id){
        return buchungService.getMaterialienByBuchung_Id(id);
    }

    @GetMapping("getLadepauschaule/{id}")
    public Ladepauschale getLadepauschale(@PathVariable int id){
        getBuchung(id);
        return buchungService.getLadepauschale(id);
    }

    @GetMapping("getAufbauService/{id}")
    public List<AufbauService> getAufbauService(@PathVariable int id){
        getBuchung(id);
        return buchungService.getAufbauService(id);
    }

    @GetMapping("getPreis/{id}")
    public double getPreis(@PathVariable int id, @RequestBody Mietdauer mietdauer){
        getBuchung(id);
        List<BuchungMaterial> bestellung = getMaterialienByBuchung_Id(id);
        List<AufbauService> aufbauService = getAufbauService(id);
        List<String> aufbauServices =aufbauService.stream().map(AufbauService::getName).toList();
        double ladung = getLadepauschale(id).getPreis();
        int tage = mietdauer.getTage();
        int wochende = mietdauer.getWochenende();
        double preis = 0;

        for (BuchungMaterial bm: bestellung){
            Material m = bm.getMaterial();

            if (KonstantenMaterialien.getBestuhlung().contains(bm.getMaterial().getName())
                    && aufbauServices.contains(AufbauServiceNamen.Aufbau_Garnituren.name())){
                preis += bm.getAnzahl() * ((tage+ wochende)* m.getAufbau());
            }
            else if (aufbauServices.contains(AufbauServiceNamen.Aufbau_Zelte.name()) ||
                    aufbauServices.contains(AufbauServiceNamen.Aufbau_Regenrinne.name())){
                preis += bm.getAnzahl() * ((tage+ wochende)* m.getAufbau());
            }
        }
        return preis+ladung;
    }

    @DeleteMapping("/deleteAll")
    public void deleteAll(){
        for (Buchung b: getAll()) {
            buchungService.deleteBuchung(b);
        }
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id){
        Buchung buchung = getBuchung(id);
        buchungService.deleteBuchung(buchung);
    }
}
