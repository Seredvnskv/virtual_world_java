package wirtualny_swiat.rosliny;

import wirtualny_swiat.Organizm;
import wirtualny_swiat.Pozycja;
import wirtualny_swiat.Swiat;

import java.awt.*;
import java.util.Random;

public abstract class Roslina extends Organizm {
    Roslina(Swiat swiat, String nazwa, Pozycja pozycja, int sila, int inicjatywa, String symbol, Color kolor) {
        super(swiat, nazwa, pozycja, sila, inicjatywa, symbol, kolor);
    }

    @Override
    public void kolizja(Pozycja pozycja) { }

    @Override
    public void akcja() {
        Random random = new Random();
        int rozsianie = random.nextInt(10);

        if (rozsianie == 0) {
            Pozycja nowaPozycja = swiat.znajdzPozycje(pozycja);
            if (nowaPozycja != null) {
                Organizm dziecko = dziecko(nowaPozycja);
                dziecko.setPotomek(true);
                swiat.dodajNowyOrganizm(dziecko);
                swiat.dodajEvent(getNazwa() + " rozsiewa się z pozycji " + pozycja + " na pozyjcę " + nowaPozycja);
            }
        }
    }
}
