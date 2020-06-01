package it.polimi.ingswPSP35.client.gui;

import it.polimi.ingswPSP35.client.NetworkHandler;
import it.polimi.ingswPSP35.commons.MessageID;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SelectDivinities extends JPanel implements ActionListener {

    private static final int LARG = 640;
    private static final int ALT = 640;
    private List<String> selectedDivinities = new ArrayList<>();
    private List<String> chosenDivinities = new ArrayList<>();
    private int nPlayers;
    private NetworkHandler networkHandler;


    public SelectDivinities(int nPlayers, NetworkHandler networkHandler){


        this.networkHandler = networkHandler;
        this.nPlayers = nPlayers;
        this.setSize(LARG, ALT);
        this.setOpaque(false);
        this.setLayout(new BorderLayout());


        JPanel divinities = new JPanel();
        divinities.setOpaque(false);
        divinities.setLayout(new GridLayout(3,3));
        this.add(divinities, BorderLayout.CENTER);

        JPanel stringpanel = new JPanel();
        stringpanel.setOpaque(false);
        stringpanel.setLayout(new FlowLayout());
        this.add(stringpanel, BorderLayout.NORTH);

        JLabel string = new JLabel("Choose the divinities for the next game:");
        stringpanel.add(string);

        JPanel ap = new JPanel();
        ap.setOpaque(false);
        ap.setLayout(new FlowLayout());
        divinities.add(ap);

        JLabel ollo = new JLabel(new ImageIcon(getClass().getResource("/01.png")));
        ap.add(ollo);
        JCheckBox apollo = new JCheckBox("Apollo");
        apollo.addActionListener(new CheckBoxListener(apollo, selectedDivinities, nPlayers));
        ap.add(apollo);

        JPanel ar = new JPanel();
        ar.setOpaque(false);
        ar.setLayout(new FlowLayout());
        divinities.add(ar);

        JLabel temis = new JLabel(new ImageIcon((getClass().getResource("/02.png"))));
        ar.add(temis);
        JCheckBox artemis = new JCheckBox("Artemis");
        artemis.addActionListener(new CheckBoxListener(artemis, selectedDivinities, nPlayers));
        ar.add(artemis);


        JPanel at = new JPanel();
        at.setOpaque(false);
        at.setLayout(new FlowLayout());
        divinities.add(at);

        JLabel hena = new JLabel(new ImageIcon(getClass().getResource("/03.png")));
        at.add(hena);
        JCheckBox athena = new JCheckBox("Athena");
        athena.addActionListener(new CheckBoxListener(athena, selectedDivinities, nPlayers));
        at.add(athena);

        JPanel atl = new JPanel();
        atl.setOpaque(false);
        atl.setLayout(new FlowLayout());
        divinities.add(atl);

        JLabel as = new JLabel(new ImageIcon(getClass().getResource("/04.png")));
        atl.add(as);
        JCheckBox atlas = new JCheckBox("Atlas");
        atlas.addActionListener(new CheckBoxListener(atlas, selectedDivinities, nPlayers));
        atl.add(atlas);

        JPanel de = new JPanel();
        de.setOpaque(false);
        de.setLayout(new FlowLayout());
        divinities.add(de);

        JLabel meter = new JLabel(new ImageIcon(getClass().getResource("/05.png")));
        de.add(meter);
        JCheckBox demeter = new JCheckBox("Demeter");
        demeter.addActionListener(new CheckBoxListener(demeter, selectedDivinities, nPlayers));
        de.add(demeter);

        JPanel he = new JPanel();
        he.setOpaque(false);
        he.setLayout(new FlowLayout());
        divinities.add(he);

        JLabel phaestus = new JLabel(new ImageIcon(getClass().getResource("/06.png")));
        he.add(phaestus);
        JCheckBox hephaestus = new JCheckBox("Hephaestus");
        hephaestus.addActionListener(new CheckBoxListener(hephaestus, selectedDivinities, nPlayers));
        he.add(hephaestus);

        JPanel mi = new JPanel();
        mi.setOpaque(false);
        mi.setLayout(new FlowLayout());
        divinities.add(mi);

        JLabel notaur = new JLabel(new ImageIcon(getClass().getResource("/08.png")));
        mi.add(notaur);
        JCheckBox minotaur = new JCheckBox("Minotaur");
        minotaur.addActionListener(new CheckBoxListener(minotaur, selectedDivinities, nPlayers));
        mi.add(minotaur);

        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new FlowLayout());
        divinities.add(p);

        JLabel an = new JLabel(new ImageIcon(getClass().getResource("/09.png")));
        p.add(an);
        JCheckBox pan = new JCheckBox("Pan");
        pan.addActionListener(new CheckBoxListener(pan, selectedDivinities, nPlayers));
        p.add(pan);

        JPanel pro = new JPanel();
        pro.setOpaque(false);
        pro.setLayout(new FlowLayout());
        divinities.add(pro);

        JLabel metheus = new JLabel(new ImageIcon(getClass().getResource("/10.png")));
        pro.add(metheus);
        JCheckBox prometheus = new JCheckBox("Prometheus");
        prometheus.addActionListener(new CheckBoxListener(prometheus, selectedDivinities, nPlayers));
        pro.add(prometheus);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setOpaque(false);
        panel.setForeground(Color.BLACK);
        this.add(panel, BorderLayout.SOUTH);

        JButton next = new JButton("NEXT");
        next.setBackground(Color.GRAY);
        next.setForeground(Color.BLACK);
        next.addActionListener(this);
        panel.add(next);
        

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("NEXT") && selectedDivinities.size() == nPlayers){
            //TODO distinguere tra 2 e 3
            networkHandler.send(MessageID.CHOOSE2DIVINITIES, selectedDivinities);
            this.setVisible(false);
        }
    }
}
