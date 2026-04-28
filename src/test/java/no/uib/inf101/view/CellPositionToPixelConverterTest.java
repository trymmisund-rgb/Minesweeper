package no.uib.inf101.view;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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

    @Test
    public void testGetCellPositionOfPointCenter() {
        CellPositionToPixelConverter converter = makeConverter(4, 5);
        CellPosition original = new CellPosition(1, 2);
        Rectangle2D bounds = converter.getBoundsForCell(original);
        Point2D center = new Point2D.Double(bounds.getCenterX(), bounds.getCenterY());
        assertEquals(original, converter.getCellPositionOfPoint(center));
    }

    @Test
    public void testGetCellPositionOfPointOutsideReturnsNull() {
        CellPositionToPixelConverter converter = makeConverter(4, 5);
        assertNull(converter.getCellPositionOfPoint(new Point2D.Double(-10, -10)));
    }

    @Test
    public void testRoundTripAllCells() {
        CellPositionToPixelConverter converter = makeConverter(5, 8);
        GridDimension gd = new Grid(5, 8);
        for (CellPosition original : gd.positions()) {
            Rectangle2D bounds = converter.getBoundsForCell(original);
            Point2D center = new Point2D.Double(bounds.getCenterX(), bounds.getCenterY());
            assertEquals(original, converter.getCellPositionOfPoint(center));
        }
    }
}
