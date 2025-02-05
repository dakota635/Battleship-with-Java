package battleship.board;

public record Coordinates (int row, int col) implements Comparable<Coordinates> {

    public static Coordinates fromString(String coordinates) {
        int row = coordinates.charAt(0) - 'A' + 1;
        int col = Integer.parseInt(coordinates.substring(1));

        return new Coordinates(row, col);
    }

    public boolean isOutOfBounds(int totalRows, int totalCols) {
        return (!(row >= 1 && row <= totalRows && col >= 1 && col <= totalCols));
    }

    public boolean isRelationshipHorizontal(Coordinates coordinates) {
        return this.row() == coordinates.row();
    }

    @Override
    public int compareTo(Coordinates coordinates) {
        if (this.row == coordinates.row() && (this.col == coordinates.col())) {
            return 0;
        } else if (this.row > coordinates.row() || (this.col > coordinates.col())) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return String.format("Row: %d%nCol: %d", this.row, this.col);
    }
}