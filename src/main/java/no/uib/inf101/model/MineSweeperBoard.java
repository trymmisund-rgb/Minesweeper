package no.uib.inf101.model;

import java.util.ArrayList;
import java.util.List;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.Grid;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.IGrid;

/**
 * A pure data container representing the Minesweeper board.
 * Implements IGrid to provide direct cell access and grid navigation.
 * All game logic is handled by MineSweeperModel.
 */

public class MineSweeperBoard implements IGrid{
    
    private final IGrid grid;
    private final int mines;

    /**
     * Constructs a new board with the given dimensions and mine count.
     * All cells are initialized to safe, unrevealed, unflagged cells with 0 neighbour mines.
     *
     * @param rows  number of rows
     * @param cols  number of columns
     * @param mines number of mines to be placed (placed later by the model)
     */
    public MineSweeperBoard(int rows, int cols, int mines) {
        this.grid = new Grid(rows, cols, new GridCell(false, false, false, 0));
        this.mines = mines;
    }
   
    /**
     * Constructs a board using an existing IGrid and a mine count.
     * Useful for testing with a pre-configured grid.
     *
     * @param grid  the grid to use as the board
     * @param mines number of mines
     */
    public MineSweeperBoard(IGrid grid, int mines) {
        this.grid = grid;
        this.mines = mines;
    }

    /**
     * Gets the neighbouring cells of the given position.
     * returns between 3 and 8 neighbours depending on whether the position
     * is in a corner, on an edge, or in the middle of the board.
     * 
     * @param pos the position to get neighbours for
     * @return a list of all valid neighbouring CellPositions
     * @throws IllegalArgumentException if pos is null or not on the grid
     */
    public List<CellPosition> getNeighbors(CellPosition pos){
        if (pos == null || !positionIsOnGrid(pos))
            throw new IllegalArgumentException();
        
        List<CellPosition> neighbors = new ArrayList<>();
        for (int delta_row = -1; delta_row <= 1; delta_row++) {
            for (int delta_col = -1; delta_col <= 1; delta_col++){
                if(delta_row==0 && delta_col==0)
                    continue;
           
                CellPosition neighbor = new CellPosition(pos.row() + delta_row, pos.col() + delta_col);
                if(positionIsOnGrid(neighbor))
                    neighbors.add(neighbor);
                
            }
        }
        return neighbors;
    }

    /**
     * Returns the underlying IGrid.
     *
     * @return the grid backing this board
     */
    public IGrid getGrid() {
        return grid; 
    }

    /**
     * Returns the total number of mines on this board.
     *
     * @return number of mines
     */
    public int getMines() {
        return mines; 
    }

    @Override
    public int rows() {
        return grid.rows();
    }

    @Override
    public int cols() {
        return grid.cols();
    }

    @Override
    public void set(CellPosition pos, GridCell cell) {
        grid.set(pos, cell);
    }

    @Override
    public GridCell get(CellPosition pos) {
        return grid.get(pos);
    }
}
