package no.uib.inf101.grid;

import java.util.Iterator;

/**
 * An iterable over all positions in a grid, traversing row by row
 * from top-left to bottom-right.
 */

public class Positions implements Iterable<CellPosition>{
    private final GridDimension grid;
	
	public Positions(GridDimension grid){
		this.grid = grid;
	}
	
	@Override
	public Iterator<CellPosition> iterator() {
		return new PositionIterator(grid);
	}

}

/**
 * Iterator that traverses all positions in a grid row by row,
 * from top-left to bottom-right.
 * Advances column by column, moving to the next row when the end
 * of a row is reached.
 */

class PositionIterator implements Iterator<CellPosition>{

	private final GridDimension grid;
	private CellPosition current = new CellPosition(0,0);
	
	public PositionIterator(GridDimension grid) {
		this.grid = grid;
	}

	@Override
	public boolean hasNext() {
		return grid.positionIsOnGrid(current);
	}

	@Override
	public CellPosition next() {
		CellPosition next = current;
		current = new CellPosition(current.row(),current.col()+1);
		if(!grid.positionIsOnGrid(current)) {
			current = new CellPosition(current.row()+1, 0);
		}
		return next;
	}
	
}

