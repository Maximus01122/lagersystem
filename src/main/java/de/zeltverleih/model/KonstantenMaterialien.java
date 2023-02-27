package de.zeltverleih.model;

import java.util.Arrays;
import java.util.List;

public class KonstantenMaterialien {
    private static final String GARNITUR = "Garnituren";
    private static final String BANK = "einzelne Bänke";
    private static final String TISCH = "einzelne Tische";

    private static final List<String> Bestuhlung = Arrays.asList(
            GARNITUR, BANK, TISCH);
    private static final String Zelt4x6 = " Zelt (4x6)";
    private static final String Zelt4x10 = "Zelt (4x10)";
    private static final List<String> Zelte = Arrays.asList(
            Zelt4x6, Zelt4x10);

    public static List<String> getBestuhlung() {
        return Bestuhlung;
    }

    public static List<String> getConnectedZelte() {
        return Zelte;
    }

    public static String getGarniturName(){
        return GARNITUR;
    }

    public static String getTischName(){
        return TISCH;
    }

    public static String getBankName(){
        return BANK;
    }

    public static String getZelt4x6Name(){
        return Zelt4x6;
    }

    public static String getZelt4x10Name(){
        return Zelt4x10;
    }
}
