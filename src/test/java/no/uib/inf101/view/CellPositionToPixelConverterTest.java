package no.uib.inf101.view;

import java.awt.geom.Rectangle2D;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.Grid;
import no.uib.inf101.grid.GridDimension;

public class CellPositionToPixelConverterTest {

    private CellPositionToPixelConverter makeConverter(int rows, int cols) {
        GridDimension gd = new Grid(rows, cols);
        return new CellPositionToPixelConverter(new Rectangle2D.Double(50, 50, 500, 400), gd, 0);
    }

    @Test
    public void sanityTest() {
        GridDimension gd = new Grid(3, 4);
        CellPositionToPixelConverter converter = new CellPositionToPixelConverter(
                new Rectangle2D.Double(29, 29, 340, 240), gd, 30);
        Rectangle2D expected = new Rectangle2D.Double(214, 129, 47.5, 40);
        assertEquals(expected, converter.getBoundsForCell(new CellPosition(1, 2)));
    }

}
