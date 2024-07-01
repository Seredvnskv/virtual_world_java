package wirtualny_swiat;

public class Pozycja {
    private final int x;
    private final int y;

    public Pozycja(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Pozycja(Pozycja pozycja) {
        this.x = pozycja.getX();
        this.y = pozycja.getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ')';
    }
}
