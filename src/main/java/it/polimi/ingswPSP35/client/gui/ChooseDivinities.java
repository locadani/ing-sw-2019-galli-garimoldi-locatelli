package it.polimi.ingswPSP35.client.gui;

import it.polimi.ingswPSP35.client.Info;
import it.polimi.ingswPSP35.client.MatchInfo;
import it.polimi.ingswPSP35.client.ServerHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ChooseDivinities extends JPanel implements ActionListener {

    private static final int LARG = 640;
    private static final int ALT = 640;
    private List<String> divinitiesList;
    private String pickedDivinity;
    private String selectedDivinity;
    private ButtonGroup buttons;
    private ServerHandler serverHandler;
    private MatchInfo matchInfo;

    public ChooseDivinities(List<String> divinitiesList, ServerHandler serverHandler, MatchInfo matchInfo) {

        this.matchInfo = matchInfo;
        this.serverHandler = serverHandler;
        this.divinitiesList = divinitiesList;
        this.setSize(LARG, ALT);
        this.setOpaque(false);
        this.setLayout(new BorderLayout());


        JPanel divinities = new JPanel();
        divinities.setOpaque(false);
        divinities.setLayout(new GridLayout(3, 3));
        this.add(divinities, BorderLayout.CENTER);

        JPanel stringpanel = new JPanel();
        stringpanel.setOpaque(false);
        stringpanel.setLayout(new FlowLayout());
        this.add(stringpanel, BorderLayout.NORTH);

        buttons = new ButtonGroup();

        JLabel string = new JLabel("Choose your divinity for the next game:");
        stringpanel.add(string);

        JPanel ap = new JPanel();
        ap.setOpaque(false);
        ap.setLayout(new FlowLayout());
        divinities.add(ap);

        JLabel ollo = new JLabel(new ImageIcon(getClass().getResource("/01.png")));
        ap.add(ollo);
        JRadioButton apollo = new JRadioButton("APOLLO");
        buttons.add(apollo);
        apollo.setActionCommand("Apollo");
        if (!divinitiesList.contains("Apollo")) {
            apollo.setEnabled(false);
        }
        ap.add(apollo);

        JPanel ar = new JPanel();
        ar.setOpaque(false);
        ar.setLayout(new FlowLayout());
        divinities.add(ar);

        JLabel temis = new JLabel(new ImageIcon((getClass().getResource("/02.png"))));
        ar.add(temis);
        JRadioButton artemis = new JRadioButton("ARTEMIS");
        artemis.setActionCommand("Artemis");
        buttons.add(artemis);
        if (!divinitiesList.contains("Artemis"))
            artemis.setEnabled(false);
        ar.add(artemis);


        JPanel at = new JPanel();
        at.setOpaque(false);
        at.setLayout(new FlowLayout());
        divinities.add(at);

        JLabel hena = new JLabel(new ImageIcon(getClass().getResource("/03.png")));
        at.add(hena);
        JRadioButton athena = new JRadioButton("ATHENA");
        athena.setActionCommand("Athena");
        buttons.add(athena);
        if (!divinitiesList.contains("Athena"))
            athena.setEnabled(false);
        at.add(athena);

        JPanel atl = new JPanel();
        atl.setOpaque(false);
        atl.setLayout(new FlowLayout());
        divinities.add(atl);

        JLabel as = new JLabel(new ImageIcon(getClass().getResource("/04.png")));
        atl.add(as);
        JRadioButton atlas = new JRadioButton("ATLAS");
        atlas.setActionCommand("Atlas");
        buttons.add(atlas);
        if (!divinitiesList.contains("Atlas"))
            atlas.setEnabled(false);
        atl.add(atlas);

        JPanel de = new JPanel();
        de.setOpaque(false);
        de.setLayout(new FlowLayout());
        divinities.add(de);

        JLabel meter = new JLabel(new ImageIcon(getClass().getResource("/05.png")));
        de.add(meter);
        JRadioButton demeter = new JRadioButton("DEMETER");
        demeter.setActionCommand("Demeter");
        buttons.add(demeter);
        if (!divinitiesList.contains("Demeter"))
            demeter.setEnabled(false);
        de.add(demeter);

        JPanel he = new JPanel();
        he.setOpaque(false);
        he.setLayout(new FlowLayout());
        divinities.add(he);

        JLabel phaestus = new JLabel(new ImageIcon(getClass().getResource("/06.png")));
        he.add(phaestus);
        JRadioButton hephaestus = new JRadioButton("HEPHAESTUS");
        hephaestus.setActionCommand("Hephaestus");
        buttons.add(hephaestus);
        if (!divinitiesList.contains("Hephaestus"))
            hephaestus.setEnabled(false);
        he.add(hephaestus);

        JPanel mi = new JPanel();
        mi.setOpaque(false);
        mi.setLayout(new FlowLayout());
        divinities.add(mi);

        JLabel notaur = new JLabel(new ImageIcon(getClass().getResource("/08.png")));
        mi.add(notaur);
        JRadioButton minotaur = new JRadioButton("MINOTAUR");
        minotaur.setActionCommand("Minotaur");
        buttons.add(minotaur);
        if (!divinitiesList.contains("Minotaur"))
            minotaur.setEnabled(false);
        mi.add(minotaur);

        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new FlowLayout());
        divinities.add(p);

        JLabel an = new JLabel(new ImageIcon(getClass().getResource("/09.png")));
        p.add(an);
        JRadioButton pan = new JRadioButton("PAN");
        pan.setActionCommand("Pan");
        buttons.add(pan);
        if (!divinitiesList.contains("Pan"))
            pan.setEnabled(false);
        pan.addActionListener(this);
        p.add(pan);

        JPanel pro = new JPanel();
        pro.setOpaque(false);
        pro.setLayout(new FlowLayout());
        divinities.add(pro);

        JLabel metheus = new JLabel(new ImageIcon(getClass().getResource("/10.png")));
        pro.add(metheus);
        JRadioButton prometheus = new JRadioButton("PROMETHEUS");
        prometheus.setActionCommand("Prometheus");
        buttons.add(prometheus);
        if (!divinitiesList.contains("Prometheus"))
            prometheus.setEnabled(false);
        prometheus.addActionListener(this);
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

        if (buttons.getSelection() != null) {
            serverHandler.update(buttons.getSelection().getActionCommand());
            matchInfo.setPlayerDivinity(buttons.getSelection().getActionCommand());
            setVisible(false);
        }
    }


}