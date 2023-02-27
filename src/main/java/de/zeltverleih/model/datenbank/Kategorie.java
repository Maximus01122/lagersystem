package de.zeltverleih.model.datenbank;

import de.zeltverleih.model.AufbauServiceNamen;
import de.zeltverleih.model.Kategorienamen;

import javax.persistence.*;
import java.util.Arrays;

@Entity
public class Kategorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String name;

    public Kategorie(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Kategorie() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        try{Kategorienamen.valueOf(name);}
        catch (IllegalArgumentException ex){
            throw new IllegalArgumentException("Es sind nur die Namen "
                    + Arrays.toString(Kategorienamen.values()) + " erlaubt");
        }
        this.name = name;
    }
}
