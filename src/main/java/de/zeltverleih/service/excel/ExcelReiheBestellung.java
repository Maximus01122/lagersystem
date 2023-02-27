package de.zeltverleih.service.excel;

import de.zeltverleih.model.*;
import de.zeltverleih.model.datenbank.Material;
import de.zeltverleih.service.BuchungServiceImpl;
import de.zeltverleih.service.MaterialServiceImpl;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Service
public class ExcelReiheBestellung extends ExcelDateiService<List<PlatzMaterial>>{
    @Autowired
    private MaterialServiceImpl materialService;

    @Autowired
    private BuchungServiceImpl buchungService;

    public ExcelReiheBestellung(MaterialServiceImpl materialService, BuchungServiceImpl buchungService) {
        this.materialService = materialService;
        this.buchungService = buchungService;
    }

    @Override
    protected String getSheetName() {
        return "Buchungen";
    }

    @Override
    protected List<PlatzMaterial> rowToObject(Row reihe) {

        Iterator<Cell> iterator = reihe.cellIterator();
        List<PlatzMaterial> bestellung = new ArrayList<>();

        while (iterator.hasNext()){
            Cell cell = iterator.next();
            try {

                if (cell.getCellType().equals(CellType.NUMERIC)){

                    int number = cell.getColumnIndex();
                    String columnName = reversedColumnNames.get(number);

                    // Eintrag in Spalte zu Material umwandeln und in Buchung hinzufügen
                    if (!Kundeninfos.contains(columnName)){
                        Material m = materialService.getByName(columnName);
                        bestellung.add(new PlatzMaterial((int) cell.getNumericCellValue(),m));
                        System.out.println(number +  ": " +columnName + "  " + cell.getNumericCellValue());
                    }
                }
            }
            catch (Exception ignored){}
        }
        return bestellung;
    }

    @Override
    protected void MaterialList(Row reihe) {}

    @Override
    protected List<Material> MaterialList() {
        return null;
    }
}
