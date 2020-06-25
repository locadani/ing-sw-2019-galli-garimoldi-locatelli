package it.polimi.ingswPSP35.client;

public class CellInfo {

    private int height;
    private int colour;
    private String piece;

    public CellInfo()
    {
        height = 0;
        colour = -1;
        piece = "E";
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getColour() {
        return colour;
    }

    public void setColour(int colour) {
        this.colour = colour;
    }

    public String getPiece() {
        return piece;
    }

    public void setPiece(String piece) {
        this.piece = piece;
    }

    public String print()
    {
        String result = piece;
        if(piece.equals("W"))
        {
             result = addColour(result);
        }
        result = result + height;
        return result;
    }

    private String addColour(String string)
    {
        switch (colour) {
            case 0:
                string = AnsiCode.RED + string + AnsiCode.RESET;
                break;
            case 1:
                string = AnsiCode.GREEN + string + AnsiCode.RESET;
                break;
            case 2:
                string = AnsiCode.BLUE + string + AnsiCode.RESET;
                break;
        }
        return string;
    }
}
