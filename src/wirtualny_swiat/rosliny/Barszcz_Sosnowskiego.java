package wirtualny_swiat.rosliny;

import wirtualny_swiat.Organizm;
import wirtualny_swiat.Pozycja;
import wirtualny_swiat.Swiat;
import wirtualny_swiat.zwierzeta.Zwierze;
import wirtualny_swiat.zwierzeta.Czlowiek;

import java.awt.*;

public class Barszcz_Sosnowskiego extends Roslina {
    public Barszcz_Sosnowskiego(Pozycja pozycja, Swiat swiat) {
        super(swiat, "Barszcz_Sosnowskiego", pozycja, 10, 0, "#", new Color(0,102,102));
    }

    @Override
    public void akcja() {
        int[][] kierunek = swiat.kierunek();

        for (int i = 0; i < kierunek.length; i++) {
            Pozycja zabijPozycja = new Pozycja(getPozycja().getX() + kierunek[i][0], getPozycja().getY() + kierunek[i][1]);
            if (swiat.czyMapa(zabijPozycja)) {
                Organizm target = swiat.getPoleMapa(zabijPozycja);
                if (target instanceof Zwierze && !(target instanceof Czlowiek && ((Czlowiek)target).getAktywnaUmiejetnosc())) {
                    target.setZyje();
                    swiat.setPoleMapa(target.getPozycja(), null);
                    swiat.dodajEvent(this.getNazwa() + " eliminuje " + target.getNazwa() + " na pozycji " + target.getPozycja());
                }
            }
        }

        super.akcja();
    }

    public Barszcz_Sosnowskiego dziecko(Pozycja pozycja) {
        return new Barszcz_Sosnowskiego(pozycja, swiat);
    }
}
