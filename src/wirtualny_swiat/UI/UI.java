package wirtualny_swiat.UI;

import wirtualny_swiat.Organizm;
import wirtualny_swiat.Swiat;
import wirtualny_swiat.Pozycja;
import wirtualny_swiat.rosliny.*;
import wirtualny_swiat.zwierzeta.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class UI extends JFrame implements ActionListener, KeyListener {
    private JFrame okno;
    private JButton nowa_gra, zapisz, wczytaj, wyjdz, nastepna_tura;
    private JPanel mapa;
    public JTextArea event;
    private final int szrokosc_okna = 1600, wysokosc_okna = 800;
    private Swiat swiat;

    public UI() {
        okno = new JFrame("SAMBOR SEREDYNSKI 198035");
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        okno.setSize(szrokosc_okna, wysokosc_okna);
        okno.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

        nowa_gra = new JButton("NOWA GRA");
        zapisz = new JButton("ZAPISZ");
        wczytaj = new JButton("WCZYTAJ");
        wyjdz = new JButton("WYJDZ");
        nastepna_tura = new JButton("NASTEPNA TURA");

        nowa_gra.addActionListener(this);
        zapisz.addActionListener(this);
        wczytaj.addActionListener(this);
        wyjdz.addActionListener(this);
        nastepna_tura.addActionListener(this);

        panel.add(Box.createHorizontalGlue());
        panel.add(nowa_gra);
        panel.add(nastepna_tura);
        panel.add(zapisz);
        panel.add(wczytaj);
        panel.add(wyjdz);
        panel.add(Box.createHorizontalGlue());

        okno.add(panel, BorderLayout.NORTH);

        event = new JTextArea();
        event.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(event);
        scrollPane.setPreferredSize(new Dimension(500, wysokosc_okna));
        okno.add(scrollPane, BorderLayout.EAST);

        okno.addKeyListener(this);
        okno.setFocusable(true);
        okno.requestFocusInWindow();

        okno.setVisible(true);
    }

    public void rysujSwiat() {
        if (mapa != null) {
            okno.remove(mapa);
        }
        mapa = new JPanel(new GridLayout(swiat.getWysokosc(), swiat.getSzerokosc()));
        if (swiat.czyHex()) {
            mapa = new Mapa_Hex(swiat, this);
        } else {
            mapa = new Mapa_Krata(swiat, this);
        }
        okno.add(mapa, BorderLayout.CENTER);
        okno.validate();
        okno.repaint();
        okno.requestFocusInWindow();
    }

    private void tworzSwiat(int szerokosc, int wysokosc, boolean czyHex) {
        swiat = new Swiat(szerokosc, wysokosc, this);
        swiat.setHex(czyHex);
        dodajPoczatkoweOrganizmy();
        rysujSwiat();
        event.append("Nowy świat został stworzony o rozmiarach: " + szerokosc + "x" + wysokosc + ".\n");
    }

    private void nowaGra() {
        JTextField fieldSzerokosc = new JTextField(5);
        JTextField fieldWysokosc = new JTextField(5);
        JRadioButton krataButton = new JRadioButton("Krata", true);
        JRadioButton hexButton = new JRadioButton("Hex");
        ButtonGroup group = new ButtonGroup();
        group.add(krataButton);
        group.add(hexButton);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Szerokość:"));
        panel.add(fieldSzerokosc);
        panel.add(new JLabel("Wysokość:"));
        panel.add(fieldWysokosc);
        panel.add(krataButton);
        panel.add(hexButton);

        int result = JOptionPane.showConfirmDialog(null, panel, "Wprowadź rozmiary świata i wybierz typ planszy", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            int szerokosc = Integer.parseInt(fieldSzerokosc.getText());
            int wysokosc = Integer.parseInt(fieldWysokosc.getText());
            boolean czyHex = hexButton.isSelected();
            tworzSwiat(szerokosc, wysokosc, czyHex);
        }

        okno.requestFocusInWindow();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nowa_gra) {
            nowaGra();
        }
        else if (e.getSource() == wyjdz) {
            System.exit(0);
        }
        else if (e.getSource() == nastepna_tura) {
            swiat.wykonajTure();
        }
        else if (e.getSource() == zapisz) {
            zapiszGre();
        }
        else if (e.getSource() == wczytaj) {
            wczytajGre();
        }
        rysujSwiat();
    }

    private Pozycja losujPozycje() {
        Random random = new Random();
        int x, y;
        Pozycja pozycja;
        do {
            x = random.nextInt(swiat.getSzerokosc());
            y = random.nextInt(swiat.getWysokosc());
            pozycja = new Pozycja(x, y);
        } while (swiat.getPoleMapa(pozycja) != null);

        return pozycja;
    }

    private void dodajPoczatkoweOrganizmy() {
        swiat.dodajOrganizm("C", losujPozycje());

        swiat.dodajOrganizm("W", losujPozycje());
        swiat.dodajOrganizm("W", losujPozycje());
        swiat.dodajOrganizm("O", losujPozycje());
        swiat.dodajOrganizm("O", losujPozycje());
        swiat.dodajOrganizm("L", losujPozycje());
        swiat.dodajOrganizm("L", losujPozycje());
        swiat.dodajOrganizm("A", losujPozycje());
        swiat.dodajOrganizm("A", losujPozycje());
        swiat.dodajOrganizm("Z", losujPozycje());
        swiat.dodajOrganizm("Z", losujPozycje());

        swiat.dodajOrganizm(".", losujPozycje());
        swiat.dodajOrganizm(".", losujPozycje());
        swiat.dodajOrganizm("*", losujPozycje());
        swiat.dodajOrganizm("*", losujPozycje());
        swiat.dodajOrganizm("-", losujPozycje());
        swiat.dodajOrganizm("-", losujPozycje());
        swiat.dodajOrganizm("+", losujPozycje());
        swiat.dodajOrganizm("+", losujPozycje());
        swiat.dodajOrganizm("#", losujPozycje());
        swiat.dodajOrganizm("#", losujPozycje());
    }

    public void addEvent(String event) {
        this.event.append(event + "\n");
    }

    public void clearEvent() {
        event.setText("");
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (swiat != null) {
            if (swiat.czyCzlowiek()) {
                swiat.setKlawisz(e.getKeyCode());
            }
            swiat.wykonajTure();
            rysujSwiat();
            swiat.setKlawisz(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private void zapiszGre() {
        try (PrintWriter file = new PrintWriter(new FileWriter("savegame.txt"))) {
            file.println(swiat.getSzerokosc() + " " + swiat.getWysokosc() + " " + swiat.czyHex() + " " + swiat.getTura());
            ArrayList<Organizm> organizmy = swiat.getList();
            for (Organizm organizm : organizmy) {
                file.print(organizm.getSymbol() + " "
                        + organizm.getPozycja().getX() + " "
                        + organizm.getPozycja().getY() + " "
                        + organizm.getNazwa() + " "
                        + organizm.getSila() + " "
                        + organizm.getWiek() + " "
                );

                if (organizm instanceof Czlowiek czlowiek) {
                    file.print(czlowiek.getAktywnaUmiejetnosc() + " "
                            + czlowiek.getPozostaleTury() + " "
                            + czlowiek.getTuryTrwania() + " ");
                }

                file.println();
            }
            swiat.dodajEvent("ZAPISANO STAN GRY");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void wczytajGre() {
        try (BufferedReader file = new BufferedReader(new FileReader("savegame.txt"))) {
            String[] line = file.readLine().split(" ");
            int szerokosc = Integer.parseInt(line[0]);
            int wysokosc = Integer.parseInt(line[1]);
            boolean hex = Boolean.parseBoolean(line[2]);
            int tura = Integer.parseInt(line[3]);

            swiat = new Swiat(szerokosc, wysokosc, this);
            swiat.setHex(hex);
            swiat.setTura(tura);

            String currentLine;
            while ((currentLine = file.readLine()) != null) {
                line = currentLine.split(" ");
                String symbol = line[0];
                int x = Integer.parseInt(line[1]);
                int y = Integer.parseInt(line[2]);
                String nazwa = line[3];
                int sila = Integer.parseInt(line[4]);
                int wiek = Integer.parseInt(line[5]);

                Pozycja pozycja = new Pozycja(x, y);

                if ("Czlowiek".equals(nazwa)) {
                    boolean aktywnaUmiejetnosc  = Boolean.parseBoolean(line[6]);
                    int pozostaleTury = Integer.parseInt(line[7]);
                    int turyTrwania = Integer.parseInt(line[8]);
                    Czlowiek czlowiek = new Czlowiek(pozycja, swiat);
                    czlowiek.setSila(sila);
                    czlowiek.setWiek(wiek);
                    czlowiek.setUmiejetnosc(aktywnaUmiejetnosc, pozostaleTury, turyTrwania);
                    swiat.dodajNowyOrganizm(czlowiek);
                }
                else {
                    Organizm organizm = switch(symbol) {
                        case "W" -> new Wilk(pozycja, swiat);
                        case "O" -> new Owca(pozycja, swiat);
                        case "L" -> new Lis(pozycja, swiat);
                        case "A" -> new Antylopa(pozycja, swiat);
                        case "Z" -> new Zolw(pozycja, swiat);
                        case "C" -> new Czlowiek(pozycja, swiat);
                        case "." -> new Trawa(pozycja, swiat);
                        case "*" -> new Mlecz(pozycja, swiat);
                        case "+" -> new Guarana(pozycja, swiat);
                        case "-" -> new Wilcze_Jagody(pozycja, swiat);
                        case "#" -> new Barszcz_Sosnowskiego(pozycja, swiat);
                        default -> null;
                    };

                    if (organizm != null) {
                        organizm.setWiek(wiek);
                        organizm.setSila(sila);
                        swiat.dodajNowyOrganizm(organizm);
                    }
                }
            }
            swiat.dodajEvent("WCZYTANO STAN GRY");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
