package no.uib.inf101.model.mines;

import java.util.List;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.model.MineSweeperBoard;

public class FixedMineFactory implements MineFactory{

    private final List<CellPosition> minePositions;

    public FixedMineFactory(List<CellPosition> minePositions) {
        this.minePositions = minePositions;
    }

    @Override
    public List<CellPosition> getMinePositions(MineSweeperBoard board, int mineCount, List<CellPosition> exclude) {
        return minePositions;
    }

}
