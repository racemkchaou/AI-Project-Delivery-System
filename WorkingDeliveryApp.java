import javax.swing.*;
import java.awt.*;
import java.util.*;

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
    private JComboBox<String> storeCombo, customerCombo;
    private JTextArea directionsArea;

    public WorkingDeliveryApp() {
        setTitle("Système de Livraison - FONCTIONNEL");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initUI();
        pack();
        setLocationRelativeTo(null);
    }

    private void initUI() {
        gridPanel = new GridPanel();

        // Contrôles
        storeCombo = new JComboBox<>();
        customerCombo = new JComboBox<>();
        updateCombos();

        directionsArea = new JTextArea(3, 30);
        directionsArea.setText("right,down,right,tunnel,left,down");

        JButton visualizeBtn = new JButton("Visualiser");
        visualizeBtn.setBackground(new Color(70, 130, 180));
        visualizeBtn.setForeground(Color.WHITE);
        visualizeBtn.addActionListener(e -> visualize());

        JButton resetBtn = new JButton("Reset");
        resetBtn.setBackground(new Color(220, 100, 100));
        resetBtn.setForeground(Color.WHITE);
        resetBtn.addActionListener(e -> gridPanel.reset());

        // Layout
        JPanel controlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        controlPanel.add(new JLabel("TEST IMMÉDIAT - Tout devrait être visible"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        controlPanel.add(new JLabel("Magasin:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        controlPanel.add(storeCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        controlPanel.add(new JLabel("Client:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        controlPanel.add(customerCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        controlPanel.add(new JLabel("Directions:"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        controlPanel.add(new JScrollPane(directionsArea), gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        controlPanel.add(visualizeBtn, gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        controlPanel.add(resetBtn, gbc);

        setLayout(new BorderLayout());
        add(controlPanel, BorderLayout.NORTH);
        add(new JScrollPane(gridPanel), BorderLayout.CENTER);
    }

    private void updateCombos() {
        storeCombo.removeAllItems();
        for (Store s : GridData.stores) {
            storeCombo.addItem(s.id + " @ " + s.pos);
        }
        customerCombo.removeAllItems();
        for (Customer c : GridData.customers) {
            customerCombo.addItem(c.id + " @ " + c.pos);
        }
    }

    private void visualize() {
        gridPanel.reset(); // Effacer les anciens dessins

        String dirs = directionsArea.getText().trim();

        // Pour chaque store
        for (Store s : GridData.stores) {

            // Pour chaque customer
            for (Customer c : GridData.customers) {

                // Calculer le path
                java.util.List<Position> p = gridPanel.computePath(s.id, dirs);

                // Ajouter ce path au dessin
                gridPanel.addPath(p);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new WorkingDeliveryApp().setVisible(true);
        });
    }

    // Panel d'affichage
    class GridPanel extends JPanel {
        private java.util.List<java.util.List<Position>> allPaths = new ArrayList<>();
        private Position truck = null;
        private static final int CELL = 50;
        private static final int OFFSET = 50;

        public GridPanel() {
            setBackground(Color.WHITE);
            // Preferred size: leave a margin (OFFSET on both sides) and place intersections
            // at OFFSET + x*CELL
            int width = OFFSET * 2 + (GridData.COLS - 1) * CELL;
            int height = OFFSET * 2 + (GridData.ROWS - 1) * CELL;
            setPreferredSize(new Dimension(width, height));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // 1. Coûts des segments (dessinés en premier)
            drawCosts(g2d);

            // 2. Grille
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.setStroke(new BasicStroke(1));
            // draw horizontal grid lines (one for each row of intersections)
            for (int i = 0; i < GridData.ROWS; i++) {
                int y = OFFSET + i * CELL;
                g2d.drawLine(OFFSET, y, OFFSET + (GridData.COLS - 1) * CELL, y);
            }
            // draw vertical grid lines (one for each column of intersections)
            for (int i = 0; i < GridData.COLS; i++) {
                int x = OFFSET + i * CELL;
                g2d.drawLine(x, OFFSET, x, OFFSET + (GridData.ROWS - 1) * CELL);
            }

            // 3. Points d'intersection
            g2d.setColor(Color.BLACK);
            for (int y = 0; y < GridData.ROWS; y++) {
                for (int x = 0; x < GridData.COLS; x++) {
                    int px = OFFSET + x * CELL;
                    int py = OFFSET + y * CELL;
                    g2d.fillOval(px - 4, py - 4, 8, 8);

                    // Coordonnées
                    g2d.setColor(Color.GRAY);
                    g2d.setFont(new Font("Arial", Font.PLAIN, 9));
                    g2d.drawString(x + "," + y, px + 6, py - 6);
                    g2d.setColor(Color.BLACK);
                }
            }

            // 4. Entités
            drawStores(g2d);
            drawCustomers(g2d);
            drawTunnels(g2d);

            // 5. Chemin avec flèches
            // 5. Tous les chemins (each path is a list of Positions)
            if (!allPaths.isEmpty()) {
                // donner une couleur différente par chemin si tu veux (ici on alterne deux
                // couleurs)
                Color[] palette = { Color.BLUE, Color.MAGENTA, Color.RED, Color.CYAN, Color.ORANGE };
                int idx = 0;
                for (java.util.List<Position> path : allPaths) {
                    if (path.size() > 1) {
                        Color col = palette[idx % palette.length];
                        for (int i = 0; i < path.size() - 1; i++) {
                            Point p1 = toPoint(path.get(i));
                            Point p2 = toPoint(path.get(i + 1));
                            drawArrow(g2d, p1, p2, col);
                        }
                        // mettre à jour truck sur la dernière position du dernier chemin (optionnel)
                        truck = path.get(path.size() - 1);
                    }
                    idx++;
                }
            }

            // 6. Camion
            if (truck != null) {
                Point p = toPoint(truck);
                g2d.setColor(Color.YELLOW);
                g2d.fillOval(p.x - 10, p.y - 10, 20, 20);
                g2d.setColor(Color.BLACK);
                g2d.drawOval(p.x - 10, p.y - 10, 20, 20);
                g2d.drawString("T", p.x - 3, p.y + 4);
            }
        }

        // Méthode pour dessiner les coûts
        private void drawCosts(Graphics2D g2d) {
            g2d.setFont(new Font("Arial", Font.BOLD, 12));

            // Coûts des segments horizontaux (de chaque intersection vers la droite)
            // itérer exactement sur ROWS x (COLS - 1)
            for (int y = 0; y < GridData.ROWS; y++) {
                for (int x = 0; x < GridData.COLS - 1; x++) {
                    int cost = GridData.H_COSTS[y][x];
                    int x1 = OFFSET + x * CELL;
                    int x2 = OFFSET + (x + 1) * CELL;
                    int yPos = OFFSET + y * CELL;

                    int centerX = (x1 + x2) / 2;
                    int centerY = yPos - 8; // Un peu plus haut pour éviter l'intersection

                    drawCostText(g2d, String.valueOf(cost), centerX, centerY, getCostColor(cost));
                }
            }

            // Coûts des segments verticaux (de chaque intersection vers le bas)
            // itérer exactement sur (ROWS - 1) x COLS
            for (int y = 0; y < GridData.ROWS - 1; y++) {
                for (int x = 0; x < GridData.COLS; x++) {
                    int cost = GridData.V_COSTS[y][x];
                    int xPos = OFFSET + x * CELL;
                    int y1 = OFFSET + y * CELL;
                    int y2 = OFFSET + (y + 1) * CELL;

                    int centerX = xPos + 8; // Un peu à droite pour éviter l'intersection
                    int centerY = (y1 + y2) / 2;

                    drawCostText(g2d, String.valueOf(cost), centerX, centerY, getCostColor(cost));
                }
            }
        }

        // Méthode pour obtenir la couleur selon le coût
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

        // Méthode utilitaire pour dessiner le texte du coût (sans rectangle)
        private void drawCostText(Graphics2D g2d, String text, int x, int y, Color color) {
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(text);

            // Dessiner le texte directement avec la couleur
            g2d.setColor(color);
            g2d.drawString(text, x - textWidth / 2, y);
        }

        // Méthode pour dessiner une flèche
        private void drawArrow(Graphics2D g2d, Point start, Point end, Color color) {
            // Dessiner la ligne principale
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawLine(start.x, start.y, end.x, end.y);

            // Calculer l'angle de la flèche
            double angle = Math.atan2(end.y - start.y, end.x - start.x);
            int arrowLength = 10;

            // Dessiner la tête de flèche
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
            return new Point(OFFSET + pos.col * CELL, OFFSET + pos.row * CELL);
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

                // Ligne
                g2d.setColor(t.color);
                Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                        0, new float[] { 9 }, 0);
                g2d.setStroke(dashed);
                g2d.drawLine(p1.x, p1.y, p2.x, p2.y);

                // Cercles
                g2d.setStroke(new BasicStroke(2));
                g2d.fillOval(p1.x - 8, p1.y - 8, 16, 16);
                g2d.fillOval(p2.x - 8, p2.y - 8, 16, 16);

                g2d.setColor(Color.WHITE);
                g2d.drawString("E", p1.x - 3, p1.y + 4);
                g2d.drawString("S", p2.x - 3, p2.y + 4);
            }
        }

        public java.util.List<Position> computePath(String storeId, String directions) {

            java.util.List<Position> path = new ArrayList<>();

            // Trouver le store de départ
            Store start = null;
            for (Store s : GridData.stores) {
                if (s.id.equals(storeId)) {
                    start = s;
                    break;
                }
            }
            if (start == null)
                return path;

            Position current = start.pos;
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
            repaint();
        }

        public void addPath(java.util.List<Position> p) {
            allPaths.add(p);
            repaint();
        }

    }
}
