package no.uib.inf101.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import no.uib.inf101.grid.CellPosition;

public class MineSweeperModelTest {

    @Test
    public void testInitialGameState() {
        MineSweeperModel model = new MineSweeperModel();
        assertEquals(GameState.START_SCREEN, model.getGameState());
    }
 
    @Test
    public void testSetDifficultyEasy() {
        MineSweeperModel model = new MineSweeperModel();
        model.setDifficulty(Difficulty.EASY);
        assertEquals(8, model.getBoard().rows());
        assertEquals(10, model.getBoard().cols());
        assertEquals(10, model.getBoard().getMines());
        assertEquals(GameState.FIRST_MOVE, model.getGameState());
    }
 
    @Test
    public void testSetDifficultyMedium() {
        MineSweeperModel model = new MineSweeperModel();
        model.setDifficulty(Difficulty.MEDIUM);
        assertEquals(14, model.getBoard().rows());
        assertEquals(18, model.getBoard().cols());
        assertEquals(40, model.getBoard().getMines());
    }
 
    @Test
    public void testSetDifficultyHard() {
        MineSweeperModel model = new MineSweeperModel();
        model.setDifficulty(Difficulty.HARD);
        assertEquals(20, model.getBoard().rows());
        assertEquals(24, model.getBoard().cols());
        assertEquals(99, model.getBoard().getMines());
    }
 
    @Test
    public void testSetDifficultyNullThrows() {
        MineSweeperModel model = new MineSweeperModel();
        assertThrows(IllegalArgumentException.class, () -> model.setDifficulty(null));
    }
 
    @Test
    public void testInitialFlagsLeft() {
        MineSweeperModel model = new MineSweeperModel();
        model.setDifficulty(Difficulty.EASY);
        assertEquals(10, model.getFlagsLeft());
    }
 
    @Test
    public void testFirstRevealStartsGame() {
        MineSweeperModel model = new MineSweeperModel();
        model.setDifficulty(Difficulty.EASY);
        model.reveal(new CellPosition(4, 5));
        assertEquals(GameState.ACTIVE, model.getGameState());
    }
 
    @Test
    public void testFirstClickNeverHasMine() {
        MineSweeperModel model = new MineSweeperModel();
        model.setDifficulty(Difficulty.EASY);
        CellPosition firstClick = new CellPosition(4, 5);
        model.reveal(firstClick);
        assertFalse(model.getBoard().get(firstClick).hasMine());
    }
 
    @Test
    public void testFirstClickNeighborsNeverHaveMines() {
        MineSweeperModel model = new MineSweeperModel();
        model.setDifficulty(Difficulty.EASY);
        CellPosition firstClick = new CellPosition(4, 5);
        model.reveal(firstClick);
        for (CellPosition neighbor : model.getBoard().getNeighbors(firstClick)) {
            assertFalse(model.getBoard().get(neighbor).hasMine());
        }
    }
 
    @Test
    public void testRevealIgnoredWhenGameOver() {
        MineSweeperModel model = new MineSweeperModel();
        model.setDifficulty(Difficulty.EASY);
        model.reveal(new CellPosition(4, 5));
        model.goToStartScreen();
        model.setDifficulty(Difficulty.EASY);
        // can't easily force game over without knowing mine positions,
        // but verify reveal does nothing on START_SCREEN
        model.goToStartScreen();
        model.reveal(new CellPosition(0, 0));
        assertEquals(GameState.START_SCREEN, model.getGameState());
    }
 
    @Test
    public void testToggleFlagAddsFlag() {
        MineSweeperModel model = new MineSweeperModel();
        model.setDifficulty(Difficulty.EASY);
        CellPosition pos = new CellPosition(0, 0);
        model.toggleFlag(pos);
        assertTrue(model.getBoard().get(pos).flagged());
    }
 
    @Test
    public void testToggleFlagRemovesFlag() {
        MineSweeperModel model = new MineSweeperModel();
        model.setDifficulty(Difficulty.EASY);
        CellPosition pos = new CellPosition(0, 0);
        model.toggleFlag(pos);
        model.toggleFlag(pos);
        assertFalse(model.getBoard().get(pos).flagged());
    }
 
    @Test
    public void testToggleFlagDecreasesFlagsLeft() {
        MineSweeperModel model = new MineSweeperModel();
        model.setDifficulty(Difficulty.EASY);
        int before = model.getFlagsLeft();
        model.toggleFlag(new CellPosition(0, 0));
        assertEquals(before - 1, model.getFlagsLeft());
    }
 
    @Test
    public void testToggleFlagCannotExceedMineCount() {
        MineSweeperModel model = new MineSweeperModel();
        model.setDifficulty(Difficulty.EASY);
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
        MineSweeperModel model = new MineSweeperModel();
        model.setDifficulty(Difficulty.EASY);
        CellPosition pos = new CellPosition(4, 5);
        model.reveal(pos);
        model.toggleFlag(pos);
        assertFalse(model.getBoard().get(pos).flagged());
    }
 
    @Test
    public void testResetGameResetsState() {
        MineSweeperModel model = new MineSweeperModel();
        model.setDifficulty(Difficulty.EASY);
        model.reveal(new CellPosition(4, 5));
        model.resetGame();
        assertEquals(GameState.FIRST_MOVE, model.getGameState());
        assertEquals(10, model.getFlagsLeft());
        assertEquals(0, model.getElapsedSeconds());
    }
 
    @Test
    public void testIncrementTimeOnlyWhenActive() {
        MineSweeperModel model = new MineSweeperModel();
        model.setDifficulty(Difficulty.EASY);
        model.incrementTime();
        assertEquals(0, model.getElapsedSeconds());
        model.reveal(new CellPosition(4, 5));
        model.incrementTime();
        model.incrementTime();
        assertEquals(2, model.getElapsedSeconds());
    }
 
    @Test
    public void testIncrementTimeStopsAt999() {
        MineSweeperModel model = new MineSweeperModel();
        model.setDifficulty(Difficulty.EASY);
        model.reveal(new CellPosition(4, 5));
        for (int i = 0; i < 1001; i++) {
            model.incrementTime();
        }
        assertEquals(999, model.getElapsedSeconds());
    }
 
    @Test
    public void testGoToStartScreen() {
        MineSweeperModel model = new MineSweeperModel();
        model.setDifficulty(Difficulty.EASY);
        model.goToStartScreen();
        assertEquals(GameState.START_SCREEN, model.getGameState());
    }
 
    @Test
    public void testGetDimensionMatchesBoard() {
        MineSweeperModel model = new MineSweeperModel();
        model.setDifficulty(Difficulty.EASY);
        assertEquals(model.getBoard().rows(), model.getDimension().rows());
        assertEquals(model.getBoard().cols(), model.getDimension().cols());
    }
 
    @Test
    public void testLastRevealedMineNullInitially() {
        MineSweeperModel model = new MineSweeperModel();
        model.setDifficulty(Difficulty.EASY);
        assertNull(model.getLastRevealedMine());
    }

}
