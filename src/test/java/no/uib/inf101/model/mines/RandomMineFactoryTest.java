package no.uib.inf101.model.mines;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.model.MineSweeperBoard;

public class RandomMineFactoryTest {
    
    @Test
    public void testReturnsCorrectNumberOfMines() {
        MineSweeperBoard board = new MineSweeperBoard(8, 10, 10);
        RandomMineFactory factory = new RandomMineFactory();
        List<CellPosition> mines = factory.getMinePositions(board, 10, new ArrayList<>());
        assertEquals(10, mines.size());
    }
 
    @Test
    public void testNoMinesOnExcludedPositions() {
        MineSweeperBoard board = new MineSweeperBoard(8, 10, 10);
        RandomMineFactory factory = new RandomMineFactory();
        List<CellPosition> exclude = List.of(
            new CellPosition(4, 5),
            new CellPosition(3, 4),
            new CellPosition(3, 5),
            new CellPosition(3, 6),
            new CellPosition(4, 4),
            new CellPosition(4, 6),
            new CellPosition(5, 4),
            new CellPosition(5, 5),
            new CellPosition(5, 6)
        );
        List<CellPosition> mines = factory.getMinePositions(board, 10, exclude);
        for (CellPosition mine : mines) {
            assertFalse(exclude.contains(mine));
        }
    }
 
    @Test
    public void testNoDuplicateMinePositions() {
        MineSweeperBoard board = new MineSweeperBoard(8, 10, 10);
        RandomMineFactory factory = new RandomMineFactory();
        List<CellPosition> mines = factory.getMinePositions(board, 10, new ArrayList<>());
        Set<CellPosition> unique = new HashSet<>(mines);
        assertEquals(mines.size(), unique.size());
    }
 
    @Test
    public void testAllMinesAreOnGrid() {
        MineSweeperBoard board = new MineSweeperBoard(8, 10, 10);
        RandomMineFactory factory = new RandomMineFactory();
        List<CellPosition> mines = factory.getMinePositions(board, 10, new ArrayList<>());
        for (CellPosition pos : mines) {
            assertTrue(board.positionIsOnGrid(pos));
        }
    }
 
    @Test
    public void testSeededFactoryGivesSameResult() {
        MineSweeperBoard board = new MineSweeperBoard(8, 10, 10);
        RandomMineFactory factory1 = new RandomMineFactory(42);
        RandomMineFactory factory2 = new RandomMineFactory(42);
        List<CellPosition> mines1 = factory1.getMinePositions(board, 10, new ArrayList<>());
        List<CellPosition> mines2 = factory2.getMinePositions(board, 10, new ArrayList<>());
        assertEquals(mines1, mines2);
    }
 
    @Test
    public void testDifferentSeedsGiveDifferentResults() {
        MineSweeperBoard board = new MineSweeperBoard(8, 10, 10);
        RandomMineFactory factory1 = new RandomMineFactory(1);
        RandomMineFactory factory2 = new RandomMineFactory(2);
        List<CellPosition> mines1 = factory1.getMinePositions(board, 10, new ArrayList<>());
        List<CellPosition> mines2 = factory2.getMinePositions(board, 10, new ArrayList<>());
        assertNotEquals(mines1, mines2);
    }
}
