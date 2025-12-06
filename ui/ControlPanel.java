package ui;

import model.*;
import data.GridData;
import utils.GraphicsUtils;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ControlPanel extends JPanel {

    private GridPanel gridPanel;

    public ControlPanel(GridPanel gridPanel) {
        this.gridPanel = gridPanel;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(240, 248, 255));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
                BorderFactory.createEmptyBorder(8, 10, 8, 10))); // Réduire le padding

        add(createTitleSection());
        add(Box.createVerticalStrut(10)); // Réduire l'espacement
        add(createDataSection());
        add(Box.createVerticalStrut(10)); // Réduire l'espacement
        add(createRoutesSection());
        add(Box.createVerticalStrut(10)); // Réduire l'espacement
        add(createButtonSection());
    }

    private JPanel createTitleSection() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 248, 255));

        JLabel title = new JLabel("SYSTÈME DE LIVRAISON - TABLEAU DE BORD");
        title.setFont(new Font("Arial", Font.BOLD, 14)); // Réduire la taille
        title.setForeground(new Color(0, 70, 140));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Visualisation des livraisons en temps réel");
        subtitle.setFont(new Font("Arial", Font.ITALIC, 10)); // Réduire la taille
        subtitle.setForeground(new Color(100, 100, 100));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(title);
        panel.add(subtitle);

        return panel;
    }

    private JPanel createDataSection() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 10, 0)); // Réduire l'espacement
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
                BorderFactory.createEmptyBorder(5, 8, 5, 8))); // Réduire le padding

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 11)); // Réduire la taille
        titleLabel.setForeground(borderColor.darker());
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(5)); // Réduire l'espacement

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
            item.setFont(new Font("Arial", Font.PLAIN, 10)); // Réduire la taille
            item.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(item);
            panel.add(Box.createVerticalStrut(2)); // Réduire l'espacement
        }

        return panel;
    }

    private JPanel createTunnelPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(255, 240, 220));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 140, 70), 2),
                BorderFactory.createEmptyBorder(5, 8, 5, 8))); // Réduire le padding

        JLabel titleLabel = new JLabel("TUNNELS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 11)); // Réduire la taille
        titleLabel.setForeground(new Color(160, 100, 40));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(5)); // Réduire l'espacement

        for (Tunnel tunnel : GridData.getTunnels()) {
            String text = String.format("• %s: %s → %s",
                    tunnel.getId(), tunnel.getEntrance(), tunnel.getExit());
            JLabel item = new JLabel(text);
            item.setFont(new Font("Arial", Font.PLAIN, 10)); // Réduire la taille
            item.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(item);
            panel.add(Box.createVerticalStrut(2)); // Réduire l'espacement
        }

        return panel;
    }

    private JPanel createRoutesSection() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 250, 220));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(218, 165, 32), 2),
                BorderFactory.createEmptyBorder(5, 8, 5, 8))); // Réduire le padding

        JLabel titleLabel = new JLabel("PLAN DE LIVRAISON - CHEMINS PROGRAMMÉS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 11)); // Réduire la taille
        titleLabel.setForeground(new Color(160, 120, 40));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel routesPanel = new JPanel(new GridLayout(2, 2, 8, 8)); // Réduire l'espacement
        routesPanel.setBackground(new Color(255, 253, 240));
        routesPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Réduire le padding

        String[][] routes = {
                { "Store (1,1) → Customer (4,3)", "right,down,right,down,down,left", "🔵" },
                { "Store (1,1) → Customer (2,6)", "right,right,right,down,down,down", "🟣" },
                { "Store (6,5) → Customer (4,3)", "left,up,up,right,tunnel,right", "🔴" },
                { "Store (6,5) → Customer (2,6)", "left,left,up,up,left,down", "🟢" }
        };

        Color[] routeColors = { Color.BLUE, Color.MAGENTA, Color.RED, Color.GREEN };

        for (int i = 0; i < routes.length; i++) {
            routesPanel.add(createRouteCard(routes[i], routeColors[i]));
        }

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(routesPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createRouteCard(String[] routeData, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color, 2),
                BorderFactory.createEmptyBorder(4, 6, 4, 6))); // Réduire le padding

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);

        JLabel iconLabel = new JLabel(routeData[2]);
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 11)); // Réduire la taille

        JLabel routeHeader = new JLabel(routeData[0]);
        routeHeader.setFont(new Font("Arial", Font.BOLD, 10)); // Réduire la taille
        routeHeader.setForeground(color);

        headerPanel.add(iconLabel, BorderLayout.WEST);
        headerPanel.add(routeHeader, BorderLayout.CENTER);

        JTextArea directions = new JTextArea("Chemin: " + routeData[1]);
        directions.setFont(new Font("Monospaced", Font.PLAIN, 8)); // Réduire la taille
        directions.setBackground(new Color(250, 250, 250));
        directions.setEditable(false);
        directions.setLineWrap(true);
        directions.setWrapStyleWord(true);
        directions.setMargin(new Insets(2, 2, 2, 2)); // Réduire les marges
        directions.setRows(2); // Limiter à 2 lignes

        card.add(headerPanel, BorderLayout.NORTH);
        card.add(directions, BorderLayout.CENTER);

        return card;
    }

    private JPanel createButtonSection() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0)); // Réduire l'espacement
        panel.setBackground(new Color(240, 248, 255));

        JButton visualizeBtn = GraphicsUtils.createStyledButton(
                "Visualiser le Trajet",
                new Color(70, 130, 180),
                Color.WHITE,
                new Font("Arial", Font.BOLD, 12)); // Réduire la taille
        visualizeBtn.addActionListener(e -> visualizePaths());

        JButton resetBtn = GraphicsUtils.createStyledButton(
                "Réinitialiser",
                new Color(220, 100, 100),
                Color.WHITE,
                new Font("Arial", Font.BOLD, 12)); // Réduire la taille
        resetBtn.addActionListener(e -> gridPanel.reset());

        panel.add(visualizeBtn);
        panel.add(resetBtn);

        return panel;
    }

    private void visualizePaths() {
        gridPanel.reset();

        DeliveryInfo[] deliveries = {
                new DeliveryInfo(new Position(1, 1), new Position(4, 3),
                        "right,down,right,down,down,left"),
                new DeliveryInfo(new Position(1, 1), new Position(2, 6),
                        "right,right,right,down,down,down"),
                new DeliveryInfo(new Position(6, 5), new Position(4, 3),
                        "left,up,up,right,tunnel,right"),
                new DeliveryInfo(new Position(6, 5), new Position(2, 6),
                        "left,left,up,up,left,down")
        };

        for (DeliveryInfo delivery : deliveries) {
            List<Position> path = gridPanel.computePath(
                    delivery.getStartPosition(),
                    delivery.getDirections());
            gridPanel.addPath(path);
        }
    }
}