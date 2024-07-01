package wirtualny_swiat.zwierzeta;

import wirtualny_swiat.Organizm;
import wirtualny_swiat.Pozycja;
import wirtualny_swiat.Swiat;
import wirtualny_swiat.rosliny.Guarana;

import java.awt.*;
import java.util.Objects;

public abstract class Zwierze extends Organizm {
    Zwierze(Swiat swiat, String nazwa, Pozycja pozycja, int sila, int inicjatywa, String symbol, Color kolor) {
        super(swiat, nazwa, pozycja, sila, inicjatywa, symbol, kolor);
    }

    public abstract Organizm dziecko(Pozycja pozycja);

    @Override
    public void akcja() {
        Pozycja staraPozycja = getPozycja();
        Pozycja nowaPozycja = swiat.randomRuch(getPozycja());

        swiat.dodajEvent(this.getNazwa() + " sila " + this.getSila() + " przemieszcza się z " + staraPozycja + " na " + nowaPozycja);

        kolizja(nowaPozycja);
        postarzOrganizm();
    }

    @Override
    public void kolizja(Pozycja pozycja) {
        Organizm target = swiat.getPoleMapa(pozycja);

        if (target != null && target != this) {
            if (Objects.equals(target.getNazwa(), this.getNazwa())) {
                if (target.getWiek() > 5 && this.getWiek() > 5) {
                    Pozycja pozycjaDziecka = swiat.znajdzPozycje(pozycja);

                    if (pozycjaDziecka != null) {
                        Organizm dziecko = dziecko(pozycjaDziecka);
                        dziecko.setPotomek(true);
                        swiat.dodajNowyOrganizm(dziecko);
                        swiat.dodajEvent("Nowe dziecko " + dziecko.getNazwa() + " na pozycji " + pozycjaDziecka);
                    }
                    else {
                        swiat.dodajEvent("Nie ma miejsca na rozmnożenie!");
                        return;
                    }
                }
                else {
                    return;
                }
            }
            else if (Objects.equals(target.getNazwa(), "Zółw") && this.getSila() < 5) {
                swiat.dodajEvent(target.getNazwa() + " sila " + target.getSila() + " odpiera atak " + this.getNazwa() + " sila " + this.getSila() + " na pozycji " + pozycja);
                return;
            }
            else if (Objects.equals(target.getNazwa(), "Guarana")) {
                swiat.dodajEvent(this.getNazwa() + " sila " + this.getSila() + " zjadl " + target.getNazwa() + " na pozycji " + pozycja);
                this.wzmocnijOrganizm(3);
                target.setZyje();
                swiat.setPoleMapa(target.getPozycja(), this);
                swiat.setPoleMapa(this.pozycja, null);
                this.pozycja = new Pozycja(target.getPozycja());
            }
            else if (Objects.equals(target.getNazwa(), "Wilcze_Jagody")) {
                swiat.dodajEvent(this.getNazwa() + " sila " + this.getSila() + " zjadl " + target.getNazwa() + " na pozycji " + pozycja + " i umarł");
                target.setZyje();
                this.setZyje();
                swiat.setPoleMapa(target.getPozycja(), null);
                swiat.setPoleMapa(this.pozycja, null);
            }
            else if (Objects.equals(target.getNazwa(), "Barszcz_Sosnowskiego")) {
                swiat.dodajEvent(this.getNazwa() + " sila " + this.getSila() + " zjadl " + target.getNazwa() + " na pozycji " + pozycja + " i umarł");
                target.setZyje();
                this.setZyje();
                swiat.setPoleMapa(target.getPozycja(), null);
                swiat.setPoleMapa(this.pozycja, null);
            }
            else if (this.getSila() > target.getSila()) {
                swiat.dodajEvent(this.getNazwa() + " sila " + this.getSila() + " zabil " + target.getNazwa() + " sila " + target.getSila() + " na pozcyji " + pozycja);
                target.setZyje();
                swiat.setPoleMapa(target.getPozycja(), this);
                swiat.setPoleMapa(this.pozycja, null);
                this.pozycja = new Pozycja(target.getPozycja());
            }
            else if (target.getSila() > this.getSila()) {
                swiat.dodajEvent(target.getNazwa() + " sila " + target.getSila() + " zabil " + this.getNazwa() + " sila " + this.getSila() + " na pozcyji " + pozycja);
                this.setZyje();
                swiat.setPoleMapa(this.pozycja, null);
            }
        }
        else {
            swiat.setPoleMapa(this.pozycja,null);
            this.pozycja = new Pozycja(pozycja);
            swiat.setPoleMapa(this.pozycja, this);
        }
    }
}
