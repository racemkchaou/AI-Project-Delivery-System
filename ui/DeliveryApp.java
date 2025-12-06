package ui;

import javax.swing.*;
import java.awt.*;

public class DeliveryApp extends JPanel {

    private GridPanel gridPanel;
    private ControlPanel controlPanel;
    private MainApp mainApp;

    public DeliveryApp(MainApp mainApp) {
        this.mainApp = mainApp;
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        // Créer le panneau de la grille
        gridPanel = new GridPanel();

        // Créer le panneau de contrôle
        controlPanel = new ControlPanel(gridPanel, mainApp);

        // Limiter la hauteur maximale du control panel
        controlPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
        controlPanel.setPreferredSize(new Dimension(controlPanel.getPreferredSize().width, 220));

        // Panneau principal
        setBackground(new Color(245, 245, 245));

        // Ajouter un ScrollPane pour la grille avec plus d'espace
        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.setPreferredSize(new Dimension(800, 600));

        // Wrapper pour le control panel
        JScrollPane controlScrollPane = new JScrollPane(controlPanel);
        controlScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        controlScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        controlScrollPane.setBorder(null);
        controlScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
        controlScrollPane.setPreferredSize(new Dimension(controlScrollPane.getPreferredSize().width, 220));

        // Assembler les composants
        add(controlScrollPane, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}