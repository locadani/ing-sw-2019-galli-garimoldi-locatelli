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
            divinityPanel.setLayout(new BorderLayout());
            this.add(divinityPanel);

            JLabel divinityLabel = new JLabel(new ImageIcon(getClass().getResource("/" + entry.getValue() + ".png")));
            divinityPanel.add(divinityLabel, BorderLayout.CENTER);

            JLabel usernameBackground = new JLabel(new ImageIcon(getClass().getResource("/username.png")));
            usernameBackground.setLayout(new FlowLayout());
            JLabel username = new JLabel(entry.getKey());
            usernameBackground.add(username);
            divinityPanel.add(usernameBackground, BorderLayout.SOUTH);

        }
    }
}
