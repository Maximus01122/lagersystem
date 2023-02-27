package de.zeltverleih.model;
import java.time.LocalDate;
import java.util.Objects;

public class HolderDates {
    private LocalDate startdatum;
    private LocalDate enddatum;

    public HolderDates(LocalDate startdatum, LocalDate enddatum) {
        this.startdatum = Objects.requireNonNull(startdatum,"Startdatum darf nicht null sein");;
        this.enddatum = Objects.requireNonNull(enddatum,"Enddatum darf nicht null sein");;
    }

    public HolderDates(){}

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
}
