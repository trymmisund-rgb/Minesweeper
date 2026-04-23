package no.uib.inf101.controller;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.model.Difficulty;
import no.uib.inf101.model.GameState;

public interface ControllableMineSweeperModel {

    public void reveal(CellPosition pos);

    public void toggleFlag(CellPosition pos);

    public GameState getGameState();

    public void startGame(CellPosition pos);

    public void resetGame();

    public void setDifficulty(Difficulty difficulty);

    public int getElapsedSeconds();

    public void incrementTime();

    public void goToStartScreen();

}
