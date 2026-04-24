package no.uib.inf101.model.mines;

import java.util.List;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.model.MineSweeperBoard;

public interface MineFactory {

    /**
     * Returns a list of positions where mines should be placed.
     *
     * @param board     the board on which mines will be placed
     * @param mineCount the number of mine positions to return
     * @param exclude   positions that must not contain mines (e.g. first click and its neighbours)
     * @return a list of CellPositions for mine placement
     */
    List<CellPosition> getMinePositions(MineSweeperBoard board, int mineCount, List<CellPosition> exclude);
}
