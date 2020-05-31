package it.polimi.ingswPSP35.client.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CheckBoxListener implements ActionListener {

    private JCheckBox checkBox;
    private List<String> chosenDivinities;
    private int nPlayers;

    public CheckBoxListener(JCheckBox checkBox, List<String> chosenDivinities, int nPlayers) {
        this.checkBox = checkBox;
        this.chosenDivinities = chosenDivinities;
        this.nPlayers = nPlayers;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String divinity = e.getActionCommand();
        if (!chosenDivinities.contains(divinity)) {
            if (chosenDivinities.size() < nPlayers) {
                chosenDivinities.add(divinity);
            } else
                checkBox.setSelected(false);
        } else
            chosenDivinities.remove(divinity);
            /*if(chosenDivinities.size() < nPlayers){
                if(chosenDivinities.contains(divinity))
                    chosenDivinities.remove(divinity);
                else chosenDivinities.add(divinity);
            }
            else checkBox.setSelected(false);
        for (String diviniti : chosenDivinities) {
            System.out.println(diviniti);
        }*/
    }


}
