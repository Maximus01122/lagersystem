package de.zeltverleih;

import de.zeltverleih.model.AufbauServiceNamen;
import de.zeltverleih.model.Kategorienamen;
import de.zeltverleih.model.PlatzMaterial;
import de.zeltverleih.model.datenbank.*;
import de.zeltverleih.repository.BuchungRepository;
import de.zeltverleih.service.*;
import de.zeltverleih.service.excel.ExcelReiheBestellung;
import de.zeltverleih.service.excel.ExcelReiheBuchung;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.util.Pair;

import java.util.List;

@Configuration
class Loader {

    private static final Logger log = LoggerFactory.getLogger(Loader.class);
    @Autowired
    BuchungMaterialServiceImpl buchungMaterialService;

    @Autowired
    BuchungServiceImpl buchungService;

    @Autowired
    KundenServiceImpl kundenService;
    @Autowired
    MaterialServiceImpl materialService;

    @Autowired
    AufbauServiceService aufbauServiceService;

    @Autowired
    KategorieServiceImpl kategorieService;
    @Bean
    CommandLineRunner initDatabase(BuchungRepository repository) {

        return /*args -> {
            ExcelReiheBuchung erb = new ExcelReiheBuchung(materialService);
            erb.introduceMaterial().forEach(m -> materialService.saveMaterial(m));
            List<Pair<Buchung, List<PlatzMaterial>>> bList = erb.readExcel(
                    "src/main/resources/Terminübersicht 2022.xlsx");
            bList.forEach(pair ->
            {
                Kunde k = pair.getFirst().getKunde();
                k.setAdresse(new Adresse("","","",""));
                kundenService.saveKunde(k);
                Buchung b = buchungService.saveBuchung(pair.getFirst());
                List<PlatzMaterial> pmList = pair.getSecond();
                for (PlatzMaterial platzmaterial:
                     pmList) {
                    System.out.println(platzmaterial.getMaterial().getName());
                    Material m = materialService.getByName(platzmaterial.getMaterial().getName());
                    BuchungMaterial bm = new BuchungMaterial(m,b, platzmaterial.getAnzahl());
                    buchungMaterialService.saveBuchungMaterial(bm);
                }

            });

            ExcelReiheBestellung reiheBestellung = new ExcelReiheBestellung(materialService, buchungService);
            List<List<PlatzMaterial>> bmList = reiheBestellung.readExcel(
                    "src/main/resources/Terminübersicht 2022.xlsx");
            bmList.forEach(kbmList->kbmList.forEach(System.out::println));

            for (AufbauServiceNamen name : AufbauServiceNamen.values()) {
                AufbauService a = new AufbauService();
                a.setName(name.name());
                aufbauServiceService.saveService(a);
            }

            for (Kategorienamen name : Kategorienamen.values()) {
                Kategorie k = new Kategorie();
                k.setName(name.name());
                System.out.println(k.getName());
                kategorieService.saveKategorie(k);
            }
        }*/null;
    }
}