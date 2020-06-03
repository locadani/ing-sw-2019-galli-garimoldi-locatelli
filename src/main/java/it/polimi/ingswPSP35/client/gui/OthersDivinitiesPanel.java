package it.polimi.ingswPSP35.client.gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class OthersDivinitiesPanel extends JPanel {

    private List<String> divinities;

    public OthersDivinitiesPanel(List<String> divinities){

        this.divinities = divinities;
        this.setOpaque(false);
        this.setLayout(new FlowLayout());

        JLabel apollo = new JLabel(new ImageIcon(getClass().getResource("/01.png")));
        if(divinities.contains("Apollo"))
            this.add(apollo);

        JLabel artemis = new JLabel(new ImageIcon(getClass().getResource("/02.png")));
        if(divinities.contains("Artemis"))
            this.add(artemis);

        JLabel athena = new JLabel(new ImageIcon(getClass().getResource("/03.png")));
        if(divinities.contains("Athena"))
            this.add(athena);

        JLabel atlas = new JLabel(new ImageIcon(getClass().getResource("/04.png")));
        if(divinities.contains("Atlas"))
            this.add(atlas);

        JLabel demeter = new JLabel(new ImageIcon(getClass().getResource("/05.png")));
        if(divinities.contains("Demeter"))
            this.add(demeter);

        JLabel hephaestus = new JLabel(new ImageIcon(getClass().getResource("/06.png")));
        if(divinities.contains("Hephaestus"))
            this.add(hephaestus);

        JLabel minotaur = new JLabel(new ImageIcon(getClass().getResource("/08.png")));
        if(divinities.contains("Minotaur"))
            this.add(minotaur);

        JLabel pan = new JLabel(new ImageIcon(getClass().getResource("/09.png")));
        if(divinities.contains("Pan"))
            this.add(pan);

        JLabel prometheus = new JLabel(new ImageIcon(getClass().getResource("/10.png")));
        if(divinities.contains("Prometheus"))
            this.add(prometheus);
    }
}
