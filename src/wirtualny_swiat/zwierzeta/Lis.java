package wirtualny_swiat.zwierzeta;

import wirtualny_swiat.Pozycja;
import wirtualny_swiat.Swiat;
import wirtualny_swiat.Organizm;

import java.awt.*;

public class Lis extends Zwierze {
    public Lis(Pozycja pozycja, Swiat swiat) {
        super(swiat, "Lis", pozycja, 3, 7, "L", new Color(209, 62, 17));
    }

    @Override
    public Lis dziecko(Pozycja pozycja) {
        return new Lis(pozycja, swiat);
    }

    @Override
    public void akcja() {
        Pozycja staraPozycja = getPozycja();
        Pozycja nowaPozycja = swiat.randomRuch(staraPozycja);

        Organizm target = swiat.getPoleMapa(nowaPozycja);

        if (target != null && target.getSila() > this.getSila()) {
            swiat.dodajEvent(this.getNazwa() + " sila " + this.getSila() + " pozostaje na pozycji " + staraPozycja);
            postarzOrganizm();
            return;
        }
        else {
            swiat.dodajEvent(this.getNazwa() + " sila " + this.getSila() + " przemieszcza się z " + staraPozycja + " na " + nowaPozycja);
            super.kolizja(nowaPozycja);
            postarzOrganizm();
        }
    }
}
