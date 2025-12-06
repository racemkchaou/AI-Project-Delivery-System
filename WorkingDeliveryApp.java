import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

// Classes de base
class Store {
    Position pos;
    String id;
    Color color;

    Store(Position p, String i, Color c) {
        pos = p;
        id = i;
        color = c;
    }
}

class Customer {
    Position pos;
    String id;
    Color color;

    Customer(Position p, String i, Color c) {
        pos = p;
        id = i;
        color = c;
    }
}

class Tunnel {
    Position entrance, exit;
    String id;
    Color color;

    Tunnel(Position e1, Position e2, String i, Color c) {
        entrance = e1;
        exit = e2;
        id = i;
        color = c;
    }
}

public class WorkingDeliveryApp extends JFrame {

    private GridPanel gridPanel;

    public WorkingDeliveryApp() {
        setTitle("Système de Livraison - FONCTIONNEL");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initUI();
        pack();
        setLocationRelativeTo(null);
    }

    private void initUI() {
        gridPanel = new GridPanel();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));

        JPanel controlPanel = createEnhancedControlPanel();

        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanel.add(controlPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    private JPanel createEnhancedControlPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBackground(new Color(240, 248, 255));
        controlPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JLabel title = new JLabel("SYSTÈME DE LIVRAISON - TABLEAU DE BORD");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(0, 70, 140));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Visualisation des livraisons en temps réel");
        subtitle.setFont(new Font("Arial", Font.ITALIC, 12));
        subtitle.setForeground(new Color(100, 100, 100));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        controlPanel.add(title);
        controlPanel.add(Box.createVerticalStrut(5));
        controlPanel.add(subtitle);
        controlPanel.add(Box.createVerticalStrut(20));

        JPanel dataPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        dataPanel.setBackground(new Color(240, 248, 255));

        dataPanel.add(createEnhancedEntityPanel("MAGASINS", GridData.stores,
                new Color(220, 240, 255), new Color(70, 130, 180)));

        dataPanel.add(createEnhancedEntityPanel("CLIENTS", GridData.customers,
                new Color(220, 255, 220), new Color(60, 140, 60)));

        dataPanel.add(createEnhancedTunnelPanel());

        controlPanel.add(dataPanel);
        controlPanel.add(Box.createVerticalStrut(20));

        JPanel directionsPanel = new JPanel(new BorderLayout());
        directionsPanel.setBackground(new Color(255, 250, 220));
        directionsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(218, 165, 32), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(255, 250, 220));

        JLabel titleLabel = new JLabel("PLAN DE LIVRAISON - CHEMINS PROGRAMMÉS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(new Color(160, 120, 40));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        titlePanel.add(titleLabel, BorderLayout.NORTH);
        directionsPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel routesPanel = new JPanel();
        routesPanel.setLayout(new GridLayout(2, 2, 10, 10));
        routesPanel.setBackground(new Color(255, 253, 240));
        routesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[][] routes = {
                { "Store (1,1) → Customer (4,3)", "right,down,right,down,down,left", "🔵" },
                { "Store (1,1) → Customer (2,6)", "right,right,right,down,down,down", "🟣" },
                { "Store (6,5) → Customer (4,3)", "left,up,up,right,tunnel,right", "🔴" },
                { "Store (6,5) → Customer (2,6)", "left,left,up,up,left,down", "🟢" }
        };

        Color[] routeColors = {
                Color.BLUE,
                Color.MAGENTA,
                Color.RED,
                Color.GREEN
        };

        for (int i = 0; i < routes.length; i++) {
            JPanel routeCard = new JPanel(new BorderLayout());
            routeCard.setBackground(Color.WHITE);
            routeCard.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(routeColors[i], 2),
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)));

            JPanel headerPanel = new JPanel(new BorderLayout());
            headerPanel.setBackground(Color.WHITE);

            JLabel iconLabel = new JLabel(routes[i][2]);
            iconLabel.setFont(new Font("Arial", Font.PLAIN, 14));

            JLabel routeHeader = new JLabel(routes[i][0]);
            routeHeader.setFont(new Font("Arial", Font.BOLD, 12));
            routeHeader.setForeground(routeColors[i]);

            headerPanel.add(iconLabel, BorderLayout.WEST);
            headerPanel.add(routeHeader, BorderLayout.CENTER);

            JTextArea routeDirections = new JTextArea("Chemin: " + routes[i][1]);
            routeDirections.setFont(new Font("Monospaced", Font.PLAIN, 10));
            routeDirections.setBackground(new Color(250, 250, 250));
            routeDirections.setEditable(false);
            routeDirections.setLineWrap(true);
            routeDirections.setWrapStyleWord(true);
            routeDirections.setMargin(new Insets(5, 5, 5, 5));

            routeCard.add(headerPanel, BorderLayout.NORTH);
            routeCard.add(new JScrollPane(routeDirections), BorderLayout.CENTER);

            routesPanel.add(routeCard);
        }

        directionsPanel.add(routesPanel, BorderLayout.CENTER);

        controlPanel.add(directionsPanel);
        controlPanel.add(Box.createVerticalStrut(20));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(new Color(240, 248, 255));

        JButton visualizeBtn = createStyledButton("Visualiser le Trajet",
                new Color(70, 130, 180), Color.WHITE, new Font("Arial", Font.BOLD, 14));
        visualizeBtn.addActionListener(e -> visualize());

        JButton resetBtn = createStyledButton("Réinitialiser",
                new Color(220, 100, 100), Color.WHITE, new Font("Arial", Font.BOLD, 14));
        resetBtn.addActionListener(e -> gridPanel.reset());

        buttonPanel.add(visualizeBtn);
        buttonPanel.add(resetBtn);

        controlPanel.add(buttonPanel);

        return controlPanel;
    }

    private JPanel createEnhancedEntityPanel(String title, List<?> entities,
            Color bgColor, Color borderColor) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(bgColor);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 2),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(borderColor.darker());
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(10));

        for (Object entity : entities) {
            String text = "";
            if (entity instanceof Store) {
                Store s = (Store) entity;
                text = String.format("• %s → Position: %s", s.id, s.pos);
            } else if (entity instanceof Customer) {
                Customer c = (Customer) entity;
                text = String.format("• %s → Position: %s", c.id, c.pos);
            }

            JLabel item = new JLabel(text);
            item.setFont(new Font("Arial", Font.PLAIN, 12));
            item.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(item);
            panel.add(Box.createVerticalStrut(5));
        }

        return panel;
    }

    private JPanel createEnhancedTunnelPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(255, 240, 220));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 140, 70), 2),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));

        JLabel titleLabel = new JLabel("TUNNELS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(new Color(160, 100, 40));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(10));

        for (Tunnel t : GridData.tunnels) {
            String text = String.format("• %s: %s → %s",
                    t.id, t.entrance, t.exit);
            JLabel item = new JLabel(text);
            item.setFont(new Font("Arial", Font.PLAIN, 12));
            item.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(item);
            panel.add(Box.createVerticalStrut(5));
        }

        return panel;
    }

    private JButton createStyledButton(String text, Color bgColor,
            Color fgColor, Font font) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(font);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void visualize() {
        gridPanel.reset();

        List<DeliveryInfo> deliveries = getDeliveriesFromBackend();

        for (DeliveryInfo delivery : deliveries) {
            java.util.List<Position> p = gridPanel.computePath(
                    delivery.startPos,
                    delivery.directions);

            gridPanel.addPath(p);
        }
    }

    class DeliveryInfo {
        Position startPos;
        Position endPos;
        String directions;

        DeliveryInfo(Position startPos, Position endPos, String directions) {
            this.startPos = startPos;
            this.endPos = endPos;
            this.directions = directions;
        }
    }

    private List<DeliveryInfo> getDeliveriesFromBackend() {
        List<DeliveryInfo> deliveries = new ArrayList<>();

        // Store (1,1) → Customer (4,3)
        deliveries.add(new DeliveryInfo(
                new Position(1, 1),
                new Position(4, 3),
                "right,down,right,down,down,left"));

        // Store (1,1) → Customer (2,6)
        deliveries.add(new DeliveryInfo(
                new Position(1, 1),
                new Position(2, 6),
                "right,right,right,down,down,down"));

        // Store (6,5) → Customer (4,3)
        deliveries.add(new DeliveryInfo(
                new Position(6, 5),
                new Position(4, 3),
                "left,up,up,right,tunnel,right"));

        // Store (6,5) → Customer (2,6)
        deliveries.add(new DeliveryInfo(
                new Position(6, 5),
                new Position(2, 6),
                "left,left,up,up,left,down"));

        return deliveries;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WorkingDeliveryApp app = new WorkingDeliveryApp();
            app.setDefaultCloseOperation(EXIT_ON_CLOSE);
            app.setExtendedState(JFrame.MAXIMIZED_BOTH);
            app.setVisible(true);
        });
    }

    class GridPanel extends JPanel {
        private java.util.List<java.util.List<Position>> allPaths = new ArrayList<>();
        private java.util.List<Position> truckPositions = new ArrayList<>();

        private static final int CELL = 50;
        private static final int OFFSET = 50;

        public GridPanel() {
            setBackground(new Color(240, 240, 240));

            int gridWidth = OFFSET * 2 + (GridData.COLS - 1) * CELL;
            int gridHeight = OFFSET * 2 + (GridData.ROWS - 1) * CELL;

            setPreferredSize(new Dimension(gridWidth, gridHeight));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            drawCosts(g2d);

            g2d.setColor(Color.LIGHT_GRAY);
            g2d.setStroke(new BasicStroke(1));

            int gridStartX = OFFSET;
            int gridStartY = OFFSET;
            int gridWidth = (GridData.COLS - 1) * CELL;
            int gridHeight = (GridData.ROWS - 1) * CELL;

            for (int i = 0; i < GridData.ROWS; i++) {
                int y = gridStartY + i * CELL;
                g2d.drawLine(gridStartX, y, gridStartX + gridWidth, y);
            }

            for (int i = 0; i < GridData.COLS; i++) {
                int x = gridStartX + i * CELL;
                g2d.drawLine(x, gridStartY, x, gridStartY + gridHeight);
            }

            g2d.setColor(Color.BLACK);
            for (int y = 0; y < GridData.ROWS; y++) {
                for (int x = 0; x < GridData.COLS; x++) {
                    int px = OFFSET + x * CELL;
                    int py = OFFSET + y * CELL;
                    g2d.fillOval(px - 4, py - 4, 8, 8);

                    g2d.setColor(Color.GRAY);
                    g2d.setFont(new Font("Arial", Font.PLAIN, 9));
                    g2d.drawString(x + "," + y, px + 6, py - 6);
                    g2d.setColor(Color.BLACK);
                }
            }

            drawStores(g2d);
            drawCustomers(g2d);
            drawTunnels(g2d);

            if (!allPaths.isEmpty()) {
                Color[] palette = { Color.BLUE, Color.MAGENTA, Color.RED, Color.CYAN, Color.ORANGE };
                int idx = 0;

                truckPositions.clear();

                for (java.util.List<Position> path : allPaths) {
                    if (path.size() > 1) {
                        Color col = palette[idx % palette.length];

                        for (int i = 0; i < path.size() - 1; i++) {
                            Point p1 = toPoint(path.get(i));
                            Point p2 = toPoint(path.get(i + 1));
                            drawArrow(g2d, p1, p2, col);
                        }

                        Position lastPos = path.get(path.size() - 1);
                        truckPositions.add(lastPos);
                    }
                    idx++;
                }
            }

            for (Position truckPos : truckPositions) {
                Point p = toPoint(truckPos);
                g2d.setColor(Color.YELLOW);
                g2d.fillOval(p.x - 10, p.y - 10, 20, 20);
                g2d.setColor(Color.BLACK);
                g2d.drawOval(p.x - 10, p.y - 10, 20, 20);
                g2d.drawString("T", p.x - 3, p.y + 4);
            }
        }

        private void drawCosts(Graphics2D g2d) {
            g2d.setFont(new Font("Arial", Font.BOLD, 12));

            for (int y = 0; y < GridData.ROWS; y++) {
                for (int x = 0; x < GridData.COLS - 1; x++) {
                    int cost = GridData.H_COSTS[y][x];
                    int x1 = OFFSET + x * CELL;
                    int x2 = OFFSET + (x + 1) * CELL;
                    int yPos = OFFSET + y * CELL;

                    int centerX = (x1 + x2) / 2;
                    int centerY = yPos;

                    drawCostOnHorizontalLine(g2d, String.valueOf(cost), centerX, centerY, getCostColor(cost));
                }
            }

            for (int y = 0; y < GridData.ROWS - 1; y++) {
                for (int x = 0; x < GridData.COLS; x++) {
                    int cost = GridData.V_COSTS[y][x];
                    int xPos = OFFSET + x * CELL;
                    int y1 = OFFSET + y * CELL;
                    int y2 = OFFSET + (y + 1) * CELL;

                    int centerX = xPos;
                    int centerY = (y1 + y2) / 2;

                    drawCostOnVerticalLine(g2d, String.valueOf(cost), centerX, centerY, getCostColor(cost));
                }
            }
        }

        private void drawCostOnHorizontalLine(Graphics2D g2d, String text, int x, int y, Color color) {
            FontMetrics fm = g2d.getFontMetrics();

            if (text.equals("0")) {
                drawBlockSign(g2d, x, y, color);
            } else {
                int textWidth = fm.stringWidth(text);

                g2d.setColor(getBackground());
                g2d.fillRect(x - textWidth / 2 - 2, y - 13, textWidth + 4, 16);

                g2d.setColor(color);
                g2d.drawString(text, x - textWidth / 2, y - 4);
            }
        }

        private void drawCostOnVerticalLine(Graphics2D g2d, String text, int x, int y, Color color) {
            FontMetrics fm = g2d.getFontMetrics();

            if (text.equals("0")) {
                drawBlockSign(g2d, x, y, color);
            } else {
                int textWidth = fm.stringWidth(text);

                g2d.setColor(getBackground());
                g2d.fillRect(x + 1, y - 7, textWidth + 4, 16);

                g2d.setColor(color);
                g2d.drawString(text, x + 3, y + 4);
            }
        }

        private Color getCostColor(int cost) {
            switch (cost) {
                case 0:
                    return Color.GREEN.darker();
                case 1:
                    return Color.BLUE;
                case 2:
                    return Color.ORANGE;
                case 3:
                    return Color.RED;
                case 4:
                    return Color.MAGENTA.darker();
                default:
                    return Color.BLACK;
            }
        }

        private void drawBlockSign(Graphics2D g2d, int x, int y, Color color) {
            int size = 14;

            Stroke originalStroke = g2d.getStroke();
            Color originalColor = g2d.getColor();

            g2d.setColor(Color.RED);
            g2d.fillOval(x - size / 2, y - size / 2, size, size);

            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(1));
            g2d.drawOval(x - size / 2, y - size / 2, size, size);

            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine(x - size / 3, y - size / 3, x + size / 3, y + size / 3);

            g2d.setStroke(originalStroke);
            g2d.setColor(originalColor);
        }

        private void drawArrow(Graphics2D g2d, Point start, Point end, Color color) {
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawLine(start.x, start.y, end.x, end.y);

            double angle = Math.atan2(end.y - start.y, end.x - start.x);
            int arrowLength = 10;

            Polygon arrowHead = new Polygon();
            arrowHead.addPoint(end.x, end.y);
            arrowHead.addPoint(
                    (int) (end.x - arrowLength * Math.cos(angle - Math.PI / 6)),
                    (int) (end.y - arrowLength * Math.sin(angle - Math.PI / 6)));
            arrowHead.addPoint(
                    (int) (end.x - arrowLength * Math.cos(angle + Math.PI / 6)),
                    (int) (end.y - arrowLength * Math.sin(angle + Math.PI / 6)));

            g2d.setColor(color);
            g2d.fill(arrowHead);
            g2d.setColor(color.darker());
            g2d.draw(arrowHead);
        }

        private Point toPoint(Position pos) {
            // pos.x = horizontal (colonnes), pos.y = vertical (lignes)
            return new Point(OFFSET + pos.x * CELL, OFFSET + pos.y * CELL);
        }

        private void drawStores(Graphics2D g2d) {
            g2d.setStroke(new BasicStroke(2));
            for (Store s : GridData.stores) {
                Point p = toPoint(s.pos);
                g2d.setColor(s.color);
                g2d.fillRect(p.x - 10, p.y - 10, 20, 20);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(p.x - 10, p.y - 10, 20, 20);
                g2d.setColor(Color.WHITE);
                g2d.drawString(s.id, p.x - 5, p.y + 4);
            }
        }

        private void drawCustomers(Graphics2D g2d) {
            for (Customer c : GridData.customers) {
                Point p = toPoint(c.pos);
                int[] xs = { p.x, p.x - 10, p.x + 10 };
                int[] ys = { p.y - 10, p.y + 10, p.y + 10 };
                g2d.setColor(c.color);
                g2d.fillPolygon(xs, ys, 3);
                g2d.setColor(Color.BLACK);
                g2d.drawPolygon(xs, ys, 3);
                g2d.setColor(Color.WHITE);
                g2d.drawString(c.id, p.x - 5, p.y + 4);
            }
        }

        private void drawTunnels(Graphics2D g2d) {
            for (Tunnel t : GridData.tunnels) {
                Point p1 = toPoint(t.entrance);
                Point p2 = toPoint(t.exit);

                g2d.setColor(t.color);
                Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                        0, new float[] { 9 }, 0);
                g2d.setStroke(dashed);
                g2d.drawLine(p1.x, p1.y, p2.x, p2.y);

                g2d.setStroke(new BasicStroke(2));
                g2d.fillOval(p1.x - 8, p1.y - 8, 16, 16);
                g2d.fillOval(p2.x - 8, p2.y - 8, 16, 16);

                g2d.setColor(Color.WHITE);
                g2d.drawString("E", p1.x - 3, p1.y + 4);
                g2d.drawString("S", p2.x - 3, p2.y + 4);
            }
        }

        public java.util.List<Position> computePath(Position startPos, String directions) {
            java.util.List<Position> path = new ArrayList<>();

            Position current = startPos;
            path.add(current);

            String[] moves = directions.split(",");
            for (String move : moves) {
                move = move.trim().toLowerCase();

                if (move.equals("tunnel")) {
                    for (Tunnel t : GridData.tunnels) {
                        if (t.entrance.equals(current))
                            current = t.exit;
                        else if (t.exit.equals(current))
                            current = t.entrance;
                    }
                } else {
                    current = current.move(move);
                }
                path.add(current);
            }

            return path;
        }

        public void reset() {
            allPaths.clear();
            truckPositions.clear();
            repaint();
        }

        public void addPath(java.util.List<Position> p) {
            allPaths.add(p);
            repaint();
        }
    }
}