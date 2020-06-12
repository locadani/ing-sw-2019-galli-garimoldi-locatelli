package it.polimi.ingswPSP35.client.gui;

import it.polimi.ingswPSP35.client.MatchInfo;
import it.polimi.ingswPSP35.client.NetworkHandler;
import it.polimi.ingswPSP35.commons.MessageID;

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
    private NetworkHandler networkHandler;
    private MatchInfo matchInfo;

    public ChooseDivinities(List<String> divinitiesList, NetworkHandler networkHandler, MatchInfo matchInfo) {

        this.matchInfo = matchInfo;
        this.networkHandler = networkHandler;
        this.divinitiesList = divinitiesList;
        this.setSize(LARG, ALT);
        this.setOpaque(false);
        this.setLayout(new BorderLayout());


        JPanel divinities = new JPanel();
        divinities.setOpaque(false);
        divinities.setLayout(new GridLayout(1, 3));
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
        if (divinitiesList.contains("Apollo"))
            divinities.add(ap);

        JLabel ollo = new JLabel(new ImageIcon(getClass().getResource("/001.png")));
        ap.add(ollo);
        JRadioButton apollo = new JRadioButton("Apollo");
        buttons.add(apollo);
        apollo.setActionCommand("Apollo");
        if (!divinitiesList.contains("Apollo")) {
            apollo.setEnabled(false);
        }
        ap.add(apollo);

        JPanel ar = new JPanel();
        ar.setOpaque(false);
        ar.setLayout(new FlowLayout());
        if (divinitiesList.contains("Artemis"))
            divinities.add(ar);

        JLabel temis = new JLabel(new ImageIcon((getClass().getResource("/002.png"))));
        ar.add(temis);
        JRadioButton artemis = new JRadioButton("Artemis");
        artemis.setActionCommand("Artemis");
        buttons.add(artemis);
        if (!divinitiesList.contains("Artemis"))
            artemis.setEnabled(false);
        ar.add(artemis);


        JPanel at = new JPanel();
        at.setOpaque(false);
        at.setLayout(new FlowLayout());
        if (divinitiesList.contains("Athena"))
            divinities.add(at);

        JLabel hena = new JLabel(new ImageIcon(getClass().getResource("/003.png")));
        at.add(hena);
        JRadioButton athena = new JRadioButton("Athena");
        athena.setActionCommand("Athena");
        buttons.add(athena);
        if (!divinitiesList.contains("Athena"))
            athena.setEnabled(false);
        at.add(athena);

        JPanel atl = new JPanel();
        atl.setOpaque(false);
        atl.setLayout(new FlowLayout());
        if (divinitiesList.contains("Atlas"))
            divinities.add(atl);

        JLabel as = new JLabel(new ImageIcon(getClass().getResource("/004.png")));
        atl.add(as);
        JRadioButton atlas = new JRadioButton("Atlas");
        atlas.setActionCommand("Atlas");
        buttons.add(atlas);
        if (!divinitiesList.contains("Atlas"))
            atlas.setEnabled(false);
        atl.add(atlas);

        JPanel de = new JPanel();
        de.setOpaque(false);
        de.setLayout(new FlowLayout());
        if (divinitiesList.contains("Demeter"))
            divinities.add(de);

        JLabel meter = new JLabel(new ImageIcon(getClass().getResource("/005.png")));
        de.add(meter);
        JRadioButton demeter = new JRadioButton("Demeter");
        demeter.setActionCommand("Demeter");
        buttons.add(demeter);
        if (!divinitiesList.contains("Demeter"))
            demeter.setEnabled(false);
        de.add(demeter);

        JPanel he = new JPanel();
        he.setOpaque(false);
        he.setLayout(new FlowLayout());
        if (divinitiesList.contains("Hephaestus"))
            divinities.add(he);

        JLabel phaestus = new JLabel(new ImageIcon(getClass().getResource("/006.png")));
        he.add(phaestus);
        JRadioButton hephaestus = new JRadioButton("Hephaestus");
        hephaestus.setActionCommand("Hephaestus");
        buttons.add(hephaestus);
        if (!divinitiesList.contains("Hephaestus"))
            hephaestus.setEnabled(false);
        he.add(hephaestus);

        JPanel mi = new JPanel();
        mi.setOpaque(false);
        mi.setLayout(new FlowLayout());
        if (divinitiesList.contains("Minotaur"))
            divinities.add(mi);

        JLabel notaur = new JLabel(new ImageIcon(getClass().getResource("/008.png")));
        mi.add(notaur);
        JRadioButton minotaur = new JRadioButton("Minotaur");
        minotaur.setActionCommand("Minotaur");
        buttons.add(minotaur);
        if (!divinitiesList.contains("Minotaur"))
            minotaur.setEnabled(false);
        mi.add(minotaur);

        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new FlowLayout());
        if (divinitiesList.contains("Pan"))
            divinities.add(p);

        JLabel an = new JLabel(new ImageIcon(getClass().getResource("/009.png")));
        p.add(an);
        JRadioButton pan = new JRadioButton("Pan");
        pan.setActionCommand("Pan");
        buttons.add(pan);
        if (!divinitiesList.contains("Pan"))
            pan.setEnabled(false);
        p.add(pan);

        JPanel pro = new JPanel();
        pro.setOpaque(false);
        pro.setLayout(new FlowLayout());
        if (divinitiesList.contains("Prometheus"))
            divinities.add(pro);

        JLabel metheus = new JLabel(new ImageIcon(getClass().getResource("/010.png")));
        pro.add(metheus);
        JRadioButton prometheus = new JRadioButton("Prometheus");
        prometheus.setActionCommand("Prometheus");
        buttons.add(prometheus);
        if (!divinitiesList.contains("Prometheus"))
            prometheus.setEnabled(false);
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
            networkHandler.send(MessageID.PICKDIVINITY, buttons.getSelection().getActionCommand());
            setVisible(false);
        }
    }


}