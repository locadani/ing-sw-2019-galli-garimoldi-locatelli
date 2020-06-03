package it.polimi.ingswPSP35.client.gui;

import javax.swing.*;
import java.awt.*;

public class MyDivinityPanel extends JPanel {

    private String divinity;

    private CardLayout cardLayout = new CardLayout();

    public MyDivinityPanel(String divinity){

        this.divinity = divinity;
        this.setOpaque(false);
        this.setLayout(cardLayout);

        JLabel apollo = new JLabel(new ImageIcon(getClass().getResource("/001.png")));
        this.add(apollo, "Apollo");

        JLabel artemis = new JLabel(new ImageIcon(getClass().getResource("/002.png")));
        this.add(artemis, "Artemis");

        JLabel athena = new JLabel(new ImageIcon(getClass().getResource("/003.png")));
        this.add(athena, "Athena");

        JLabel atlas = new JLabel(new ImageIcon(getClass().getResource("/004.png")));
        this.add(atlas, "Atlas");

        JLabel demeter = new JLabel(new ImageIcon(getClass().getResource("/005.png")));
        this.add(demeter, "Demeter");

        JLabel hephaestus = new JLabel(new ImageIcon(getClass().getResource("/006.png")));
        this.add(hephaestus, "Hephaestus");

        JLabel minotaur = new JLabel(new ImageIcon(getClass().getResource("/008.png")));
        this.add(minotaur, "Minotaur");

        JLabel pan = new JLabel(new ImageIcon(getClass().getResource("/009.png")));
        this.add(pan, "Pan");

        JLabel prometheus = new JLabel(new ImageIcon(getClass().getResource("/010.png")));
        this.add(prometheus, "Prometheus");

        cardLayout.show(this, divinity);
    }
}