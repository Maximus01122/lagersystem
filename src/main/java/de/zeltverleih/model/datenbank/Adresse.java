package de.zeltverleih.model.datenbank;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Adresse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(nullable = false)
    private String plz;
    @Column(nullable = false)
    private String strasse;
    @Column(nullable = false)
    private String ort;
    @Column(nullable = false)
    private String hausnummer;

    public Adresse() {
    }

    public Adresse(String plz, String ort, String strasse, String hausnummer) {
        this.plz = plz;
        this.strasse = ort;
        this.ort = strasse;
        this.hausnummer = hausnummer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id<0)
            throw new IllegalArgumentException("ID darf nicht negativ sein");
        this.id = id;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz){
        if(plz==null)
            throw new NullPointerException("PLZ darf nicht null sein!");

        for (char c : plz.toCharArray()){
            if (c < '0' || c > '9'){
                throw new IllegalArgumentException("PLZ darf nur Ziffern enthalten");
            }
        }
        this.plz = plz;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        if (strasse == null)
            throw new NullPointerException("Strasse darf nicht null sein");

        for (char c : strasse.toCharArray()){
            if (!(c < '0' || c > '9')){
                throw new IllegalArgumentException("Strasse darf keine Ziffern enthalten");
            }
        }

        this.strasse = strasse;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        if (ort == null)
            throw new NullPointerException("Ort darf nicht null sein");

        for (char c : ort.toCharArray()){
            if (!(c < '0' || c > '9')){
                throw new IllegalArgumentException("Ort darf keine Ziffern enthalten");
            }
        }
        this.ort = ort;
    }

    public String getHausnummer() {
        return hausnummer;
    }

    public void setHausnummer(String hausnummer) {
        if (hausnummer == null)
            throw new NullPointerException("Hausnummer darf nicht null sein");
        this.hausnummer = hausnummer;
    }

    @Override
    public String toString() {
        return "Adresse{" +
                "id=" + id +
                ", plz='" + plz + '\'' +
                ", strasse='" + strasse + '\'' +
                ", ort='" + ort + '\'' +
                ", hausnummer='" + hausnummer + '\'' +
                '}';
    }

    public void checkAttribute(){
        setOrt(getOrt());
        setStrasse(getStrasse());
        setHausnummer(getHausnummer());
        setPlz(getPlz());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adresse adresse = (Adresse) o;
        return id == adresse.id
                && Objects.equals(plz, adresse.plz)
                && Objects.equals(strasse, adresse.strasse)
                && Objects.equals(ort, adresse.ort)
                && Objects.equals(hausnummer, adresse.hausnummer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, plz, strasse, ort, hausnummer);
    }
}
