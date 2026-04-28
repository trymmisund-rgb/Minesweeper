package no.uib.inf101.view;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.model.GameState;
import no.uib.inf101.model.MineSweeperBoard;

/**
 * Defines the data the view is allowed to read from the model.
 * The view may only read through this interface, preventing it from
 * accidentally modifying game state.
 */

public interface ViewableMineSweeperModel {
    
    /**
     * Returns the board.
     *
     * @return the MineSweeperBoard
     */
    public MineSweeperBoard getBoard();

    /**
     * Returns the current game state.
     *
     * @return the current GameState
     */
    public GameState getGameState();

    /**
     * Returns the number of seconds elapsed since the game started.
     *
     * @return elapsed seconds, capped at 999
     */
    public int getElapsedSeconds();

    /**
     * Returns the number of flags the player has left to place.
     *
     * @return number of remaining flags
     */
    public int getFlagsLeft();

    /**
     * Returns the position of the mine the player last revealed, or null if none.
     * Used by the view to highlight the fatal mine in red.
     *
     * @return the last revealed mine position, or null
     */
    public CellPosition getLastRevealedMine();
}
