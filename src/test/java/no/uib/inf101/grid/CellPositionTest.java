package no.uib.inf101.grid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

public class CellPositionTest {
    
    @Test
    public void testRowAndCol() {
        CellPosition pos = new CellPosition(3, 5);
        assertEquals(3, pos.row());
        assertEquals(5, pos.col());
    }
 
    @Test
    public void testEquality() {
        CellPosition a = new CellPosition(1, 2);
        CellPosition b = new CellPosition(1, 2);
        assertEquals(a, b);
    }
 
    @Test
    public void testInequality() {
        CellPosition a = new CellPosition(1, 2);
        CellPosition b = new CellPosition(2, 1);
        assertNotEquals(a, b);
    }
 
    @Test
    public void testZeroPosition() {
        CellPosition pos = new CellPosition(0, 0);
        assertEquals(0, pos.row());
        assertEquals(0, pos.col());
    }
}
