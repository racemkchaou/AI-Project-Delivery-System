public class Position {

    public int row;
    public int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Position move(String direction) {
        int newRow = row;
        int newCol = col;

        switch (direction.toLowerCase()) {
            case "up":
                newRow = row - 1;
                break;

            case "down":
                newRow = row + 1;
                break;

            case "left":
                newCol = col - 1;
                break;

            case "right":
                newCol = col + 1;
                break;

            default:
                return this; // Mouvement non reconnu → aucune action
        }

        // 🔥 Vérification des limites de la grille
        if (newRow < 0 || newRow >= GridData.ROWS || newCol < 0 || newCol >= GridData.COLS) {
            // On reste à la position actuelle si mouvement interdit
            return this;
        }

        return new Position(newRow, newCol);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Position))
            return false;
        Position p = (Position) obj;
        return row == p.row && col == p.col;
    }

    @Override
    public int hashCode() {
        return row * 31 + col;
    }

    @Override
    public String toString() {
        return "(" + row + "," + col + ")";
    }
}
