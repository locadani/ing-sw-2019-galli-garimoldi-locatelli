package it.polimi.ingswPSP35.client.gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class OthersDivinitiesPanel extends JPanel {

    private Map<String, String> divinities;

    public OthersDivinitiesPanel(Map<String, String> divinities){

        this.divinities = divinities;
        this.setOpaque(false);
        this.setLayout(new FlowLayout());


        for(Map.Entry<String, String> entry : divinities.entrySet()) {
            JPanel divinityPanel = new JPanel();
            divinityPanel.setOpaque(false);
            divinityPanel.setLayout(new FlowLayout());
            this.add(divinityPanel);
            JLabel divinityLabel = new JLabel(new ImageIcon(getClass().getResource("/" + entry.getValue() + ".png")));
            divinityPanel.add(divinityLabel);
            JLabel username = new JLabel(entry.getKey());
            username.setOpaque(true);
            divinityPanel.add(username);
        }

        /*
        JLabel apollo = new JLabel(new ImageIcon(getClass().getResource("/01.png")));
        if(divinities.containsValue("Apollo"))
            this.add(apollo);

        JLabel artemis = new JLabel(new ImageIcon(getClass().getResource("/02.png")));
        if(divinities.containsValue("Artemis"))
            this.add(artemis);

        JLabel athena = new JLabel(new ImageIcon(getClass().getResource("/03.png")));
        if(divinities.containsValue("Athena"))
            this.add(athena);

        JLabel atlas = new JLabel(new ImageIcon(getClass().getResource("/04.png")));
        if(divinities.containsValue("Atlas"))
            this.add(atlas);

        JLabel demeter = new JLabel(new ImageIcon(getClass().getResource("/05.png")));
        if(divinities.containsValue("Demeter"))
            this.add(demeter);

        JLabel hephaestus = new JLabel(new ImageIcon(getClass().getResource("/06.png")));
        if(divinities.containsValue("Hephaestus"))
            this.add(hephaestus);

        JLabel minotaur = new JLabel(new ImageIcon(getClass().getResource("/08.png")));
        if(divinities.containsValue("Minotaur"))
            this.add(minotaur);

        JLabel pan = new JLabel(new ImageIcon(getClass().getResource("/09.png")));
        if(divinities.containsValue("Pan"))
            this.add(pan);

        JLabel prometheus = new JLabel(new ImageIcon(getClass().getResource("/10.png")));
        if(divinities.containsValue("Prometheus"))
            this.add(prometheus);
        */
    }
}
