package de.zeltverleih.model.datenbank;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class BuchungLadepauschale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne(optional = false)
    @JoinColumn(nullable = false)
    private Ladepauschale ladepauschale;
    @OneToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(nullable = false, unique = true)
    private Buchung buchung;

    public BuchungLadepauschale(Ladepauschale ladepauschale, Buchung buchung) {
        this.ladepauschale = ladepauschale;
        this.buchung = buchung;
    }

    public BuchungLadepauschale(){}

    public Ladepauschale getLadepauschale() {
        return ladepauschale;
    }

    public void setLadepauschale(Ladepauschale ladepauschale) {
        this.ladepauschale = Objects.requireNonNull(ladepauschale, "Ladepauschale darf nicht null sein");
    }

    public Buchung getBuchung() {
        return buchung;
    }

    public void setBuchung(Buchung buchung) {
        this.buchung = Objects.requireNonNull(buchung, "Buchung darf nicht null sein");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
