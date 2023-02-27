package de.zeltverleih.model.datenbank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Kundennummer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int kundenummern;

    public Kundennummer(int id, int kundenummern) {
        this.id = id;
        this.kundenummern = kundenummern;
    }

    public Kundennummer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKundenummern() {
        return kundenummern;
    }

    public void setKundenummern(int kundenummern) {
        this.kundenummern = kundenummern;
    }
}
