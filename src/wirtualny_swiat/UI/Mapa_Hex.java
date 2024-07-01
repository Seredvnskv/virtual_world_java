package wirtualny_swiat.UI;

import wirtualny_swiat.Swiat;
import wirtualny_swiat.Pozycja;
import javax.swing.*;
import java.awt.*;

public class Mapa_Hex extends JPanel {
    public Mapa_Hex(Swiat swiat, UI okno) {
        setLayout(null);
        int wysokosc = 50;
        double wysokosc_trojkata = wysokosc * Math.sqrt(3) / 2;
        double offsetY = 0;
        double offsetX = 0;

        for (int y = 0; y < swiat.getWysokosc(); y++) {
            for (int x = 0; x < swiat.getSzerokosc(); x++) {
                JButton przycisk = new Hex_Przycisk(swiat, new Pozycja(x, y), okno, wysokosc);
                add(przycisk);
                przycisk.setBounds((int) offsetX, (int) offsetY, wysokosc, wysokosc);
                offsetX += wysokosc;
            }
            offsetY += wysokosc_trojkata;
            offsetX = (double) ((y + 1) * wysokosc) / 2;
        }
    }
}

