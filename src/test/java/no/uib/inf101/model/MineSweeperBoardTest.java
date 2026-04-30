package no.uib.inf101.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;

public class MineSweeperBoardTest {

    private MineSweeperBoard board;
 
    @BeforeEach
    public void setUp() {
        board = new MineSweeperBoard(8, 10, 10);
    }

    @Test
    public void testDimensions() {
        assertEquals(8, board.rows());
        assertEquals(10, board.cols());
        assertEquals(10, board.getMines());
    }
 
    @Test
    public void testDefaultCells() {
        for (CellPosition pos : board.positions()) {
            GridCell cell = board.get(pos);
            assertFalse(cell.hasMine());
            assertFalse(cell.revealed());
            assertFalse(cell.flagged());
            assertEquals(0, cell.neighbormines());
        }
    }
 
    @Test
    public void testSetAndGet() {
        CellPosition pos = new CellPosition(1, 1);
        GridCell cell = new GridCell(true, false, false, 0);
        board.set(pos, cell);
        assertEquals(cell, board.get(pos));
    }
 
    @Test
    public void testGetNeighborsCenterCell() {
        MineSweeperBoard small = new MineSweeperBoard(3, 3, 0);
        assertEquals(8, small.getNeighbors(new CellPosition(1, 1)).size());
    }
 
    @Test
    public void testGetNeighborsCornerCell() {
        MineSweeperBoard small = new MineSweeperBoard(3, 3, 0);
        assertEquals(3, small.getNeighbors(new CellPosition(0, 0)).size());
    }
 
    @Test
    public void testGetNeighborsEdgeCell() {
        MineSweeperBoard small = new MineSweeperBoard(3, 3, 0);
        assertEquals(5, small.getNeighbors(new CellPosition(0, 1)).size());
    }
 
    @Test
    public void testGetNeighborsThrowsOnNull() {
        assertThrows(IllegalArgumentException.class, () -> board.getNeighbors(null));
    }
 
    @Test
    public void testGetNeighborsThrowsOnOutOfBounds() {
        assertThrows(IllegalArgumentException.class, () -> board.getNeighbors(new CellPosition(50, 50)));
    }
 
    @Test
    public void testConstructorWithIGrid() {
        MineSweeperBoard board2 = new MineSweeperBoard(board.getGrid(), 10);
        assertEquals(board.rows(), board2.rows());
        assertEquals(board.cols(), board2.cols());
        assertEquals(board.getMines(), board2.getMines());
    }
}
