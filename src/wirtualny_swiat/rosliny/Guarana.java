package wirtualny_swiat.rosliny;

import wirtualny_swiat.Pozycja;
import wirtualny_swiat.Swiat;

import java.awt.*;

public class Guarana extends Roslina {
    public Guarana(Pozycja pozycja, Swiat swiat) {
        super(swiat, "Guarana", pozycja, 0, 0, "+", new Color(204,0,0));
    }

    public Guarana dziecko(Pozycja pozycja) {
        return new Guarana(pozycja, swiat);
    }
}
