package wirtualny_swiat.rosliny;

import wirtualny_swiat.Organizm;
import wirtualny_swiat.Pozycja;
import wirtualny_swiat.Swiat;
import java.awt.*;

public class Trawa extends Roslina {
    public Trawa(Pozycja pozycja, Swiat swiat) {
        super(swiat, "Trawa", pozycja, 0, 0, ".", new Color(34,139,34));
    }

    public Trawa dziecko(Pozycja pozycja) {
        return new Trawa(pozycja, swiat);
    }
}
