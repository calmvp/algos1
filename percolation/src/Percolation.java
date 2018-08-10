import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final int VIRTUAL_TOP = 0;
    private final int virutalBottom;
    private final int gridSize;
    private int openSites = 0;
    private final WeightedQuickUnionUF weightedQuickUnionUF;
    private boolean[][] grid;


    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException("n must be greater than 0");
        }

        this.gridSize = n;
        this.grid = new boolean[gridSize][gridSize];
        this.weightedQuickUnionUF = new WeightedQuickUnionUF(gridSize * gridSize + 2);
        this.virutalBottom = gridSize * gridSize + 1;
    }

    public void open(int row, int col) {
        if (row <= 0 || col <= 0) {
            throw new java.lang.IllegalArgumentException("requested row and column must be greater than 0");
        }

        if (row > this.gridSize || col > this.gridSize) {
            throw new java.lang.IllegalArgumentException("requested row and column may not exceed the grid size");
        }
        int adjustedRow = row - 1;
        int adjustedCol = col - 1;
        if (!this.grid[adjustedRow][adjustedCol]) {

            this.grid[adjustedRow][adjustedCol] = true;
            this.openSites += 1;
            int ufIndex = this.mapGridCoordsToUfIndex(row, col);
            this.connectAdjacentElements(row, col, adjustedRow, adjustedCol, ufIndex);
        }
    }

    public boolean isOpen(int row, int col) {
        if (row <= 0 || col <= 0) {
            throw new java.lang.IllegalArgumentException("requested row and/or column must be within the grid");
        }

        int adjustedRow = row - 1;
        int adjustedCol = col - 1;
        return this.grid[adjustedRow][adjustedCol];
    }

    public boolean isFull(int row, int col) {
        if (row <= 0 || col <= 0) {
            throw new java.lang.IllegalArgumentException("requested row and/or column must be within the grid");
        }
        int ufIndex = this.mapGridCoordsToUfIndex(row, col);
        return this.weightedQuickUnionUF.connected(ufIndex, VIRTUAL_TOP);
    }

    public int numberOfOpenSites() {
        return this.openSites;
    }

    public boolean percolates() {
        return this.weightedQuickUnionUF.connected(VIRTUAL_TOP, this.virutalBottom);
    }

    private int mapGridCoordsToUfIndex(int row, int col) {
        return (this.gridSize * (row - 1)) + col;
    }

    private void connectAdjacentElements(int row, int col, int adjustedRow, int adjustedCol, int mappedIndex) {
        this.connectAbove(row, col, adjustedRow, adjustedCol, mappedIndex);
        this.connectBelow(row, col, adjustedRow, adjustedCol, mappedIndex);
        this.connectLeft(row, col, adjustedRow, adjustedCol, mappedIndex);
        this.connectRight(row, col, adjustedRow, adjustedCol, mappedIndex);
    }

    private void connectAbove(int row, int col, int adjustedRow, int adjustedCol, int mappedIndex) {
        // connect to virtual top if top row
        if (row == 1) {
            this.weightedQuickUnionUF.union(mappedIndex, VIRTUAL_TOP);
        } else {
            // verify element above is open. If so, connect them.
            if (this.grid[adjustedRow - 1][adjustedCol]) {
                int elementAboveIndex = this.mapGridCoordsToUfIndex(row -1, col);
                this.weightedQuickUnionUF.union(mappedIndex, elementAboveIndex);
            }
        }
    }

    private void connectBelow(int row, int col, int adjustedRow, int adjustedCol, int mappedIndex) {
        if (row == this.gridSize) {
            this.weightedQuickUnionUF.union(mappedIndex, this.virutalBottom);
        } else {
            if (this.grid[adjustedRow + 1][adjustedCol]) {
                int elementBelowIndex = this.mapGridCoordsToUfIndex(row + 1, col);
                this.weightedQuickUnionUF.union(mappedIndex, elementBelowIndex);
            }
        }

    }

    private void connectRight(int row, int col, int adjustedRow, int adjustedCol, int mappedIndex) {
        if (col < this.gridSize && this.grid[adjustedRow][adjustedCol + 1]) {
            int elementRight = this.mapGridCoordsToUfIndex(row, col + 1);
            this.weightedQuickUnionUF.union(mappedIndex, elementRight);
        }
    }

    private void connectLeft(int row, int col, int adjustedRow, int adjustedCol, int mappedIndex) {
        if (col > 1 && this.grid[adjustedRow][adjustedCol - 1]) {
            int elementLeft = this.mapGridCoordsToUfIndex(row, col -1);
            this.weightedQuickUnionUF.union(mappedIndex, elementLeft);
        }
    }
}
