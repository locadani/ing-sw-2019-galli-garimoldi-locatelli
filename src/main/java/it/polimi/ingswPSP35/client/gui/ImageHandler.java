package it.polimi.ingswPSP35.client.gui;

import javax.swing.*;

public class ImageHandler {

    public static ImageIcon getImage(String name)
    {
        name = name.toLowerCase();
        ImageIcon icon = new ImageIcon(ImageHandler.class.getResource("/img/" + name + ".png"), name.substring(0, name.length() - 2));
        return icon;
    }
}



/*private static final ImageIcon worker0 = new ImageIcon(ImageHandler.class.getResource("img/worker0.png"), "Worker");
    private static final ImageIcon worker1 = new ImageIcon(ImageHandler.class.getResource("img/worker1.png"), "Worker");
    private static final ImageIcon worker2 = new ImageIcon(ImageHandler.class.getResource("img/worker2.png"), "Worker");
    private static final ImageIcon worker3 = new ImageIcon(ImageHandler.class.getResource("img/worker3.png"), "Worker");
    private static final ImageIcon empty = new ImageIcon(ImageHandler.class.getResource("img/empty.png"), "Empty");
    private static final ImageIcon block1 = new ImageIcon(ImageHandler.class.getResource("img/b1.png"), "Block1");
    private static final ImageIcon block2 = new ImageIcon(ImageHandler.class.getResource("img/b2.png"), "Block2");
    private static final ImageIcon block3 = new ImageIcon(ImageHandler.class.getResource("img/b3.png"), "Block3");
    private static final ImageIcon dome0 = new ImageIcon(ImageHandler.class.getResource("img/dome0.png"), "Dome");
    private static final ImageIcon dome1 = new ImageIcon(ImageHandler.class.getResource("img/dome1.png"), "Dome");
    private static final ImageIcon dome2 = new ImageIcon(ImageHandler.class.getResource("img/dome2.png"), "Dome");
    private static final ImageIcon dome3 = new ImageIcon(ImageHandler.class.getResource("img/dome3.png"), "Dome");
*/