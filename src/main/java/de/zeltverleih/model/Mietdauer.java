package de.zeltverleih.model;

public class Mietdauer {
    private int wochenende;
    private int tage;

    public Mietdauer(int wochenende, int tage) {
        this.wochenende = wochenende;
        this.tage = tage;
    }

    public int getWochenende() {
        return wochenende;
    }

    public void setWochenende(int wochenende) {
        this.wochenende = wochenende;
    }

    public int getTage() {
        return tage;
    }

    public void setTage(int tage) {
        this.tage = tage;
    }
}
