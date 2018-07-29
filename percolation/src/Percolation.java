import java.util.Arrays;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int VIRTUAL_TOP = 0;
    private int virutalBottom;
    private int gridSize;
    private WeightedQuickUnionUF weightedQuickUnionUF;
    public boolean[][] grid;


    public Percolation(int n) throws IllegalArgumentException {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException("n must be greater than 0");
        }

        this.gridSize = n;
        this.grid = new boolean[gridSize][gridSize];
        this.weightedQuickUnionUF = new WeightedQuickUnionUF(gridSize * gridSize + 2);
        this.virutalBottom = gridSize * gridSize + 1;
    }

    public void open (int row, int col) throws IllegalArgumentException {
        if (row <= 0 || col <= 0 ) {
            throw new java.lang.IllegalArgumentException("requested row and column must be greater than 0");
        }

        if (row >= this.gridSize || col >= this.gridSize){
            throw new java.lang.IllegalArgumentException("requested row and column may not exceed the grid size");
        }
        int adjustedRow = row - 1;
        int adjustedCol = col - 1;
        this.grid[adjustedRow][adjustedCol] = true;

        int ufIndex = this.mapGridCoordsToUfIndex(row, col);
        this.connectAdjacentElements(row, col, adjustedRow, adjustedCol, ufIndex);
    }

    public boolean isOpen(int row, int col) throws IllegalArgumentException {
        if (row <= 0 || col <=0) {
            throw new java.lang.IllegalArgumentException("requested row and/or column must be within the grid");
        }

        int adjustedRow = row - 1;
        int adjustedCol = col - 1;
        return this.grid[adjustedRow][adjustedCol];
    }

    private int mapGridCoordsToUfIndex(int row, int col) {
        return (this.gridSize * (row - 1)) + col;
    }

    private void connectAdjacentElements(int row, int col, int adjustedRow, int adjustedCol, int mappedIndex){
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
            if (this.grid[adjustedRow + 1][adjustedCol] == true) {
                int elementAboveIndex = this.mapGridCoordsToUfIndex(row -1, col);
                this.weightedQuickUnionUF.union(mappedIndex, elementAboveIndex);
            }
        }
    }

    private void connectBelow(int row, int col, int adjustedRow, int adjustedCol, int mappedIndex) {
        if (row == this.gridSize) {
            this.weightedQuickUnionUF.union(mappedIndex, this.virutalBottom);
        } else {
            if (this.grid[adjustedRow - 1][adjustedCol] == true) {
                int elementBelowIndex = this.mapGridCoordsToUfIndex( row + 1, col);
                this.weightedQuickUnionUF.union(mappedIndex, elementBelowIndex);
            }
        }

    }

    private void connectRight(int row, int col, int adjustedRow, int adjustedCol, int mappedIndex){
        if (col < this.gridSize && this.grid[adjustedRow][adjustedCol + 1]){
            int elementRight = this.mapGridCoordsToUfIndex(row, col + 1);
            this.weightedQuickUnionUF.union(mappedIndex, elementRight);
        }
    }

    private void connectLeft(int row, int col, int adjustedRow, int adjustedCol, int mappedIndex) {
        if (col > 1 && this.grid[adjustedRow][adjustedCol - 1] == true) {
            int elementLeft = this.mapGridCoordsToUfIndex(row, col -1);
            this.weightedQuickUnionUF.union(mappedIndex, elementLeft);
        }
    }
    public static void main(String[] args) {
        Percolation perco = new Percolation(5);
        System.out.println(Arrays.toString(perco.grid[0]));
    }
}
