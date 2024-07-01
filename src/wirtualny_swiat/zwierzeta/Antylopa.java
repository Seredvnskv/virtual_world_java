package wirtualny_swiat.zwierzeta;

import wirtualny_swiat.Organizm;
import wirtualny_swiat.Pozycja;
import wirtualny_swiat.Swiat;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Antylopa extends Zwierze {

    public Antylopa(Pozycja pozycja, Swiat swiat) {
        super(swiat, "Antylopa", pozycja, 4, 4, "A", new Color(102, 51, 0));
    }

    private int[][] kierunek() {
        return new int[][] {{2,0}, {-2,0}, {0,-2}, {0,2}};
    }

    @Override
    public Antylopa dziecko(Pozycja pozycja) {
        return new Antylopa(pozycja, swiat);
    }

    @Override
    public void akcja() {
        Pozycja staraPozycja = getPozycja();
        Pozycja nowaPozycja = randomRuch(getPozycja());

        swiat.dodajEvent(this.getNazwa() + " sila " + this.getSila() + " przemieszcza się z " + staraPozycja + " na " + nowaPozycja);

        kolizja(nowaPozycja);
        postarzOrganizm();
    }

    @Override
    public void kolizja(Pozycja pozycja) {
        Random random = new Random();
        boolean czyUcieka = random.nextBoolean();

        Organizm target = swiat.getPoleMapa(pozycja);

        if (target != null && target != this) {
            if (Objects.equals(target.getNazwa(), this.getNazwa())) {
                if (target.getWiek() > 5 && this.getWiek() > 5) {
                    Pozycja pozycjaDziecka = swiat.znajdzPozycje(pozycja);

                    if (pozycjaDziecka != null) {
                        Organizm dziecko = dziecko(pozycjaDziecka);
                        dziecko.setPotomek(true);
                        swiat.dodajNowyOrganizm(dziecko);
                        swiat.dodajEvent("Nowe dziecko " + dziecko.getNazwa() + " na pozycji " + pozycjaDziecka);
                    }
                    else {
                        swiat.dodajEvent("Nie ma miejsca na rozmnożenie!");
                        return;
                    }
                }
                else {
                    return;
                }
            }
            else if (czyUcieka && (target instanceof Zwierze)) {
                Pozycja ucieczkaPozycja = swiat.znajdzPozycje(pozycja);
                if (ucieczkaPozycja != null) {
                    swiat.dodajEvent(this.getNazwa() + " ucieka przed " + target.getNazwa() + " z pozycji " + pozycja + " na pozycję " + ucieczkaPozycja);
                    swiat.setPoleMapa(this.pozycja, null);
                    this.pozycja = ucieczkaPozycja;
                    swiat.setPoleMapa(this.pozycja, this);
                } else {
                    swiat.dodajEvent(this.getNazwa() + " nie znalazł miejsca do ucieczki i walczy z " + target.getNazwa());
                    walka(target);
                }
            }
            else {
                walka(target);
            }
        }
        else {
            swiat.setPoleMapa(this.pozycja,null);
            this.pozycja = new Pozycja(pozycja);
            swiat.setPoleMapa(this.pozycja, this);
        }
    }

    private Pozycja randomRuch(Pozycja pozycja) {
        List<Pozycja> kierunki = new ArrayList<>();

        for (int[] k : kierunek()) {
            Pozycja nowaPozycja = new Pozycja(pozycja.getX() + k[0], pozycja.getY() + k[1]);
            if (swiat.czyMapa(nowaPozycja)) {
                kierunki.add(nowaPozycja);
            }
        }

        if (!kierunki.isEmpty()) {
            Random random = new Random();
            return kierunki.get(random.nextInt(kierunki.size()));
        }

        return pozycja;
    }

    private void walka(Organizm target) {
        if (Objects.equals(target.getNazwa(), "Guarana")) {
            swiat.dodajEvent(this.getNazwa() + " sila " + this.getSila() + " zjadl " + target.getNazwa() + " na pozycji " + pozycja);
            this.wzmocnijOrganizm(3);
            target.setZyje();
            swiat.setPoleMapa(target.getPozycja(), this);
            swiat.setPoleMapa(this.pozycja, null);
            this.pozycja = new Pozycja(target.getPozycja());
        }
        else if (Objects.equals(target.getNazwa(), "Wilcze_Jagody")) {
            swiat.dodajEvent(this.getNazwa() + " sila " + this.getSila() + " zjadl " + target.getNazwa() + " na pozycji " + pozycja + " i umarł");
            target.setZyje();
            this.setZyje();
            swiat.setPoleMapa(target.getPozycja(), null);
            swiat.setPoleMapa(this.pozycja, null);
        }
        else if (Objects.equals(target.getNazwa(), "Barszcz_Sosnowskiego")) {
            swiat.dodajEvent(this.getNazwa() + " sila " + this.getSila() + " zjadl " + target.getNazwa() + " na pozycji " + pozycja + " i umarł");
            target.setZyje();
            this.setZyje();
            swiat.setPoleMapa(target.getPozycja(), null);
            swiat.setPoleMapa(this.pozycja, null);
        }
        else if (this.getSila() > target.getSila()) {
            swiat.dodajEvent(this.getNazwa() + " sila " + this.getSila() + " zabil " + target.getNazwa() + " sila " + target.getSila() + " na pozcyji " + pozycja);
            target.setZyje();
            swiat.setPoleMapa(target.getPozycja(), this);
            swiat.setPoleMapa(this.pozycja, null);
            this.pozycja = new Pozycja(target.getPozycja());
        }
        else if (target.getSila() > this.getSila()) {
            swiat.dodajEvent(target.getNazwa() + " sila " + target.getSila() + " zabil " + this.getNazwa() + " sila " + this.getSila() + " na pozcyji " + pozycja);
            this.setZyje();
            swiat.setPoleMapa(this.pozycja, null);
        }
    }
}
