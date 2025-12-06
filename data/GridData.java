package data;

import model.*;
import java.awt.Color;
import java.util.*;

public class GridData {

    public static final int ROWS = 8;
    public static final int COLS = 8;

    // Coûts des segments
    public static int[][] H_COSTS = new int[ROWS][COLS - 1];
    public static int[][] V_COSTS = new int[ROWS - 1][COLS];

    // Entités
    private static List<Store> stores = new ArrayList<>();
    private static List<Customer> customers = new ArrayList<>();
    private static List<Tunnel> tunnels = new ArrayList<>();

    static {
        initTestData();
    }

    private static void initTestData() {
        stores.clear();
        customers.clear();
        tunnels.clear();

        Random rand = new Random();

        // Initialiser les coûts horizontaux
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS - 1; c++) {
                H_COSTS[r][c] = rand.nextInt(5);
            }
        }

        // Initialiser les coûts verticaux
        for (int r = 0; r < ROWS - 1; r++) {
            for (int c = 0; c < COLS; c++) {
                V_COSTS[r][c] = rand.nextInt(5);
            }
        }

        // Ajouter les entités
        stores.add(new Store(new Position(1, 1), "S1", Color.GRAY));
        stores.add(new Store(new Position(6, 5), "S2", Color.GRAY));

        customers.add(new Customer(new Position(4, 3), "C1", Color.GREEN));
        customers.add(new Customer(new Position(2, 6), "C2", Color.GREEN));

        tunnels.add(new Tunnel(
                new Position(3, 2),
                new Position(5, 4),
                "T1",
                Color.ORANGE));
    }

    // Getters
    public static List<Store> getStores() {
        return new ArrayList<>(stores);
    }

    public static List<Customer> getCustomers() {
        return new ArrayList<>(customers);
    }

    public static List<Tunnel> getTunnels() {
        return new ArrayList<>(tunnels);
    }

    public static int getHorizontalCost(int row, int col) {
        if (row >= 0 && row < ROWS && col >= 0 && col < COLS - 1) {
            return H_COSTS[row][col];
        }
        return -1;
    }

    public static int getVerticalCost(int row, int col) {
        if (row >= 0 && row < ROWS - 1 && col >= 0 && col < COLS) {
            return V_COSTS[row][col];
        }
        return -1;
    }

    // Méthode pour réinitialiser les données
    public static void reset() {
        initTestData();
    }
}