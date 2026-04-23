package no.uib.inf101.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;

public class MineSweeperBoardTest {
    @Test
    public void testDimensions() {
        MineSweeperBoard board = new MineSweeperBoard(8, 10, 10);
        assertEquals(8, board.rows());
        assertEquals(10, board.cols());
        assertEquals(10, board.getMines());
    }
 
    @Test
    public void testDefaultCells() {
        MineSweeperBoard board = new MineSweeperBoard(4, 4, 5);
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
        MineSweeperBoard board = new MineSweeperBoard(4, 4, 5);
        CellPosition pos = new CellPosition(1, 1);
        GridCell cell = new GridCell(true, false, false, 0);
        board.set(pos, cell);
        assertEquals(cell, board.get(pos));
    }
 
    @Test
    public void testRevealAllMines() {
        MineSweeperBoard board = new MineSweeperBoard(4, 4, 0);
        CellPosition minePos = new CellPosition(0, 0);
        board.set(minePos, new GridCell(true, false, false, 0));
        board.revealAllMines();
        assertTrue(board.get(minePos).revealed());
        assertFalse(board.get(new CellPosition(1, 1)).revealed());
    }
 
    @Test
    public void testRevealAllMinesIncludesFlagged() {
        MineSweeperBoard board = new MineSweeperBoard(4, 4, 0);
        CellPosition minePos = new CellPosition(0, 0);
        board.set(minePos, new GridCell(true, false, true, 0)); // flagged mine
        board.revealAllMines();
        assertTrue(board.get(minePos).revealed());
    }
 
    @Test
    public void testAllSafeCellsRevealed() {
        MineSweeperBoard board = new MineSweeperBoard(2, 2, 0);
        board.set(new CellPosition(0, 0), new GridCell(true, false, false, 0));
        board.set(new CellPosition(0, 1), new GridCell(false, true, false, 0));
        board.set(new CellPosition(1, 0), new GridCell(false, true, false, 0));
        board.set(new CellPosition(1, 1), new GridCell(false, true, false, 0));
        assertTrue(board.allSafeCellsRevealed());
    }
 
    @Test
    public void testNotAllSafeCellsRevealed() {
        MineSweeperBoard board = new MineSweeperBoard(2, 2, 0);
        board.set(new CellPosition(0, 0), new GridCell(true, false, false, 0));
        board.set(new CellPosition(0, 1), new GridCell(false, false, false, 0));
        assertFalse(board.allSafeCellsRevealed());
    }
 
    @Test
    public void testGetNeighborsCenterCell() {
        MineSweeperBoard board = new MineSweeperBoard(3, 3, 0);
        assertEquals(8, board.getNeighbors(new CellPosition(1, 1)).size());
    }
 
    @Test
    public void testGetNeighborsCornerCell() {
        MineSweeperBoard board = new MineSweeperBoard(3, 3, 0);
        assertEquals(3, board.getNeighbors(new CellPosition(0, 0)).size());
    }
 
    @Test
    public void testGetNeighborsEdgeCell() {
        MineSweeperBoard board = new MineSweeperBoard(3, 3, 0);
        assertEquals(5, board.getNeighbors(new CellPosition(0, 1)).size());
    }
 
    @Test
    public void testGetNeighborsThrowsOnNull() {
        MineSweeperBoard board = new MineSweeperBoard(3, 3, 0);
        assertThrows(IllegalArgumentException.class, () -> board.getNeighbors(null));
    }
 
    @Test
    public void testGetNeighborsThrowsOnOutOfBounds() {
        MineSweeperBoard board = new MineSweeperBoard(3, 3, 0);
        assertThrows(IllegalArgumentException.class, () -> board.getNeighbors(new CellPosition(5, 5)));
    }
 
    @Test
    public void testConstructorWithIGrid() {
        MineSweeperBoard board = new MineSweeperBoard(3, 3, 5);
        MineSweeperBoard board2 = new MineSweeperBoard(board.getGrid(), 5);
        assertEquals(3, board2.rows());
        assertEquals(3, board2.cols());
        assertEquals(5, board2.getMines());
    }
}
