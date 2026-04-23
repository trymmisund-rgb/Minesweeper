package no.uib.inf101.model.mines;

import java.util.List;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.model.MineSweeperBoard;

public interface MineFactory {

    List<CellPosition> getMinePositions(MineSweeperBoard board, int mineCount, List<CellPosition> exclude);
}
