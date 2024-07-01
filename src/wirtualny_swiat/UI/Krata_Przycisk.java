package wirtualny_swiat.UI;

import wirtualny_swiat.Swiat;
import wirtualny_swiat.Pozycja;
import wirtualny_swiat.Organizm;
import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.metal.MetalButtonUI;

public class Krata_Przycisk extends JButton {
    protected Pozycja pozycja;
    protected Swiat swiat;
    protected UI okno;

    public Krata_Przycisk(Swiat swiat, Pozycja pozycja, UI okno) {
        this.swiat = swiat;
        this.pozycja = pozycja;
        this.okno = okno;

        setToolTipText(String.valueOf(pozycja));
        Organizm p = swiat.getPoleMapa(pozycja);
        if (p != null) {
            setText(p.getSymbol());
            setEnabled(false);
            setBackground(p.rysowanie());
            setUI(new MetalButtonUI() {
                protected Color getDisabledTextColor() {
                    return Color.WHITE;
                }
            });
        } else {
            addActionListener(e -> {
                String organizm = new Tablica_Organizmy().nowyOrganizm();
                if (organizm != null) {
                    swiat.dodajOrganizm(organizm, pozycja);
                }
                okno.rysujSwiat();
            });
            setBackground(Color.WHITE);
            setFocusable(false);
        }
    }

}
