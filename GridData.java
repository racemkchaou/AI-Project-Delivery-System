import java.awt.Color;
import java.util.*;

public class GridData {
    // Taille en nombre d'INTERSECTIONS
    public static int ROWS = 8; // 8 intersections verticales (indices 0..7)
    public static int COLS = 8; // 8 intersections horizontales (indices 0..7)

    // Coûts des segments - DIMENSIONS CORRECTES
    // Horizontal: ROWS x (COLS - 1) -> chaque intersection (r,c) a une arête vers
    // la droite si c < COLS-1
    public static int[][] H_COSTS = new int[ROWS][COLS - 1];
    // Vertical: (ROWS - 1) x COLS -> chaque intersection (r,c) a une arête vers le
    // bas si r < ROWS-1
    public static int[][] V_COSTS = new int[ROWS - 1][COLS];

    // Entités
    public static List<Store> stores = new ArrayList<>();
    public static List<Customer> customers = new ArrayList<>();
    public static List<Tunnel> tunnels = new ArrayList<>();

    static {
        initTestData();
    }

    private static void initTestData() {
        // Vider les listes
        stores.clear();
        customers.clear();
        tunnels.clear();

        // Initialiser les coûts (0-4) pour les arêtes horizontales et verticales
        Random rand = new Random();

        // Horizontales : ROWS x (COLS - 1)
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS - 1; c++) {
                H_COSTS[r][c] = rand.nextInt(5); // 0-4
            }
        }

        // Verticales : (ROWS - 1) x COLS
        for (int r = 0; r < ROWS - 1; r++) {
            for (int c = 0; c < COLS; c++) {
                V_COSTS[r][c] = rand.nextInt(5); // 0-4
            }
        }

        // Ajouter des entités de TEST VISIBLE (veiller à rester dans 0..ROWS-1 /
        // 0..COLS-1)
        stores.add(new Store(new Position(1, 1), "S1", Color.RED));
        stores.add(new Store(new Position(5, 6), "S2", Color.RED));

        customers.add(new Customer(new Position(3, 4), "C1", Color.GREEN));
        customers.add(new Customer(new Position(6, 2), "C2", Color.GREEN));

        tunnels.add(new Tunnel(
                new Position(2, 3),
                new Position(4, 5),
                "T1",
                Color.ORANGE));
    }

    // Helpers optionnels
    public static int getHorizontalCost(int row, int col) {
        return H_COSTS[row][col];
    }

    public static int getVerticalCost(int row, int col) {
        return V_COSTS[row][col];
    }
}
