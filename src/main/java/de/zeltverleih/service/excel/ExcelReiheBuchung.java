package de.zeltverleih.service.excel;

import de.zeltverleih.model.datenbank.Adresse;
import de.zeltverleih.model.datenbank.Buchung;
import de.zeltverleih.model.datenbank.Kunde;
import de.zeltverleih.model.datenbank.Material;
import de.zeltverleih.model.PlatzMaterial;
import de.zeltverleih.service.MaterialServiceImpl;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service

public class ExcelReiheBuchung extends ExcelDateiService<Pair<Buchung,List<PlatzMaterial>>>{

    @Autowired
    private MaterialServiceImpl materialService;

    private final DataFormatter dataFormatter = new DataFormatter();

    private int[] anzahlMaterial = new int[100];
    private double[] aufbauMaterial = new double[100];
    private double[] tagesMieteMaterial = new double[100];
    private double[] wochenendMieteMaterial = new double[100];

    public ExcelReiheBuchung(MaterialServiceImpl materialService) {
        this.materialService = materialService;
    }


    @Override
    protected String getSheetName() {
        return "Buchungen";
    }

    @Override
    protected Pair<Buchung, List<PlatzMaterial>> rowToObject(Row reihe) {
        // Auslesen der Kundendaten
        Cell name = reihe.getCell(ColumnNames.get("Kundenname"));
        Cell telefonnummer = reihe.getCell(ColumnNames.get("Telefonnummer"));
        Cell email = reihe.getCell(ColumnNames.get("Email"));
        Cell adresse = reihe.getCell(ColumnNames.get("Adresse"));

        System.out.println(dataFormatter.formatCellValue(name));
        List<PlatzMaterial> mList = MaterialienFromColumn(reihe);
        System.out.println("\n");

        if (name == null || name.getStringCellValue().equals("Summe"))
            return null;

        else{
            Kunde k = new Kunde(dataFormatter.formatCellValue(name),
                    dataFormatter.formatCellValue(email),
                    dataFormatter.formatCellValue(telefonnummer),
                    //dataFormatter.formatCellValue(adresse)
                    new Adresse());
            Buchung b = BuchungFromRow(reihe, k);
            return Pair.of(b, mList);
        }
    }

    /** War da um die einzelnen Materialien mit Preisen und Anzahl in Datenbank zu schreiben**/
    protected List<Material> MaterialList(){
        List<Material> all = new ArrayList<>();
        for (Integer i : reversedColumnNames.keySet()) {
            if (!Kundeninfos.contains(reversedColumnNames.get(i)))
                all.add(new Material(reversedColumnNames.get(i),
                    anzahlMaterial[i],tagesMieteMaterial[i], wochenendMieteMaterial[i],
                    aufbauMaterial[i]));
        }
        return all;
    }

    /** War da um die einzelnen Materialien mit Preisen und Anzahl in Datenbank zu schreiben**/
    @Override
    protected void MaterialList(Row reihe) {
        System.out.println(reihe.getRowNum());
        Iterator<Cell> iterator = reihe.cellIterator();
        while (iterator.hasNext()){
            Cell cell = iterator.next();
            try {
                //Print Excel data in console
                if (cell.getCellType().equals(CellType.NUMERIC)){
                    int number = cell.getColumnIndex();
                    String columnName = reversedColumnNames.get(number);
                    if (!Kundeninfos.contains(columnName)){
                        if (reihe.getRowNum()==2)
                            anzahlMaterial[number] = (int) cell.getNumericCellValue();
                        else if (reihe.getRowNum()==5)
                            aufbauMaterial[number] =  cell.getNumericCellValue();
                        else if (reihe.getRowNum()==4)
                            wochenendMieteMaterial[number] = cell.getNumericCellValue();
                        else if (reihe.getRowNum()==3)
                            tagesMieteMaterial[number] = cell.getNumericCellValue();
                    }
                }
            }catch (Exception ignored){

            }
        }
    }

    protected Buchung BuchungFromRow(Row reihe, Kunde k) {
        Cell startdatum = reihe.getCell(ExcelReiheBuchung.ColumnNames.get("Startdatum"));
        Cell enddatum = reihe.getCell(ExcelReiheBuchung.ColumnNames.get("Enddatum"));
        Cell angebotVon = reihe.getCell(ExcelReiheBuchung.ColumnNames.get("Angebot vom"));

        LocalDate sd = convertToLocalDate(startdatum.getDateCellValue());
        LocalDate ed = convertToLocalDate(enddatum.getDateCellValue());
        Date av = StringToDate(dataFormatter.formatCellValue(angebotVon));
        if (av ==null)
            return new Buchung(k,sd,ed,null);
        else
            return new Buchung(k,sd,ed,convertToLocalDate(av));
    }

    public LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private Date StringToDate(String angebotVon) {
        try {
            return new SimpleDateFormat("MM/dd/yy")
                    .parse(angebotVon);
        } catch (NullPointerException | ParseException ex) {
            return null;
        }
    }


    public List<PlatzMaterial> MaterialienFromColumn(Row reihe) {
        Iterator<Cell> iterator = reihe.cellIterator();
        List<PlatzMaterial> bestellung = new ArrayList<>();
        while (iterator.hasNext()){
            Cell cell = iterator.next();
            try {
                //Print Excel data in console
                if (cell.getCellType().equals(CellType.NUMERIC)){
                    int number = cell.getColumnIndex();
                    String columnName = reversedColumnNames.get(number);
                    if (!Kundeninfos.contains(columnName)){
                        Material m = materialService.getByName(columnName);
                        bestellung.add(new PlatzMaterial((int) cell.getNumericCellValue(),m));
                        System.out.println(number +  ": " +columnName + "  " + cell.getNumericCellValue());
                    }
                }
            }catch (Exception ignored){}
        }
        return bestellung;
    }

    public List<Material> introduceMaterial() throws IOException {
        return doIt("src/main/resources/Terminübersicht 2022.xlsx");
    }
}
