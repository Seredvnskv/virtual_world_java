package wirtualny_swiat.UI;

import javax.swing.*;

public class Tablica_Organizmy extends JOptionPane {
    private final String[] organizmy = {
            "W - WILK", "O - OWCA", "L - LIS", "A - ANTYLOPA", "Z - ZÓŁW", ". - TRAWA", "* - MLECZ", "+ - GUARANA", "- - WILCZE JAGODY",
            "# - BARSZCZ SOSNOWSKIEGO", "C - CZŁOWIEK"
    };

    public Tablica_Organizmy() {}

    public String nowyOrganizm() {
        return (String) showInputDialog(null,
                "Wybierz gatunek: ", "Dodawanie organizmu",
                JOptionPane.PLAIN_MESSAGE, null, organizmy, null);
    }
}
