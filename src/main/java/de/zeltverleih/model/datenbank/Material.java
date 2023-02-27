package de.zeltverleih.model.datenbank;

import javax.persistence.*;

@Entity
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name", nullable = false, length = 60, unique = true)
    private String name;
    @Column
    int anzahl;

    @Column
    double tagesMiete;
    @Column
    double wochenendMiete;
    @Column
    double aufbau;

    @ManyToOne
    Kategorie kategorie;

    public Material() {
    }


    public Material(String name, int anzahl) {
        this.name = name;
        this.anzahl = anzahl;
    }

    public Material(String name, int anzahl, double tagesMiete, double wochenendMiete, double aufbau) {
        this.name = name;
        this.anzahl = anzahl;
        this.tagesMiete = tagesMiete;
        this.wochenendMiete = wochenendMiete;
        this.aufbau = aufbau;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id<0)
            throw new IllegalArgumentException("ID darf nicht negativ sein");
        this.id = id;
    }

    public Kategorie getKategorie() {
        return kategorie;
    }

    public void setKategorie(Kategorie kategorie) {
        this.kategorie = kategorie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null)
            throw new NullPointerException("Name darf nicht null sein");
        if (!(name.isEmpty() || name.isBlank()))
            this.name = name;
    }

    public double getWochenendMiete() {
        if (anzahl<=0)
            throw new IllegalArgumentException("Preis für Wochenende muss größer 0 sein");
        return wochenendMiete;
    }

    public void setWochenendMiete(double wochenendPreis) {
        if (anzahl<=0)
            throw new IllegalArgumentException("Preis für Tagesmiete muss größer 0 sein");
        this.wochenendMiete = wochenendPreis;
    }

    public double getTagesMiete() {
        return tagesMiete;
    }

    public void setTagesMiete(double tagesPreis) {
        this.tagesMiete = tagesPreis;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(int anzahl) {
        if (anzahl<=0)
            throw new IllegalArgumentException("Anzahl muss größer 0 sein");
        this.anzahl = anzahl;
    }

    public double getAufbau() {
        return aufbau;
    }

    public void setAufbau(double aufbau) {
        this.aufbau = aufbau;
    }

    public void checkAttribute(){
        setAnzahl(getAnzahl());
        setName(getName());
        setTagesMiete(getTagesMiete());
        setWochenendMiete(getWochenendMiete());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        return ((Material) o).id == this.id;
    }

}
