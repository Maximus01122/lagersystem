package de.zeltverleih.model;

import java.time.LocalDate;
import java.util.Objects;

public class Holder {
    private LocalDate startdatum;
    private LocalDate enddatum;
    private int buchungsId;

    public Holder(LocalDate startdatum, LocalDate enddatum, int buchungsId) {
        this.startdatum = Objects.requireNonNull(startdatum,"Startdatum darf nicht null sein");
        this.enddatum = Objects.requireNonNull(enddatum,"Enddatum darf nicht null sein");
        this.buchungsId = buchungsId;
    }

    public Holder() {
    }

    public Holder(LocalDate startdatum, LocalDate enddatum) {
        this.startdatum = Objects.requireNonNull(startdatum,"Startdatum darf nicht null sein");;
        this.enddatum = Objects.requireNonNull(enddatum,"Enddatum darf nicht null sein");;
    }

    public LocalDate getStartdatum() {
        return startdatum;
    }

    public void setStartdatum(LocalDate startdatum) {
        this.startdatum = Objects.requireNonNull(startdatum,"Startdatum darf nicht null sein");;
    }

    public LocalDate getEnddatum() {
        return enddatum;
    }

    public void setEnddatum(LocalDate enddatum) {
        this.enddatum = Objects.requireNonNull(enddatum,"Enddatum darf nicht null sein");
    }

    public int getBuchungsId() {
        return buchungsId;
    }

    public void setBuchungsId(int buchungsId) {
        this.buchungsId = buchungsId;
    }
}
