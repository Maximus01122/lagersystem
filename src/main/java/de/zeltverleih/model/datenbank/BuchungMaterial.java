package de.zeltverleih.model.datenbank;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class BuchungMaterial implements Comparable<BuchungMaterial>{

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "buchung_id", nullable = false)
    private Buchung buchung;

    @Column
    int anzahl;

    public BuchungMaterial(Material material, Buchung buchung, int anzahl) {
        this.material = material;
        this.buchung = buchung;
        this.anzahl = anzahl;
    }

    public BuchungMaterial(){}

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = Objects.requireNonNull(material, "Material von von Buchungsmaterial darf nicht null sein");
    }

    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(int anzahl) {
        if (anzahl<=0)
            throw new IllegalArgumentException("Anzahl muss größer 0 sein");
        this.anzahl = anzahl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Buchung getBuchung() {
        return buchung;
    }

    public void setBuchung(Buchung buchung) {
        this.buchung = Objects.requireNonNull(buchung, "Buchung von Buchungsmaterial darf nicht null sein");

    }

    public void checkAttribute(){
        setBuchung(getBuchung());
        setMaterial(getMaterial());
    }

    @Override
    public int compareTo(BuchungMaterial o) {
        return this.getMaterial().getId() - o.getMaterial().getId();
    }
}
