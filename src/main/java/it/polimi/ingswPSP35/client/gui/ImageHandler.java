package it.polimi.ingswPSP35.client.gui;

import javax.swing.*;

public class ImageHandler {

    public static ImageIcon getImage(String name, int colour)
    {
        name = name.toUpperCase();

        switch (name){
            case "W0":{
                switch (colour){
                    case 0:{
                        ImageIcon icon = new ImageIcon(ImageHandler.class.getResource("/img/WR0.PNG"));
                        return icon;}
                    case 1:{
                        ImageIcon icon = new ImageIcon(ImageHandler.class.getResource("/img/WG0.PNG"));
                        return icon;
                    }
                    case 2:{
                        ImageIcon icon = new ImageIcon(ImageHandler.class.getResource("/img/WB0.PNG"));
                        return icon;
                    }
                }
            }
            case "W1":{
                switch (colour){
                    case 0:{
                        ImageIcon icon = new ImageIcon(ImageHandler.class.getResource("/img/WR1.PNG"));
                        return icon;}
                    case 1:{
                        ImageIcon icon = new ImageIcon(ImageHandler.class.getResource("/img/WG1.PNG"));
                        return icon;
                    }
                    case 2:{
                        ImageIcon icon = new ImageIcon(ImageHandler.class.getResource("/img/WB1.PNG"));
                        return icon;
                    }
                }
            }

            case "W2":{
                switch (colour){
                    case 0:{
                        ImageIcon icon = new ImageIcon(ImageHandler.class.getResource("/img/WR2.PNG"));
                        return icon;}
                    case 1:{
                        ImageIcon icon = new ImageIcon(ImageHandler.class.getResource("/img/WG2.PNG"));
                        return icon;
                    }
                    case 2:{
                        ImageIcon icon = new ImageIcon(ImageHandler.class.getResource("/img/WB2.PNG"));
                        return icon;
                    }
                }
            }

            case "W3":{
                switch (colour){
                    case 0:{
                        ImageIcon icon = new ImageIcon(ImageHandler.class.getResource("/img/WR3.PNG"));
                        return icon;}
                    case 1:{
                        ImageIcon icon = new ImageIcon(ImageHandler.class.getResource("/img/WG3.PNG"));
                        return icon;
                    }
                    case 2:{
                        ImageIcon icon = new ImageIcon(ImageHandler.class.getResource("/img/WB3.PNG"));
                        return icon;
                    }
                }
            }

            default:
                ImageIcon icon = new ImageIcon(ImageHandler.class.getResource("/img/" + name + ".png"));
                return icon;
        }



    }
}