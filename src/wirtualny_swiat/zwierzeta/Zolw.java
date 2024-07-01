package wirtualny_swiat.zwierzeta;

import wirtualny_swiat.Pozycja;
import wirtualny_swiat.Swiat;

import java.awt.*;
import java.util.Random;

public class Zolw extends Zwierze {
    public Zolw(Pozycja pozycja, Swiat swiat) {
        super(swiat, "Zółw", pozycja, 2, 1, "Z",new Color(45, 64, 38));
    }

    @Override
    public Zolw dziecko(Pozycja pozycja) {
        return new Zolw(pozycja, swiat);
    }

    @Override
    public void akcja() {
        Random random = new Random();
        int ruch = random.nextInt(4);

        if (ruch == 0) {
            super.akcja();
        }
    }
}
