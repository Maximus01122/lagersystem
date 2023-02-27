package de.zeltverleih.model;

import java.time.LocalDate;

public class AngebotInfos extends MietAnzahlBody{
    private LocalDate gueltigBis;

    public AngebotInfos(int anzahlTagesmiete, int anzahlWochendmiete, int lieferung, LocalDate gueltigBis) {
        super(anzahlTagesmiete, anzahlWochendmiete, lieferung);
        this.gueltigBis = gueltigBis;
    }

    public LocalDate getGueltigBis() {
        return gueltigBis;
    }

    public void setGueltigBis(LocalDate gueltigBis) {
        this.gueltigBis = gueltigBis;
    }
}
