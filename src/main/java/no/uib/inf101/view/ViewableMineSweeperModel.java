package no.uib.inf101.view;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.model.GameState;
import no.uib.inf101.model.MineSweeperBoard;

public interface ViewableMineSweeperModel {
    
    public MineSweeperBoard getBoard();

    public GameState getGameState();

    public GridDimension getDimension();

    public int getElapsedSeconds();

    public int getFlagsLeft();

    public CellPosition getLastRevealedMine();
}
