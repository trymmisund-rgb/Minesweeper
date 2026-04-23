package no.uib.inf101.model;

import java.util.ArrayList;
import java.util.List;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.Grid;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.IGrid;

public class MineSweeperBoard implements IGrid{
    
    private final IGrid grid;
    private final int mines;

    
    public MineSweeperBoard(int rows, int cols, int mines) {
        this.grid = new Grid(rows, cols, new GridCell(false, false, false, 0));
        this.mines = mines;
    }
   
    public MineSweeperBoard(IGrid grid, int mines) {
        this.grid = grid;
        this.mines = mines;
    }

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

    public void revealAllMines() {  
        for (CellPosition pos : positions()) {  
            GridCell cell = get(pos);  
            if (cell.hasMine()) {  
                set(pos, new GridCell(true, true, false, cell.neighbormines()));  
            }  
        }  
    }

    public boolean allSafeCellsRevealed() {
        for (CellPosition pos : positions()) {
            GridCell cell = get(pos);
            if (!cell.hasMine() && !cell.revealed()) {
                return false;
            }
        }
        return true;
    }

    public IGrid getGrid() {
        return grid; 
    }

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
