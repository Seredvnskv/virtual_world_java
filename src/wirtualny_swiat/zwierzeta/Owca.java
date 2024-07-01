package wirtualny_swiat.zwierzeta;

import wirtualny_swiat.Pozycja;
import wirtualny_swiat.Swiat;

import java.awt.*;

public class Owca extends Zwierze {
    public Owca(Pozycja pozycja, Swiat swiat) {
        super(swiat, "Owca", pozycja, 4, 4, "O", new Color(157, 158, 155));
    }

    @Override
    public Owca dziecko(Pozycja pozycja) {
        return new Owca(pozycja, swiat);
    }
}
