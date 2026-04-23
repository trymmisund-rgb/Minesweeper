package no.uib.inf101.grid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class GridCellTest {
    
    @Test
    public void testFields() {
        GridCell cell = new GridCell(true, false, true, 3);
        assertTrue(cell.hasMine());
        assertFalse(cell.revealed());
        assertTrue(cell.flagged());
        assertEquals(3, cell.neighbormines());
    }
 
    @Test
    public void testEquality() {
        GridCell a = new GridCell(false, true, false, 2);
        GridCell b = new GridCell(false, true, false, 2);
        assertEquals(a, b);
    }
 
    @Test
    public void testInequality() {
        GridCell a = new GridCell(false, true, false, 2);
        GridCell b = new GridCell(true, false, true, 0);
        assertNotEquals(a, b);
    }
 
    @Test
    public void testDefaultSafeCell() {
        GridCell cell = new GridCell(false, false, false, 0);
        assertFalse(cell.hasMine());
        assertFalse(cell.revealed());
        assertFalse(cell.flagged());
        assertEquals(0, cell.neighbormines());
    }
}
