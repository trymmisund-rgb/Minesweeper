package no.uib.inf101.controller;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.model.Difficulty;
import no.uib.inf101.model.GameState;

/**
 * Defines the actions available to the controller.
 * The controller may only interact with the model through this interface,
 * ensuring a clean separation between input handling and game logic.
 */

public interface ControllableMineSweeperModel {

    /** 
     * Reveals the cell at the given position.
     * On the first move, mines are placed before revealing.
     * If the cell has no neighbouring mines, neighbours are revealed recursively.
     * Sets game state to GAME_OVER if a mine is revealed, or GAME_WON if all safe cells are revealed.
     *
     * @param pos the position to reveal
     */
    public void reveal(CellPosition pos);

    /** 
     * Toggles a flag on the cell at the given position.
     * A flag cannot be placed on a revealed cell, and the number of flags
     * cannot exceed the total number of mines.
     *
     * @param pos the position to toggle the flag on
     */
    public void toggleFlag(CellPosition pos);

    /** 
     * Gets the current state of the Minesweeper game
     * 
     * @return the current gamestate the game is in
     */
    public GameState getGameState();

    /**  
     * Places mines on the board and calculates neighbour mine counts.
     * Called automatically on the first reveal. The first clicked cell
     * and its neighbours are guaranteed to be mine-free.
     *
     * @param pos the position of the first click
     * @throws IllegalArgumentException if pos is null or not on the grid
     */
    public void startGame(CellPosition pos);

    /** 
     * Resets the game with the current difficulty.
     * Creates a new board and resets flags, time, and game state to FIRST_MOVE.
     */
    public void resetGame();

    /** 
     * Sets the difficulty of the game based on what number you press
     * on the keyboard
     * 
     * @param difficulty the difficulty of the game which is pressed
     * @throws IllegalArgumentException if difficulty is null
     */
    public void setDifficulty(Difficulty difficulty);

    /** 
     * Gets the number of seconds spent on the game
     * 
     * @return seconds used to solve the board
     */
    public int getElapsedSeconds();

    /** 
     * Increments the elapsed time by one second.
     * Only increments when the game state is ACTIVE and time is below 999.
     */
    public void incrementTime();

    /**
     * Sets the game state to start screen
     */
    public void goToStartScreen();

}
