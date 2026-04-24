package no.uib.inf101.model.mines;

import java.util.List;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.model.MineSweeperBoard;

/**
 * A MineFactory that always returns a predetermined list of mine positions.
 * Used in tests to guarantee a known board layout.
 */

public class FixedMineFactory implements MineFactory{

    private final List<CellPosition> minePositions;

    /**
     * Constructs a FixedMineFactory with the given mine positions.
     *
     * @param minePositions the fixed list of positions to use as mines
     */
    public FixedMineFactory(List<CellPosition> minePositions) {
        this.minePositions = minePositions;
    }

    @Override
    public List<CellPosition> getMinePositions(MineSweeperBoard board, int mineCount, List<CellPosition> exclude) {
        return minePositions;
    }

}
