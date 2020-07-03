package it.polimi.ingswPSP35.client.gui;

import javax.swing.*;

public class ImageHandler {

    public static ImageIcon getImage(String name, int colour) {
        name = name.toUpperCase();


        switch (name) {
            //Handles workers on floor
            case "W0": {
                switch (colour) {
                    case 0: {
                        ImageIcon icon = new ImageIcon(ImageHandler.class.getResource("/img/WR0.PNG"));
                        return icon;
                    }
                    case 1: {
                        ImageIcon icon = new ImageIcon(ImageHandler.class.getResource("/img/WG0.PNG"));
                        return icon;
                    }
                    case 2: {
                        ImageIcon icon = new ImageIcon(ImageHandler.class.getResource("/img/WB0.PNG"));
                        return icon;
                    }
                }
            }

            //handles worker on first level
            case "W1": {
                switch (colour) {
                    case 0: {
                        ImageIcon icon = new ImageIcon(ImageHandler.class.getResource("/img/WR1.PNG"));
                        return icon;
                    }
                    case 1: {
                        ImageIcon icon = new ImageIcon(ImageHandler.class.getResource("/img/WG1.PNG"));
                        return icon;
                    }
                    case 2: {
                        ImageIcon icon = new ImageIcon(ImageHandler.class.getResource("/img/WB1.PNG"));
                        return icon;
                    }
                }
            }

            //handles worker on second level
            case "W2": {
                switch (colour) {
                    case 0: {
                        ImageIcon icon = new ImageIcon(ImageHandler.class.getResource("/img/WR2.PNG"));
                        return icon;
                    }
                    case 1: {
                        ImageIcon icon = new ImageIcon(ImageHandler.class.getResource("/img/WG2.PNG"));
                        return icon;
                    }
                    case 2: {
                        ImageIcon icon = new ImageIcon(ImageHandler.class.getResource("/img/WB2.PNG"));
                        return icon;
                    }
                }
            }

            //handles worker on third level
            case "W3": {
                switch (colour) {
                    case 0: {
                        ImageIcon icon = new ImageIcon(ImageHandler.class.getResource("/img/WR3.PNG"));
                        return icon;
                    }
                    case 1: {
                        ImageIcon icon = new ImageIcon(ImageHandler.class.getResource("/img/WG3.PNG"));
                        return icon;
                    }
                    case 2: {
                        ImageIcon icon = new ImageIcon(ImageHandler.class.getResource("/img/WB3.PNG"));
                        return icon;
                    }
                }
            }

            //handles empty blocks
            case "B1":
                ImageIcon icon = new ImageIcon(ImageHandler.class.getResource("/img/1B.png"));
                return icon;

            case "B2":
                icon = new ImageIcon(ImageHandler.class.getResource("/img/2B.png"));
                return icon;

            case "B3":
                icon = new ImageIcon(ImageHandler.class.getResource("/img/3B.png"));
                return icon;

            //handles everything but workers
            default:
                icon = new ImageIcon(ImageHandler.class.getResource("/img/" + name + ".png"));
                return icon;
        }


    }
}