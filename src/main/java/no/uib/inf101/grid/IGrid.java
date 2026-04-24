package no.uib.inf101.grid;

public interface IGrid extends GridDimension{
    /**
	 * Sets the value of a position in the grid. A subsequent call to {@link #get}
	 * with an equal position as argument will return the value which was set. The
	 * method will overwrite any previous value that was stored at the location.
	 * 
	 * @param pos   the position in which to store the value
	 * @param cell	the GridCell to store at the given position
	 * @throws IndexOutOfBoundsException if the position does not exist in the grid
	 */
	public void set(CellPosition pos, GridCell cell);

	/**
	 * Gets the current value at the given coordinate.
	 * 
	 * @param pos the position to get
	 * @return the value stored at the position
	 * @throws IndexOutOfBoundsException if the position does not exist in the grid
	 */
	public GridCell get(CellPosition pos);
}
