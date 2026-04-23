package no.uib.inf101.model.mines;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.model.MineSweeperBoard;

public class RandomMineFactory implements MineFactory{

    private final Random random;

    public RandomMineFactory() {
        this.random = new Random();
    }

    public RandomMineFactory(long seed) {
        this.random = new Random(seed);
    }

    @Override
    public List<CellPosition> getMinePositions(MineSweeperBoard board, int mineCount, List<CellPosition> exclude) {

        List<CellPosition> allPositions = new ArrayList<>();
        for (CellPosition pos : board.positions()){
            if (!exclude.contains(pos)){
                allPositions.add(pos);
            }
        }
        Collections.shuffle(allPositions, random);
        return allPositions.subList(0, mineCount);
    }

}
