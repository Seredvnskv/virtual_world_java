package wirtualny_swiat.rosliny;

import wirtualny_swiat.Pozycja;
import wirtualny_swiat.Swiat;

import java.awt.*;

public class Mlecz extends Roslina {
    public Mlecz(Pozycja pozycja, Swiat swiat) {
        super(swiat, "Mlecz", pozycja, 0, 0, "*", new Color(204,204,0));
    }

    public Mlecz dziecko(Pozycja pozycja) {
        return new Mlecz(pozycja, swiat);
    }

    @Override
    public void akcja() {
        for (int i = 0; i < 3; i++) {
            super.akcja();
        }
    }
}
