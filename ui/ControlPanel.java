package ui;

import model.*;
import data.GridData;
import utils.GraphicsUtils;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ControlPanel extends JPanel {

    private GridPanel gridPanel;
    private MainApp mainApp;

    public ControlPanel(GridPanel gridPanel, MainApp mainApp) {
        this.gridPanel = gridPanel;
        this.mainApp = mainApp;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(240, 248, 255));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));

        add(createTitleSection());
        add(Box.createVerticalStrut(10));
        add(createDataSection());
        add(Box.createVerticalStrut(10));
        add(createButtonSection());
    }

    private JPanel createTitleSection() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 248, 255));

        JLabel title = new JLabel("SYSTÈME DE LIVRAISON - TABLEAU DE BORD");
        title.setFont(new Font("Arial", Font.BOLD, 14));
        title.setForeground(new Color(0, 70, 140));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Visualisation des livraisons en temps réel ");
        subtitle.setFont(new Font("Arial", Font.ITALIC, 10));
        subtitle.setForeground(new Color(100, 100, 100));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(title);
        panel.add(subtitle);

        return panel;
    }

    private JPanel createDataSection() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 10, 0));
        panel.setBackground(new Color(240, 248, 255));

        panel.add(createEntityPanel("MAGASINS", GridData.getStores(),
                new Color(220, 240, 255), new Color(70, 130, 180)));

        panel.add(createEntityPanel("CLIENTS", GridData.getCustomers(),
                new Color(220, 255, 220), new Color(60, 140, 60)));

        panel.add(createTunnelPanel());

        return panel;
    }

    private JPanel createEntityPanel(String title, List<?> entities, Color bgColor, Color borderColor) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(bgColor);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 2),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 11));
        titleLabel.setForeground(borderColor.darker());
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(5));

        for (Object entity : entities) {
            String text = "";
            if (entity instanceof Store) {
                Store s = (Store) entity;
                text = String.format("• %s → Position: %s", s.getId(), s.getPosition());
            } else if (entity instanceof Customer) {
                Customer c = (Customer) entity;
                text = String.format("• %s → Position: %s", c.getId(), c.getPosition());
            }

            JLabel item = new JLabel(text);
            item.setFont(new Font("Arial", Font.PLAIN, 10));
            item.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(item);
            panel.add(Box.createVerticalStrut(2));
        }

        return panel;
    }

    private JPanel createTunnelPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(255, 240, 220));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 140, 70), 2),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)));

        JLabel titleLabel = new JLabel("TUNNELS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 11));
        titleLabel.setForeground(new Color(160, 100, 40));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(5));

        for (Tunnel tunnel : GridData.getTunnels()) {
            String text = String.format("• %s: %s → %s",
                    tunnel.getId(), tunnel.getEntrance(), tunnel.getExit());
            JLabel item = new JLabel(text);
            item.setFont(new Font("Arial", Font.PLAIN, 10));
            item.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(item);
            panel.add(Box.createVerticalStrut(2));
        }

        return panel;
    }

    private JPanel createButtonSection() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panel.setBackground(new Color(240, 248, 255));

        // Bouton Retour
        JButton backBtn = GraphicsUtils.createStyledButton(
                "← Retour",
                new Color(100, 100, 100),
                Color.WHITE,
                new Font("Arial", Font.BOLD, 12));
        backBtn.addActionListener(e -> mainApp.showWelcomePage());

        // Bouton Visualiser
        JButton visualizeBtn = GraphicsUtils.createStyledButton(
                "Visualiser",
                new Color(70, 130, 180),
                Color.WHITE,
                new Font("Arial", Font.BOLD, 12));
        visualizeBtn.addActionListener(e -> visualizePaths());

        // Bouton Réinitialiser
        JButton resetBtn = GraphicsUtils.createStyledButton(
                "Réinitialiser",
                new Color(220, 100, 100),
                Color.WHITE,
                new Font("Arial", Font.BOLD, 12));
        resetBtn.addActionListener(e -> gridPanel.reset());

        panel.add(backBtn);
        panel.add(visualizeBtn);
        panel.add(resetBtn);

        return panel;
    }

    private void visualizePaths() {
        gridPanel.reset();

        // Note: Ces chemins sont des exemples
        // Dans une vraie application, ils viendraient du backend
        if (GridData.getStores().size() >= 2 && GridData.getCustomers().size() >= 2) {
            Store s1 = GridData.getStores().get(0);
            Store s2 = GridData.getStores().get(1);
            Customer c1 = GridData.getCustomers().get(0);
            Customer c2 = GridData.getCustomers().get(1);

            // Chemins de démonstration simples (à remplacer par l'algorithme réel)
            DeliveryInfo[] deliveries = {
                    new DeliveryInfo(s1.getPosition(), c1.getPosition(), "right,down,right,down"),
                    new DeliveryInfo(s1.getPosition(), c2.getPosition(), "right,right,right,down"),
                    new DeliveryInfo(s2.getPosition(), c1.getPosition(), "left,up,left,up"),
                    new DeliveryInfo(s2.getPosition(), c2.getPosition(), "left,left,down,down")
            };

            for (DeliveryInfo delivery : deliveries) {
                List<Position> path = gridPanel.computePath(
                        delivery.getStartPosition(),
                        delivery.getDirections());
                gridPanel.addPath(path);
            }
        }
    }
}