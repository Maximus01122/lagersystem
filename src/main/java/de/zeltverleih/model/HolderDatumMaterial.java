package de.zeltverleih.model;


import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class HolderDatumMaterial extends HolderDates{
    private final List<PlatzMaterial> platzMaterialList;

    public HolderDatumMaterial(LocalDate startdatum, LocalDate enddatum, List<PlatzMaterial> platzMaterialList) {
        super(startdatum, enddatum);
        this.platzMaterialList = Objects.requireNonNull(platzMaterialList,"Liste darf nicht null sein");;
    }

    public List<PlatzMaterial> getPlatzMaterialList() {
        return platzMaterialList;
    }
}
