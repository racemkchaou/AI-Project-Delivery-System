package ui;

import javax.swing.*;
import java.awt.*;

public class DeliveryApp extends JFrame {

    private GridPanel gridPanel;
    private ControlPanel controlPanel;

    public DeliveryApp() {
        setTitle("Système de Livraison - Application");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        // Créer le panneau de la grille
        gridPanel = new GridPanel();

        // Créer le panneau de contrôle
        controlPanel = new ControlPanel(gridPanel);

        // Panneau principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));

        // Ajouter un ScrollPane pour la grille
        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Assembler les composants
        mainPanel.add(controlPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    public static void main(String[] args) {
        // Utiliser le Look and Feel du système
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Lancer l'application
        SwingUtilities.invokeLater(() -> {
            DeliveryApp app = new DeliveryApp();
            app.setExtendedState(JFrame.MAXIMIZED_BOTH);
            app.setVisible(true);
        });
    }
}