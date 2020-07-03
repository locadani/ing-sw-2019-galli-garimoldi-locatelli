package it.polimi.ingswPSP35.client.gui;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.Map;

public class OthersDivinitiesPanel extends JPanel {


    public OthersDivinitiesPanel(Map<String, String> divinities, Map<String, String> colors){

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
            Color textColor;
            try {
                Field field = Class.forName("java.awt.Color").getField(colors.get(entry.getKey()));
                textColor = (Color)field.get(null);
            } catch (Exception e) {
                textColor = Color.black;
            }
            username.setForeground(textColor);
            usernameBackground.add(username);
            divinityPanel.add(usernameBackground, BorderLayout.SOUTH);

        }
    }
}
