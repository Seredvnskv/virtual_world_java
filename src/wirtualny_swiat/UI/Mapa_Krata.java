package wirtualny_swiat.UI;

import wirtualny_swiat.Swiat;
import wirtualny_swiat.Pozycja;
import javax.swing.*;
import java.awt.*;

public class Mapa_Krata extends JPanel {
    public Mapa_Krata(Swiat swiat, UI okno) {
        setLayout(new GridLayout(swiat.getWysokosc(), swiat.getSzerokosc()));
        for (int y = 0; y < swiat.getWysokosc(); y++) {
            for (int x = 0; x < swiat.getSzerokosc(); x++) {
                Krata_Przycisk przycisk = new Krata_Przycisk(swiat, new Pozycja(x, y), okno);
                add(przycisk);
            }
        }
    }
}
