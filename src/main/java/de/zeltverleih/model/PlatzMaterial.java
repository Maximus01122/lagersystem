package de.zeltverleih.model;

import de.zeltverleih.model.datenbank.Material;

public class PlatzMaterial {
    private int anzahl;
    private Material material;

    public PlatzMaterial(){}

    public PlatzMaterial(int anzahl, Material material){
        this.anzahl = anzahl;
        this.material = material;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
