package no.uib.inf101.model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.model.mines.FixedMineFactory;

public class MineSweeperModelTest {

    private MineSweeperModel model;

    @BeforeEach
    public void setUp() {
        model = new MineSweeperModel();
        model.setDifficulty(Difficulty.EASY);
    }

    private MineSweeperModel modelWithMineAt(CellPosition minePos) {
        MineSweeperModel m = new MineSweeperModel(new FixedMineFactory(List.of(minePos)));
        m.setDifficulty(Difficulty.EASY);
        return m;
    }
    @Test
    public void testInitialGameState() {
        MineSweeperModel freshModel = new MineSweeperModel();
        assertEquals(GameState.START_SCREEN, freshModel.getGameState());
    }
 
    @Test
    public void testSetDifficultyEasy() {
        assertEquals(8, model.getBoard().rows());
        assertEquals(10, model.getBoard().cols());
        assertEquals(10, model.getBoard().getMines());
        assertEquals(GameState.FIRST_MOVE, model.getGameState());
    }
 
    @Test
    public void testSetDifficultyMedium() {
        model.setDifficulty(Difficulty.MEDIUM);
        assertEquals(14, model.getBoard().rows());
        assertEquals(18, model.getBoard().cols());
        assertEquals(40, model.getBoard().getMines());
    }
 
    @Test
    public void testSetDifficultyHard() {
        model.setDifficulty(Difficulty.HARD);
        assertEquals(20, model.getBoard().rows());
        assertEquals(24, model.getBoard().cols());
        assertEquals(99, model.getBoard().getMines());
    }
 
