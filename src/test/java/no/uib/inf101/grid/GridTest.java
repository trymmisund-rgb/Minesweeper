package no.uib.inf101.grid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class GridTest {

    @Test
    public void testDimensions() {
        Grid grid = new Grid(4, 6);
        assertEquals(4, grid.rows());
        assertEquals(6, grid.cols());
    }
 
    @Test
    public void testSetAndGet() {
        Grid grid = new Grid(3, 3);
        CellPosition pos = new CellPosition(1, 1);
        GridCell cell = new GridCell(true, false, false, 0);
        grid.set(pos, cell);
        assertEquals(cell, grid.get(pos));
    }
 
    @Test
    public void testDefaultValue() {
        GridCell defaultCell = new GridCell(false, false, false, 0);
        Grid grid = new Grid(3, 3, defaultCell);
        for (CellPosition pos : grid.positions()) {
            assertEquals(defaultCell, grid.get(pos));
        }
    }
 
    @Test
    public void testThrowsOnNegativeDimensions() {
        assertThrows(IllegalArgumentException.class, () -> new Grid(-1, 3));
        assertThrows(IllegalArgumentException.class, () -> new Grid(3, -1));
    }
 
    @Test
    public void testThrowsOnOutOfBounds() {
        Grid grid = new Grid(3, 3);
        assertThrows(IndexOutOfBoundsException.class, () -> grid.get(new CellPosition(5, 0)));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.set(new CellPosition(5, 0), new GridCell(false, false, false, 0)));
    }
 
    @Test
    public void testThrowsOnNull() {
        Grid grid = new Grid(3, 3);
        assertThrows(IllegalArgumentException.class, () -> grid.get(null));
        assertThrows(IllegalArgumentException.class, () -> grid.set(null, new GridCell(false, false, false, 0)));
    }
 
    @Test
    public void testPositionIsOnGrid() {
        Grid grid = new Grid(4, 6);
        assertTrue(grid.positionIsOnGrid(new CellPosition(0, 0)));
        assertTrue(grid.positionIsOnGrid(new CellPosition(3, 5)));
        assertFalse(grid.positionIsOnGrid(new CellPosition(4, 0)));
        assertFalse(grid.positionIsOnGrid(new CellPosition(0, 6)));
        assertFalse(grid.positionIsOnGrid(new CellPosition(-1, 0)));
        assertFalse(grid.positionIsOnGrid(null));
    }
 
    @Test
    public void testPositionsIteratesAllCells() {
        Grid grid = new Grid(3, 4);
        int count = 0;
        for (CellPosition pos : grid.positions()) {
            count++;
        }
        assertEquals(12, count);
    }
 
    @Test
    public void testPositionsOrder() {
        Grid grid = new Grid(2, 3);
        CellPosition[] expected = {
            new CellPosition(0, 0), new CellPosition(0, 1), new CellPosition(0, 2),
            new CellPosition(1, 0), new CellPosition(1, 1), new CellPosition(1, 2)
        };
        int i = 0;
        for (CellPosition pos : grid.positions()) {
            assertEquals(expected[i++], pos);
        }
    }
}
 
