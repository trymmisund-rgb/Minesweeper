package no.uib.inf101.grid;

public class Grid implements IGrid{

    private int rows;
    private int cols;
    private GridCell[][] cells;

    public Grid(int rows, int cols) {
        if (rows < 0 || cols < 0) 
            throw new IllegalArgumentException();

        this.rows = rows;
        this.cols = cols;
        this.cells = new GridCell[rows][cols];
    }

    public Grid(int rows, int cols, GridCell defaultValue) {
        if (rows < 0 || cols < 0)
            throw  new IllegalArgumentException();

        this.cells = new GridCell[rows][cols];
        this.cols = cols;
        this.rows = rows;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                cells[row][col] = defaultValue;
            }
        }
    }

    @Override
    public int rows() {
        return rows;
    }

    @Override
    public int cols() {
        return cols;
    }

    @Override
    public void set(CellPosition pos, GridCell cell) {
        if (pos == null)
            throw new IllegalArgumentException();

        if (!positionIsOnGrid(pos))
            throw new IndexOutOfBoundsException();

        cells[pos.row()][pos.col()] = cell;
    }

    @Override
    public GridCell get(CellPosition pos) {
        if (pos == null)
            throw new IllegalArgumentException();

        if (!positionIsOnGrid(pos))
            throw new IndexOutOfBoundsException();

        return cells[pos.row()][pos.col()];
    }

}
