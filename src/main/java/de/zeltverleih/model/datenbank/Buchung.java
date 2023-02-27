package de.zeltverleih.model.datenbank;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Buchung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teilnehmer_id", nullable = false)
    private Kunde kunde;

    private LocalDate startdatum;
    private LocalDate enddatum;

    private LocalDate angebotVon;

    private boolean angebotAkzeptiert;


    public Buchung(){}
    public Buchung(Kunde kunde, LocalDate startdatum, LocalDate enddatum, LocalDate angebotVon) {
        this.kunde = kunde;
        this.startdatum = startdatum;
        this.enddatum = enddatum;
        this.angebotVon = angebotVon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getStartdatum() {
        return startdatum;
    }

    public void setStartdatum(LocalDate startdatum) {
        if (enddatum!= null && startdatum.isAfter(enddatum))
            throw new IllegalArgumentException("Startdatum muss vor dem Enddatum sein");
        this.startdatum = startdatum;
    }

    public LocalDate getEnddatum() {
        return enddatum;
    }

    public void setEnddatum(LocalDate enddatum) {
        if (enddatum!= null && enddatum.isBefore(startdatum))
            throw new IllegalArgumentException("Enddatum muss nach dem Startdatum sein");
        this.enddatum = enddatum;
    }

    public Kunde getKunde() {
        return kunde;
    }

    public void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }

    public LocalDate getAngebotVon() {
        return angebotVon;
    }

    public void setAngebotVon(LocalDate angebotVon) {
        this.angebotVon =  angebotVon;
    }

    public boolean isAngebotAkzeptiert() {
        return angebotAkzeptiert;
    }

    public void setAngebotAkzeptiert(boolean angebotAkzeptiert) {
        this.angebotAkzeptiert = angebotAkzeptiert;
    }

    public void checkAttribute(){
        setKunde(getKunde());
        setStartdatum(getStartdatum());
        setEnddatum(getEnddatum());
        setAngebotVon(getAngebotVon());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Buchung buchung = (Buchung) o;
        return id == buchung.id && angebotAkzeptiert == buchung.angebotAkzeptiert
                && Objects.equals(kunde, buchung.kunde)
                && Objects.equals(startdatum, buchung.startdatum)
                && Objects.equals(enddatum, buchung.enddatum)
                && Objects.equals(angebotVon, buchung.angebotVon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, kunde, startdatum, enddatum, angebotVon, angebotAkzeptiert);
    }

    @Override
    public String toString() {
        return "Buchung{" +
                "id=" + id +
                ", kunde=" + kunde.toString() +
                ", startdatum=" + startdatum +
                ", enddatum=" + enddatum +
                ", angebotVon=" + angebotVon +
                ", angebotAkzeptiert=" + angebotAkzeptiert +
                '}';
    }
}