    @Test
    public void testSetDifficultyNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> model.setDifficulty(null));
    }
 
    @Test
    public void testInitialFlagsLeft() {
        assertEquals(10, model.getFlagsLeft());
    }
 
    @Test
    public void testFirstRevealStartsGame() {
        model.reveal(new CellPosition(4, 5));
        assertEquals(GameState.ACTIVE, model.getGameState());
    }
 
    @Test
    public void testFirstClickNeverHasMine() {
        CellPosition firstClick = new CellPosition(4, 5);
        model.reveal(firstClick);
        assertFalse(model.getBoard().get(firstClick).hasMine());
    }
 
    @Test
    public void testFirstClickNeighborsNeverHaveMines() {
        CellPosition firstClick = new CellPosition(4, 5);
        model.reveal(firstClick);
        for (CellPosition neighbor : model.getBoard().getNeighbors(firstClick)) {
            assertFalse(model.getBoard().get(neighbor).hasMine());
        }
    }
 
    @Test
    public void testRevealIgnoredOnStartScreen() {
        model.goToStartScreen();
        model.reveal(new CellPosition(0, 0));
        assertEquals(GameState.START_SCREEN, model.getGameState());
    }
 
    @Test
    public void testRevealMineEndsGame() {
        CellPosition minePos = new CellPosition(0, 1);
        MineSweeperModel m = modelWithMineAt(minePos);
        m.reveal(new CellPosition(4, 5));
        m.reveal(minePos);
        assertEquals(GameState.GAME_OVER, m.getGameState());
    }
 
    @Test
    public void testRevealMineSetsLastRevealedMine() {
        CellPosition minePos = new CellPosition(0, 1);
        MineSweeperModel m = modelWithMineAt(minePos);
        m.reveal(new CellPosition(4, 5));
        m.reveal(minePos);
        assertEquals(minePos, m.getLastRevealedMine());
    }
 
    @Test
    public void testRevealAllMinesOnGameOver() {
        CellPosition minePos = new CellPosition(0, 1);
        MineSweeperModel m = modelWithMineAt(minePos);
        m.reveal(new CellPosition(4, 5));
        m.reveal(minePos);
        assertTrue(m.getBoard().get(minePos).revealed());
    }
 
    @Test
    public void testCannotRevealAfterGameOver() {
        CellPosition minePos = new CellPosition(0, 1);
        MineSweeperModel m = modelWithMineAt(minePos);
        m.reveal(new CellPosition(4, 5));
        m.reveal(minePos);
        m.reveal(new CellPosition(3, 3));
        assertEquals(GameState.GAME_OVER, m.getGameState());
    }
 
    @Test
    public void testToggleFlagAddsFlag() {
        CellPosition pos = new CellPosition(0, 0);
        model.toggleFlag(pos);
        assertTrue(model.getBoard().get(pos).flagged());
    }
 
    @Test
    public void testToggleFlagRemovesFlag() {
        CellPosition pos = new CellPosition(0, 0);
        model.toggleFlag(pos);
        model.toggleFlag(pos);
        assertFalse(model.getBoard().get(pos).flagged());
    }
 
    @Test
    public void testToggleFlagDecreasesFlagsLeft() {
        int before = model.getFlagsLeft();
        model.toggleFlag(new CellPosition(0, 0));
        assertEquals(before - 1, model.getFlagsLeft());
    }
 
    @Test
    public void testToggleFlagIncreasesWhenRemoved() {
        CellPosition pos = new CellPosition(0, 0);
        model.toggleFlag(pos);
        int before = model.getFlagsLeft();
        model.toggleFlag(pos);
        assertEquals(before + 1, model.getFlagsLeft());
    }
 
    @Test
    public void testToggleFlagCannotExceedMineCount() {
        int flagged = 0;
        for (CellPosition pos : model.getBoard().positions()) {
            if (flagged >= model.getBoard().getMines()) break;
            model.toggleFlag(pos);
            flagged++;
        }
        assertEquals(0, model.getFlagsLeft());
        for (CellPosition pos : model.getBoard().positions()) {
            if (!model.getBoard().get(pos).flagged()) {
                model.toggleFlag(pos);
                assertFalse(model.getBoard().get(pos).flagged());
                break;
            }
        }
    }
 
    @Test
    public void testCannotFlagRevealedCell() {
        CellPosition pos = new CellPosition(4, 5);
        model.reveal(pos);
        model.toggleFlag(pos);
        assertFalse(model.getBoard().get(pos).flagged());
    }
 
    @Test
    public void testResetGameResetsState() {
        model.reveal(new CellPosition(4, 5));
        model.resetGame();
        assertEquals(GameState.FIRST_MOVE, model.getGameState());
        assertEquals(10, model.getFlagsLeft());
        assertEquals(0, model.getElapsedSeconds());
    }
 
    @Test
    public void testResetGameClearsCells() {
        model.reveal(new CellPosition(4, 5));
        model.resetGame();
        for (CellPosition pos : model.getBoard().positions()) {
            assertFalse(model.getBoard().get(pos).revealed());
            assertFalse(model.getBoard().get(pos).flagged());
            assertFalse(model.getBoard().get(pos).hasMine());
        }
    }
 
    @Test
    public void testIncrementTimeOnlyWhenActive() {
        model.incrementTime();
        assertEquals(0, model.getElapsedSeconds());
        model.reveal(new CellPosition(4, 5));
        model.incrementTime();
        model.incrementTime();
        assertEquals(2, model.getElapsedSeconds());
    }
 
    @Test
    public void testIncrementTimeStopsAt999() {
        model.reveal(new CellPosition(4, 5));
        for (int i = 0; i < 1001; i++) {
            model.incrementTime();
        }
        assertEquals(999, model.getElapsedSeconds());
    }
 
    @Test
    public void testIncrementTimeDoesNotCountWhenGameOver() {
        CellPosition minePos = new CellPosition(0, 1);
        MineSweeperModel m = modelWithMineAt(minePos);
        m.reveal(new CellPosition(4, 5));
        m.reveal(minePos);
        m.incrementTime();
        assertEquals(0, m.getElapsedSeconds());
    }
 
    @Test
    public void testGoToStartScreen() {
        model.goToStartScreen();
        assertEquals(GameState.START_SCREEN, model.getGameState());
    }
 
    @Test
    public void testGetDimensionMatchesBoard() {
        assertEquals(model.getBoard().rows(), model.getDimension().rows());
        assertEquals(model.getBoard().cols(), model.getDimension().cols());
    }
 
    @Test
    public void testLastRevealedMineNullInitially() {
        assertNull(model.getLastRevealedMine());
    }
}
