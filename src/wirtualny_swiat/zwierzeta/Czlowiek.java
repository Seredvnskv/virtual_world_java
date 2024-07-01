package wirtualny_swiat.zwierzeta;

import wirtualny_swiat.Organizm;
import wirtualny_swiat.Pozycja;
import wirtualny_swiat.Swiat;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Czlowiek extends Zwierze {

    private int pozostaleTury;
    private int turyTrwania;
    private boolean aktywnaUmiejetnosc;

    public Czlowiek(Pozycja pozycja, Swiat swiat) {
        super(swiat, "Czlowiek", pozycja, 5, 4, "C", new Color(153,153,255));
        this.turyTrwania = 0;
        this.pozostaleTury = 0;
        this.aktywnaUmiejetnosc = false;
    }

    private void setAktywnaUmiejetnosc() {
        if (aktywnaUmiejetnosc) {
            swiat.dodajEvent("Umiejętność jest już aktywna");
        }
        else if (pozostaleTury > 0) {
            swiat.dodajEvent("Umiejętność będzie dostępna za " + pozostaleTury + " tury");
        }
        else {
            aktywnaUmiejetnosc = true;
            turyTrwania = 5;
            pozostaleTury = 10;
            swiat.dodajEvent(this.getNazwa() + " aktywuje umiejętność specjalną nieśmiertelność");
        }
    }

    @Override
    public void akcja() {
        Pozycja staraPozycja = getPozycja();
        int x = getPozycja().getX();
        int y = getPozycja().getY();
        int klawisz = swiat.getKlawisz();

        if (klawisz == KeyEvent.VK_UP) {
            y--;
        }
        else if (klawisz == KeyEvent.VK_DOWN) {
            y++;
        }
        else if (klawisz == KeyEvent.VK_RIGHT) {
            x++;
        }
        else if (klawisz == KeyEvent.VK_LEFT) {
            x--;
        }
        else if (klawisz == KeyEvent.VK_SPACE) {
            setAktywnaUmiejetnosc();
        }

        Pozycja nowaPozycja = new Pozycja(x, y);

        if (swiat.czyMapa(nowaPozycja)) {
            swiat.dodajEvent(this.getNazwa() + " sila " + this.getSila() + " przemieszcza się z " + staraPozycja + " na " + nowaPozycja);
            kolizja(nowaPozycja);
        }

        if (aktywnaUmiejetnosc) {
            turyTrwania--;
            if (turyTrwania == 0) {
                aktywnaUmiejetnosc = false;
                swiat.dodajEvent(this.getNazwa() + " umiejętność zakończyła swoje działanie");
            }
        }

        if (pozostaleTury > 0) {
            pozostaleTury--;
        }
    }

    @Override
    public void kolizja(Pozycja pozycja) {
        Organizm target = swiat.getPoleMapa(pozycja);

        if (target != null && target != this) {
            if (aktywnaUmiejetnosc && (target.getSila() > this.getSila())) {
                Pozycja nowaPozycja = swiat.znajdzPozycje(pozycja);

                if (nowaPozycja != null) {
                    swiat.dodajEvent(this.getNazwa() + " ucieka z pola " + pozycja + " na pole " + nowaPozycja);
                    swiat.setPoleMapa(this.pozycja, null);
                    this.pozycja = nowaPozycja;
                    swiat.setPoleMapa(this.pozycja, this);
                }
                else {
                    swiat.dodajEvent(this.getNazwa() + " nie może uciec ale nie ginie pozostaje na polu " + pozycja);
                    return;
                }
            }
            else {
                super.kolizja(pozycja);
            }
        }
        else {
            swiat.setPoleMapa(this.pozycja,null);
            this.pozycja = new Pozycja(pozycja);
            swiat.setPoleMapa(this.pozycja, this);
        }
    }

    @Override
    public Czlowiek dziecko(Pozycja pozycja) {
        return null;
    }

    public boolean getAktywnaUmiejetnosc() {
        return aktywnaUmiejetnosc;
    }

    public int getPozostaleTury() {
        return pozostaleTury;
    }

    public int getTuryTrwania() {
        return turyTrwania;
    }

    public void setUmiejetnosc(boolean aktywnaUmiejetnosc, int pozostaleTury, int turyTrwania) {
        this.aktywnaUmiejetnosc = aktywnaUmiejetnosc;
        this.pozostaleTury = pozostaleTury;
        this.turyTrwania = turyTrwania;
    }
}
