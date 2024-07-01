package wirtualny_swiat;

import wirtualny_swiat.rosliny.*;
import wirtualny_swiat.zwierzeta.*;
import wirtualny_swiat.UI.*;

import java.io.*;
import java.util.*;

public class Swiat {
    private List<Organizm> organizmy = new ArrayList<>();
    private Organizm[][] mapa;
    private int szerokosc;
    private int wysokosc;
    private int tura;
    private boolean czyHex;
    private boolean czyCzlowiek;
    private int klawisz;
    private int cooldown = 0;
    private UI ui;

    public Swiat(int szerokosc, int wysokosc, UI ui) {
        this.szerokosc = szerokosc;
        this.wysokosc = wysokosc;
        this.mapa = new Organizm[wysokosc][szerokosc];
        this.czyHex = false;
        this.czyCzlowiek = false;
        this.ui = ui;
    }

    public int getKlawisz() {
        return klawisz;
    }

    public void setKlawisz(int klawisz) {
        this.klawisz = klawisz;
    }

    public int[][] kierunek() {
        return new int[][] {{1,0}, {-1,0}, {0,-1}, {0,1}};
    }

    public Organizm getPoleMapa(Pozycja pozycja) {
        return mapa[pozycja.getY()][pozycja.getX()];
    }

    public void setPoleMapa(Pozycja pozycja, Organizm organizm) {
        mapa[pozycja.getY()][pozycja.getX()] = organizm;
    }

    public ArrayList<Organizm> getList() {
        return (ArrayList<Organizm>) organizmy;
    }

    public int getTura() {
        return tura;
    }

    public void setTura(int tura) {
        this.tura = tura;
    }

    public int getSzerokosc() {
        return szerokosc;
    }

    public int getWysokosc() {
        return wysokosc;
    }

    public boolean czyHex() {
        return czyHex;
    }

    public boolean czyCzlowiek() {
        return czyCzlowiek;
    }

    public void setHex(boolean hex) {
        czyHex = hex;
    }

    public boolean czyMapa(Pozycja pozycja) {
        return (pozycja.getX() < getSzerokosc() && pozycja.getX() >= 0 && pozycja.getY() < getWysokosc() && pozycja.getY() >= 0);
    }

    public Pozycja znajdzPozycje(Pozycja pozycja) {
        List<int[]> kierunki = new ArrayList<>(Arrays.asList(kierunek()));
        Collections.shuffle(kierunki);

        for (int[] k : kierunki) {
            Pozycja p = new Pozycja(pozycja.getX() + k[0], pozycja.getY() + k[1]);
            if (czyMapa(p) && getPoleMapa(p) == null) {
                return p;
            }
        }

        return null;
    }

    public Pozycja randomRuch(Pozycja pozycja) {
        List<Pozycja> kierunki = new ArrayList<>();

        for (int[] k : kierunek()) {
            Pozycja nowaPozycja = new Pozycja(pozycja.getX() + k[0], pozycja.getY() + k[1]);
            if (czyMapa(nowaPozycja)) {
                kierunki.add(nowaPozycja);
            }
        }

        if (!kierunki.isEmpty()) {
            Random random = new Random();
            return kierunki.get(random.nextInt(kierunki.size()));
        }

        return pozycja;
    }

    public void dodajNowyOrganizm(Organizm organizm) {
        Pozycja pozycja = organizm.getPozycja();

        if (czyMapa(pozycja)) {
            if (getPoleMapa(pozycja) == null) {
                if (organizm instanceof Czlowiek) {
                    czyCzlowiek = true;
                }
                organizmy.add(organizm);
                setPoleMapa(pozycja, organizm);
            } else {
                throw new Error("BŁĄD");
            }
        }
    }

    public void dodajOrganizm(String symbol, Pozycja pozycja) {
        Organizm organizm = null;

        symbol = String.valueOf(symbol.charAt(0));

        organizm = switch (symbol) {
            case "W" -> new Wilk(pozycja, this);
            case "O" -> new Owca(pozycja, this);
            case "L" -> new Lis(pozycja, this);
            case "A" -> new Antylopa(pozycja, this);
            case "Z" -> new Zolw(pozycja, this);
            case "C" -> new Czlowiek(pozycja, this);
            case "." -> new Trawa(pozycja, this);
            case "*" -> new Mlecz(pozycja, this);
            case "+" -> new Guarana(pozycja, this);
            case "-" -> new Wilcze_Jagody(pozycja, this);
            case "#" -> new Barszcz_Sosnowskiego(pozycja, this);
            default -> organizm;
        };

        if (organizm != null) {
            dodajNowyOrganizm(organizm);
        }
    }

    private void kolejnoscRuchu() {
        organizmy.sort((a, b) -> {
            if (a.getInicjatywa() == b.getInicjatywa()) {
                return Integer.compare(b.getWiek(), a.getWiek());
            }
            return Integer.compare(b.getInicjatywa(), a.getInicjatywa());
        });
    }

    private void usunOrganizm() {
        for (int i = 0; i < organizmy.size(); i++) {
            Organizm target = organizmy.get(i);
            if (!target.getZyje()) {
                if (target instanceof Czlowiek) {
                    czyCzlowiek = false;
                }
                organizmy.remove(target);
            }
        }
    }

    public void wykonajTure() {
        ui.clearEvent();

        kolejnoscRuchu();

        for (Organizm organizm : organizmy) {
            if (organizm.getPotomek()) {
                organizm.setPotomek(false);
            }
        }

        for (int i = 0; i < organizmy.size(); i++) {
            Organizm organizm = organizmy.get(i);
            if (!organizm.getPotomek() && organizm.getZyje()){
                organizm.akcja();
            }
        }

        usunOrganizm();
        tura++;
    }

    public void dodajEvent(String event) {
        if (ui != null) {
            ui.addEvent(event);
        }
    }
}
