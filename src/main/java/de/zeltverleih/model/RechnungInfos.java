package de.zeltverleih.model;

import java.time.LocalDate;

public class RechnungInfos extends MietAnzahlBody{
    private LocalDate rechnungsdatum;
    private LocalDate begleichsdatum;

    public RechnungInfos(int anzahlTagesmiete, int anzahlWochendmiete, int lieferung,
                         LocalDate rechnungsdatum, LocalDate begleichsdatum) {
        super(anzahlTagesmiete, anzahlWochendmiete, lieferung);
        this.rechnungsdatum = rechnungsdatum;
        this.begleichsdatum = begleichsdatum;
    }


    public LocalDate getRechnungsdatum() {
        return rechnungsdatum;
    }

    public void setRechnungsdatum(LocalDate rechnungsdatum) {
        this.rechnungsdatum = rechnungsdatum;
    }

    public LocalDate getBegleichsdatum() {
        return begleichsdatum;
    }

    public void setBegleichsdatum(LocalDate begleichsdatum) {
        this.begleichsdatum = begleichsdatum;
    }
}
