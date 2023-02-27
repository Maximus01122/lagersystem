package de.zeltverleih.model.datenbank;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class BuchungAufbauService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private Buchung buchung;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private AufbauService aufbauService;

    public BuchungAufbauService(Buchung buchung, AufbauService aufbauService) {
        this.buchung = buchung;
        this.aufbauService = aufbauService;
    }

    public BuchungAufbauService(){}

    public Buchung getBuchung() {
        return buchung;
    }

    public void setBuchung(Buchung buchung) {
        this.buchung = Objects.requireNonNull(buchung, "Die Buchung darf nicht null sein");
    }

    public AufbauService getAufbauService() {
        return aufbauService;
    }

    public void setAufbauService(AufbauService aufbauService) {
        this.aufbauService = Objects.requireNonNull(aufbauService, "Der AufbauService darf nicht null sein");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
