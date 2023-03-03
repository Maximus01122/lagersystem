package de.zeltverleih.model;

public class MietAnzahlBody {
    private int anzahlTagesmiete;
    private int anzahlWochenendmiete;

    private int lieferung;

    public MietAnzahlBody(int anzahlTagesmiete, int anzahlWochendmiete, int lieferung) {
        this.anzahlTagesmiete = anzahlTagesmiete;
        this.anzahlWochenendmiete = anzahlWochendmiete;
        this.lieferung = lieferung;
    }

    public int getLieferung() {
        return lieferung;
    }

    public void setLieferung(int lieferung) {
        this.lieferung = lieferung;
    }

    public int getAnzahlTagesmiete() {
        return anzahlTagesmiete;
    }

    public void setAnzahlTagesmiete(int anzahlTagesmiete) {
        this.anzahlTagesmiete = anzahlTagesmiete;
    }

    public int getAnzahlWochenendmiete() {
        return anzahlWochenendmiete;
    }

    public void setAnzahlWochenendmiete(int anzahlWochenendmiete) {
        this.anzahlWochenendmiete = anzahlWochenendmiete;
    }
}
