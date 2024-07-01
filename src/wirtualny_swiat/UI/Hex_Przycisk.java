package wirtualny_swiat.UI;

import wirtualny_swiat.Swiat;
import wirtualny_swiat.Pozycja;
import java.awt.*;

public class Hex_Przycisk extends Krata_Przycisk {
    private final double bok, wysokosc;
    private final Polygon polygon;
    private Font font; // Dodanie członka klasy dla czcionki

    public Hex_Przycisk(Swiat swiat, Pozycja pozycja, UI okno, double wysokosc) {
        super(swiat, pozycja, okno);
        this.wysokosc = wysokosc;
        bok = wysokosc / 2;
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setText(null);
        polygon = new Polygon();
        for (int i = 0; i < 6; i++) {
            polygon.addPoint((int) (bok + bok * Math.cos(i * Math.PI / 3 - Math.PI / 6)), (int) (bok + bok * Math.sin(i * Math.PI / 3 - Math.PI / 6)));
        }

        // Ustawienie czcionki
        font = new Font("Arial", Font.BOLD, (int) (0.5 * wysokosc)); // Rozmiar czcionki zależny od wysokości
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (swiat.getPoleMapa(pozycja) != null) {
            g.setColor(swiat.getPoleMapa(pozycja).rysowanie());
        } else {
            g.setColor(Color.WHITE);
        }
        g.fillPolygon(polygon);
        if (swiat.getPoleMapa(pozycja) != null) {
            g.setColor(Color.WHITE);
            g.setFont(font); // Ustawienie czcionki
            g.drawString(swiat.getPoleMapa(pozycja).getSymbol(), (int) (0.3 * wysokosc), (int) (1.3 * bok));
        }
    }

    @Override
    public boolean contains(int x, int y) {
        return polygon.contains(x, y);
    }
}