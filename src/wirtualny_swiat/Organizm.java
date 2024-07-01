package wirtualny_swiat;

import java.awt.*;
import wirtualny_swiat.UI.*;

public abstract class Organizm {
    private final String nazwa;
    private String symbol;
    protected Swiat swiat;
    protected int sila;
    protected int inicjatywa;
    protected int wiek;
    protected Pozycja pozycja;
    protected Color kolor;
    protected boolean zyje;
    protected boolean potomek;

    public Organizm(Swiat swiat, String nazwa, Pozycja pozycja, int sila, int inicjatywa, String symbol, Color kolor) {
        this.nazwa = nazwa;
        this.pozycja = pozycja;
        this.sila = sila;
        this.inicjatywa = inicjatywa;
        this.swiat = swiat;
        this.symbol = symbol;
        this.kolor = kolor;
        this.wiek = 0;
        this.zyje = true;
        this.potomek = false;
    }

    public abstract void akcja();
    public abstract void kolizja(Pozycja pozcyja);
    public abstract Organizm dziecko(Pozycja pozycja);

    public Color rysowanie() { return kolor; }

    public int getSila() {
        return sila;
    }

    public int getInicjatywa() {
        return inicjatywa;
    }

    public int getWiek() {
        return wiek;
    }

    public boolean getZyje() {
        return zyje;
    }

    public boolean getPotomek() {
        return potomek;
    }

    public String getNazwa() {
        return nazwa;
    }

    public Pozycja getPozycja() {
        return pozycja;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSila(int sila) {
        this.sila = sila;
    }

    public void setWiek(int wiek) { this.wiek = wiek; }

    public void setInicjatywa(int inicjatywa) {
        this.inicjatywa = inicjatywa;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setPozycja(Pozycja nowaPozycja) {
        this.pozycja = nowaPozycja;
    }

    public void setZyje() {
        zyje = false;
    }

    public void setPotomek(boolean potomek) {
        potomek = potomek;
    }

    public void wzmocnijOrganizm(int sila) {
        this.sila += sila;
    }

    public void postarzOrganizm() {
        this.wiek++;
    }
}
