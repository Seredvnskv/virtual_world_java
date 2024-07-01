package wirtualny_swiat.zwierzeta;

import wirtualny_swiat.Pozycja;
import wirtualny_swiat.Swiat;

import java.awt.*;

public class Wilk extends Zwierze {
    public Wilk(Pozycja pozycja, Swiat swiat) {
        super(swiat, "Wilk", pozycja, 9, 5, "W",new Color(20, 20, 20));
    }

    @Override
    public Wilk dziecko(Pozycja pozycja) {
        return new Wilk(pozycja, swiat);
    }
}
