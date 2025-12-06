public class Position {

    public int x; // Coordonnée horizontale (colonne)
    public int y; // Coordonnée verticale (ligne)

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position move(String direction) {
        int newX = x;
        int newY = y;

        switch (direction.toLowerCase()) {
            case "up":
                newY = y - 1; // up = décrémenter y
                break;

            case "down":
                newY = y + 1; // down = incrémenter y
                break;

            case "left":
                newX = x - 1; // left = décrémenter x
                break;

            case "right":
                newX = x + 1; // right = incrémenter x
                break;

            default:
                return this;
        }

        // Vérification des limites
        if (newX < 0 || newX >= GridData.COLS || newY < 0 || newY >= GridData.ROWS) {
            return this;
        }

        return new Position(newX, newY);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Position))
            return false;
        Position p = (Position) obj;
        return x == p.x && y == p.y;
    }

    @Override
    public int hashCode() {
        return x * 31 + y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}