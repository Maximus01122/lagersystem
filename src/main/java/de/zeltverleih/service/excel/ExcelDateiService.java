package de.zeltverleih.service.excel;

import de.zeltverleih.model.datenbank.Material;
import org.apache.commons.collections4.MapUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public abstract class ExcelDateiService<T> {

    protected final List<String> Kundeninfos =
            List.of("Angebot vom", "gültig bis", "Startdatum", "Enddatum", "Kundenname", "Telefonnummer", "Email", "Adresse", "Service", "Summe", "Kaution", "Aufbau/ Lieferung", "Abbau/    Abholung", "Kommentar");
    public static Map<String, Integer> ColumnNames = new HashMap<>();
    public Map<Integer, String> reversedColumnNames = new HashMap<>();
    protected abstract String getSheetName();

    protected abstract T rowToObject(Row reihe);
    protected abstract void MaterialList(Row reihe);
    protected abstract List<Material> MaterialList();


    public static Map<String, Integer> ColumnNames() {
        return ColumnNames;
    }

    public Map<Integer, String> reversedColumnNames() {
        return reversedColumnNames;
    }

    // ZU Spaltennummer wird Überschrift gemappt
    private HashMap<Integer,String> reversedMap(Map<String,Integer> map){
        HashMap<Integer, String> reversedHashMap = new HashMap<>();
        for (String i : map.keySet()) {
            reversedHashMap.put(map.get(i), i);
        }
        return reversedHashMap;
    }

    public List<T> readExcel(String pathToFile) throws IOException {

        ArrayList<T> excelRows = new ArrayList<T>();

        //Einlesen der Datei
        File file = new File(pathToFile);
        FileInputStream inputStream = new FileInputStream(file);

        Workbook workbook = null;

        //Erstellen des passenden Workbooks anhand der Endung
        String fileExtensionName = pathToFile.substring(pathToFile.indexOf("."));
        if (fileExtensionName.equals(".xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (fileExtensionName.equals(".xls")) {
            workbook = new HSSFWorkbook(inputStream);

        }

        assert workbook != null;

        // Blatt mit festgelegtem Namen finden
        Sheet sheet = workbook.getSheet(getSheetName());

        // Spaltennamen der Excel-Tabelle und Spaltennummer werden erfasst
        Iterator<Row> rowIterator = sheet.iterator();

        Row row = rowIterator.next();
        Iterator<Cell> iterator = row.cellIterator();
        DataFormatter dataFormatter = new DataFormatter();

        // Überschriften und Spaltennummern zuordnen
        while (iterator.hasNext()) {
            Cell cell = iterator.next();
            ColumnNames.put(dataFormatter.formatCellValue(cell), cell.getColumnIndex());
        }

        // Spaltennummern und Überschriften zuordnen
        reversedColumnNames = MapUtils.invertMap(ColumnNames);

        rowIterator.next();

        // Überschriften der Excel-Tabelle werden übersprungen
        for (int i = 1; i < 6; i++) {
            rowIterator.next();
        }


        // Jede Reihe in Tabelle wird zu neuem Datensatz (Kunde mit Buchung) verwandelt
        while (rowIterator.hasNext()) {
            T object = rowToObject(rowIterator.next());
            if (object != null)
                excelRows.add(object);
        }

        workbook.close();


        return excelRows;
    }


    /** War da um die einzelnen Materialien mit Preisen und Anzahl in Datenbank zu schreiben**/
    public List<Material> doIt(String pathToFile) throws IOException {

        ArrayList<T> excelRows = new ArrayList<T>();
        File file = new File(pathToFile);
        FileInputStream inputStream = new FileInputStream(file);
        Workbook workbook = null;

        String fileExtensionName = pathToFile.substring(pathToFile.indexOf("."));
        if (fileExtensionName.equals(".xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (fileExtensionName.equals(".xls")) {
            workbook = new HSSFWorkbook(inputStream);

        }

        assert workbook != null;
        Sheet sheet = workbook.getSheet(getSheetName());

        Iterator<Row> rowIterator = sheet.iterator();

        Row row = rowIterator.next();
        Iterator<Cell> iterator = row.cellIterator();
        DataFormatter dataFormatter = new DataFormatter();

        while (iterator.hasNext()) {
            Cell cell = iterator.next();
            ColumnNames.put(dataFormatter.formatCellValue(cell), cell.getColumnIndex());
        }

        reversedColumnNames = MapUtils.invertMap(ColumnNames);

        rowIterator.next();

        // Spalten in Materialien umwandeln
        for (int i = 1; i < 6; i++) {
            MaterialList(rowIterator.next());
        }

        return MaterialList();
    }
}
