package de.zeltverleih.model.datenbank;

import javax.persistence.*;

@Entity
public class Ladepauschale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private double preis;

    @Column(nullable = false, unique = true)
    private String name;

    public Ladepauschale(int id, String name ,double preis) {
        this.id = id;
        this.name = name;
        this.preis = preis;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ladepauschale() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        if (preis<0)
            throw new RuntimeException("Preis darf nicht kleiner als 0 sein");
        this.preis = preis;
    }
}
