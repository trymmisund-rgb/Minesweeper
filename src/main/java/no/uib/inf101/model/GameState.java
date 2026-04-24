package no.uib.inf101.model;

/**
 * Represents the possible states of a Minesweeper game.
 *
 * START_SCREEN - the difficulty selection screen shown before the game begins
 * FIRST_MOVE   - the game has started but no cell has been revealed yet; mines are not yet placed
 * ACTIVE       - the game is in progress
 * GAME_OVER    - the player revealed a mine and lost
 * GAME_WON     - the player revealed all safe cells and won
 */

public enum GameState {
    FIRST_MOVE,
    ACTIVE,
    GAME_OVER,
    GAME_WON,
    START_SCREEN
}
