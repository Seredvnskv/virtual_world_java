package wirtualny_swiat.rosliny;

import wirtualny_swiat.Pozycja;
import wirtualny_swiat.Swiat;

import java.awt.*;

public class Wilcze_Jagody extends Roslina {
    public Wilcze_Jagody(Pozycja pozycja, Swiat swiat) {
        super(swiat, "Wilcze_Jagody", pozycja, 99, 0, "-", new Color(51,0,102));
    }

    public Wilcze_Jagody dziecko(Pozycja pozycja) {
        return new Wilcze_Jagody(pozycja, swiat);
    }
}
