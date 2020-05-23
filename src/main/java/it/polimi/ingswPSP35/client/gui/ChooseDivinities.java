package it.polimi.ingswPSP35.client.gui;

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

    public ChooseDivinities(List<String> divinitiesList) {

        this.divinitiesList = divinitiesList;
        this.setSize(LARG, ALT);
        this.setOpaque(false);
        this.setLayout(new BorderLayout());

        ButtonGroup buttons = new ButtonGroup();

        JPanel divinities = new JPanel();
        divinities.setOpaque(false);
        divinities.setLayout(new GridLayout(3, 3));
        this.add(divinities, BorderLayout.CENTER);

        JPanel stringpanel = new JPanel();
        stringpanel.setOpaque(false);
        stringpanel.setLayout(new FlowLayout());
        this.add(stringpanel, BorderLayout.NORTH);

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
        if (!divinitiesList.contains("apollo")) {
            apollo.setEnabled(false);
        }
        apollo.addActionListener(this);
        ap.add(apollo);

        JPanel ar = new JPanel();
        ar.setOpaque(false);
        ar.setLayout(new FlowLayout());
        divinities.add(ar);

        JLabel temis = new JLabel(new ImageIcon((getClass().getResource("/02.png"))));
        ar.add(temis);
        JRadioButton artemis = new JRadioButton("ARTEMIS");
        buttons.add(artemis);
        if (!divinitiesList.contains("artemis"))
            artemis.setEnabled(false);
        artemis.addActionListener(this);
        ar.add(artemis);


        JPanel at = new JPanel();
        at.setOpaque(false);
        at.setLayout(new FlowLayout());
        divinities.add(at);

        JLabel hena = new JLabel(new ImageIcon(getClass().getResource("/03.png")));
        at.add(hena);
        JRadioButton athena = new JRadioButton("ATHENA");
        buttons.add(athena);
        if (!divinitiesList.contains("athena"))
            athena.setEnabled(false);
        athena.addActionListener(this);
        at.add(athena);

        JPanel atl = new JPanel();
        atl.setOpaque(false);
        atl.setLayout(new FlowLayout());
        divinities.add(atl);

        JLabel as = new JLabel(new ImageIcon(getClass().getResource("/04.png")));
        atl.add(as);
        JRadioButton atlas = new JRadioButton("ATLAS");
        buttons.add(atlas);
        if (!divinitiesList.contains("atlas"))
            atlas.setEnabled(false);
        atlas.addActionListener(this);
        atl.add(atlas);

        JPanel de = new JPanel();
        de.setOpaque(false);
        de.setLayout(new FlowLayout());
        divinities.add(de);

        JLabel meter = new JLabel(new ImageIcon(getClass().getResource("/05.png")));
        de.add(meter);
        JRadioButton demeter = new JRadioButton("DEMETER");
        buttons.add(demeter);
        if (!divinitiesList.contains("demeter"))
            demeter.setEnabled(false);
        demeter.addActionListener(this);
        de.add(demeter);

        JPanel he = new JPanel();
        he.setOpaque(false);
        he.setLayout(new FlowLayout());
        divinities.add(he);

        JLabel phaestus = new JLabel(new ImageIcon(getClass().getResource("/06.png")));
        he.add(phaestus);
        JRadioButton hephaestus = new JRadioButton("HEPHAESTUS");
        buttons.add(hephaestus);
        if (!divinitiesList.contains("hephaestus"))
            hephaestus.setEnabled(false);
        hephaestus.addActionListener(this);
        he.add(hephaestus);

        JPanel mi = new JPanel();
        mi.setOpaque(false);
        mi.setLayout(new FlowLayout());
        divinities.add(mi);

        JLabel notaur = new JLabel(new ImageIcon(getClass().getResource("/08.png")));
        mi.add(notaur);
        JRadioButton minotaur = new JRadioButton("MINOTAUR");
        buttons.add(minotaur);
        if (!divinitiesList.contains("minotaur"))
            minotaur.setEnabled(false);
        minotaur.addActionListener(this);
        mi.add(minotaur);

        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new FlowLayout());
        divinities.add(p);

        JLabel an = new JLabel(new ImageIcon(getClass().getResource("/09.png")));
        p.add(an);
        JRadioButton pan = new JRadioButton("PAN");
        buttons.add(pan);
        if (!divinitiesList.contains("pan"))
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
        buttons.add(prometheus);
        if (!divinitiesList.contains("prometheus"))
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
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pickedDivinity = selectedDivinity;
                setVisible(false);
            }
        });
        panel.add(next);


    }

    @Override
    public void actionPerformed(ActionEvent e) {

        selectedDivinity = e.getActionCommand();

    }

    public String getPickedDivinity() {

        return this.pickedDivinity;
    }

}