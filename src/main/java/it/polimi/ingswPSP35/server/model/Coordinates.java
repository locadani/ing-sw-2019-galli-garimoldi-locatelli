package it.polimi.ingswPSP35.server.model;

public class Coordinates {
    private final int r;
    private final int c;

    public Coordinates(int r, int c)
    {
        this.r = r;
        this.c = c;
    }

    public Coordinates(int value)
    {
        value--;
        r = value / 5;
        c = value % 5;
    }

    public Coordinates copy()
    {
        Coordinates copy;
        copy = new Coordinates(r,c);
        return copy;
    }

    public int getR()
    {
        return r;
    }

    public int getC()
    {
        return c;
    }

    public boolean equals(Coordinates coordinates) {
        return coordinates.getC() == c && coordinates.getR() == r;
    }
}
