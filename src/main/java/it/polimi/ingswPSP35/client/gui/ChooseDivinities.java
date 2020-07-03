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
    private ButtonGroup buttons;
    private NetworkHandler networkHandler;

    public ChooseDivinities(List<String> divinitiesList, NetworkHandler networkHandler) {

        this.networkHandler = networkHandler;
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

        JPanel apolloPanel = new JPanel();
        apolloPanel.setOpaque(false);
        apolloPanel.setLayout(new FlowLayout());
        if (divinitiesList.contains("Apollo"))
            divinities.add(apolloPanel);

        JLabel apolloLabel = new JLabel(new ImageIcon(getClass().getResource("/001.png")));
        apolloPanel.add(apolloLabel);
        JRadioButton apollo = new JRadioButton("Apollo");
        buttons.add(apollo);
        apollo.setActionCommand("Apollo");
        if (!divinitiesList.contains("Apollo")) {
            apollo.setEnabled(false);
        }
        apolloPanel.add(apollo);

        JPanel aresPanel = new JPanel();
        aresPanel.setOpaque(false);
        aresPanel.setLayout(new FlowLayout());
        if (divinitiesList.contains("Ares"))
            divinities.add(aresPanel);

        JLabel aresLabel = new JLabel(new ImageIcon(getClass().getResource("/012.png")));
        aresPanel.add(aresLabel);
        JRadioButton ares = new JRadioButton("Ares");
        buttons.add(ares);
        ares.setActionCommand("Ares");
        if (!divinitiesList.contains("Ares")) {
            ares.setEnabled(false);
        }
        aresPanel.add(ares);

        JPanel artemisPanel = new JPanel();
        artemisPanel.setOpaque(false);
        artemisPanel.setLayout(new FlowLayout());
        if (divinitiesList.contains("Artemis"))
            divinities.add(artemisPanel);

        JLabel artemisLabel = new JLabel(new ImageIcon((getClass().getResource("/002.png"))));
        artemisPanel.add(artemisLabel);
        JRadioButton artemis = new JRadioButton("Artemis");
        artemis.setActionCommand("Artemis");
        buttons.add(artemis);
        if (!divinitiesList.contains("Artemis"))
            artemis.setEnabled(false);
        artemisPanel.add(artemis);


        JPanel athenaPanel = new JPanel();
        athenaPanel.setOpaque(false);
        athenaPanel.setLayout(new FlowLayout());
        if (divinitiesList.contains("Athena"))
            divinities.add(athenaPanel);

        JLabel athenaLabel = new JLabel(new ImageIcon(getClass().getResource("/003.png")));
        athenaPanel.add(athenaLabel);
        JRadioButton athena = new JRadioButton("Athena");
        athena.setActionCommand("Athena");
        buttons.add(athena);
        if (!divinitiesList.contains("Athena"))
            athena.setEnabled(false);
        athenaPanel.add(athena);

        JPanel atlasPanel = new JPanel();
        atlasPanel.setOpaque(false);
        atlasPanel.setLayout(new FlowLayout());
        if (divinitiesList.contains("Atlas"))
            divinities.add(atlasPanel);

        JLabel atlasLabel = new JLabel(new ImageIcon(getClass().getResource("/004.png")));
        atlasPanel.add(atlasLabel);
        JRadioButton atlas = new JRadioButton("Atlas");
        atlas.setActionCommand("Atlas");
        buttons.add(atlas);
        if (!divinitiesList.contains("Atlas"))
            atlas.setEnabled(false);
        atlasPanel.add(atlas);

        JPanel charonPanel = new JPanel();
        charonPanel.setOpaque(false);
        charonPanel.setLayout(new FlowLayout());
        if (divinitiesList.contains("Charon"))
            divinities.add(charonPanel);

        JLabel charonLabel = new JLabel(new ImageIcon(getClass().getResource("/015.png")));
        charonPanel.add(charonLabel);
        JRadioButton charon = new JRadioButton("Charon");
        buttons.add(charon);
        charon.setActionCommand("Charon");
        if (!divinitiesList.contains("Charon")) {
            charon.setEnabled(false);
        }
        charonPanel.add(charon);

        JPanel demeterPanel = new JPanel();
        demeterPanel.setOpaque(false);
        demeterPanel.setLayout(new FlowLayout());
        if (divinitiesList.contains("Demeter"))
            divinities.add(demeterPanel);

        JLabel demeterLabel = new JLabel(new ImageIcon(getClass().getResource("/005.png")));
        demeterPanel.add(demeterLabel);
        JRadioButton demeter = new JRadioButton("Demeter");
        demeter.setActionCommand("Demeter");
        buttons.add(demeter);
        if (!divinitiesList.contains("Demeter"))
            demeter.setEnabled(false);
        demeterPanel.add(demeter);

        JPanel hephaestusPanel = new JPanel();
        hephaestusPanel.setOpaque(false);
        hephaestusPanel.setLayout(new FlowLayout());
        if (divinitiesList.contains("Hephaestus"))
            divinities.add(hephaestusPanel);

        JLabel hephaestusLabel = new JLabel(new ImageIcon(getClass().getResource("/006.png")));
        hephaestusPanel.add(hephaestusLabel);
        JRadioButton hephaestus = new JRadioButton("Hephaestus");
        hephaestus.setActionCommand("Hephaestus");
        buttons.add(hephaestus);
        if (!divinitiesList.contains("Hephaestus"))
            hephaestus.setEnabled(false);
        hephaestusPanel.add(hephaestus);

        JPanel heraPanel = new JPanel();
        heraPanel.setOpaque(false);
        heraPanel.setLayout(new FlowLayout());
        if (divinitiesList.contains("Hera"))
            divinities.add(heraPanel);

        JLabel heraLabel = new JLabel(new ImageIcon(getClass().getResource("/020.png")));
        heraPanel.add(heraLabel);
        JRadioButton hera = new JRadioButton("Hera");
        buttons.add(hera);
        hera.setActionCommand("Hera");
        if (!divinitiesList.contains("Hera")) {
            hera.setEnabled(false);
        }
        heraPanel.add(hera);

        JPanel hestiaPanel = new JPanel();
        hestiaPanel.setOpaque(false);
        hestiaPanel.setLayout(new FlowLayout());
        if (divinitiesList.contains("Hestia"))
            divinities.add(hestiaPanel);

        JLabel hestiaLabel = new JLabel(new ImageIcon(getClass().getResource("/021.png")));
        hestiaPanel.add(hestiaLabel);
        JRadioButton hestia = new JRadioButton("Hestia");
        buttons.add(hestia);
        hestia.setActionCommand("Hestia");
        if (!divinitiesList.contains("Hestia")) {
            hestia.setEnabled(false);
        }
        hestiaPanel.add(hestia);

        JPanel limusPanel = new JPanel();
        limusPanel.setOpaque(false);
        limusPanel.setLayout(new FlowLayout());
        if (divinitiesList.contains("Limus"))
            divinities.add(limusPanel);

        JLabel limusLabel = new JLabel(new ImageIcon(getClass().getResource("/023.png")));
        limusPanel.add(limusLabel);
        JRadioButton limus = new JRadioButton("Limus");
        buttons.add(limus);
        limus.setActionCommand("Limus");
        if (!divinitiesList.contains("Limus")) {
            limus.setEnabled(false);
        }
        limusPanel.add(limus);

        JPanel minotaurPanel = new JPanel();
        minotaurPanel.setOpaque(false);
        minotaurPanel.setLayout(new FlowLayout());
        if (divinitiesList.contains("Minotaur"))
            divinities.add(minotaurPanel);

        JLabel minotaurLabel = new JLabel(new ImageIcon(getClass().getResource("/008.png")));
        minotaurPanel.add(minotaurLabel);
        JRadioButton minotaur = new JRadioButton("Minotaur");
        minotaur.setActionCommand("Minotaur");
        buttons.add(minotaur);
        if (!divinitiesList.contains("Minotaur"))
            minotaur.setEnabled(false);
        minotaurPanel.add(minotaur);

        JPanel panPanel = new JPanel();
        panPanel.setOpaque(false);
        panPanel.setLayout(new FlowLayout());
        if (divinitiesList.contains("Pan"))
            divinities.add(panPanel);

        JLabel panLabel = new JLabel(new ImageIcon(getClass().getResource("/009.png")));
        panPanel.add(panLabel);
        JRadioButton pan = new JRadioButton("Pan");
        pan.setActionCommand("Pan");
        buttons.add(pan);
        if (!divinitiesList.contains("Pan"))
            pan.setEnabled(false);
        panPanel.add(pan);

        JPanel prometheusPanel = new JPanel();
        prometheusPanel.setOpaque(false);
        prometheusPanel.setLayout(new FlowLayout());
        if (divinitiesList.contains("Prometheus"))
            divinities.add(prometheusPanel);

        JLabel prometheusLabel = new JLabel(new ImageIcon(getClass().getResource("/010.png")));
        prometheusPanel.add(prometheusLabel);
        JRadioButton prometheus = new JRadioButton("Prometheus");
        prometheus.setActionCommand("Prometheus");
        buttons.add(prometheus);
        if (!divinitiesList.contains("Prometheus"))
            prometheus.setEnabled(false);
        prometheusPanel.add(prometheus);

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