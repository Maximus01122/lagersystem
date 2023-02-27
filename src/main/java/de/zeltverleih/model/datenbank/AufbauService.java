package de.zeltverleih.model.datenbank;

import de.zeltverleih.model.AufbauServiceNamen;

import javax.persistence.*;
import java.util.Arrays;

@Entity
public class AufbauService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    public AufbauService(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public AufbauService() {
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
        try{
            AufbauServiceNamen.valueOf(name);}
        catch (IllegalArgumentException ex){
            throw new IllegalArgumentException("Es sind nur die Namen "
                    + Arrays.toString(AufbauServiceNamen.values()) + " erlaubt");
        }
        this.name = name;
    }
}
