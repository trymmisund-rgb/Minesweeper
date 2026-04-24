package no.uib.inf101.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;
import javax.swing.Timer;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.model.GameState;

/**
 * The view component of the Minesweeper game.
 * Responsible for rendering the board, HUD, start screen, and game over/won overlays.
 * Reads state only through ViewableMineSweeperModel.
 */
public class MineSweeperView extends JPanel {

  private static final int CELL_SIZE = 27;
  private static final int OUTER_MARGIN = 50;
  private static final int INNER_MARGIN = 0;


  private final ViewableMineSweeperModel model;

  /**
     * Constructs a new MineSweeperView.
     * Sets the preferred size based on the current board dimensions and starts a repaint timer.
     *
     * @param model the viewable model to read game state from
     */
  public MineSweeperView(ViewableMineSweeperModel model) {
    this.model = model;
    this.setPreferredSize(new Dimension(400, 400));
    Timer timer = new Timer(1000, e -> repaint());
    timer.start();
  }

  /**
   * Updates the preferred size of the view based on the current board dimensions.
   * Should be called after difficulty changes, followed by frame.pack().
   */
  public void updateSize(){
    int cols = model.getBoard().cols();
    int rows = model.getBoard().rows();
    int width = cols * CELL_SIZE + 2*OUTER_MARGIN;
    int height = rows * CELL_SIZE + 2*OUTER_MARGIN;
    this.setPreferredSize(new Dimension(width, height));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    g2.setColor(new Color(189, 189, 189)); // samme grå som cellene
    g2.fillRect(0, 0, getWidth(), getHeight());
    
    if (model.getGameState() == GameState.START_SCREEN) {
        drawStartScreen(g2);
        return;
    }
    drawBoard(g2);
    drawHUD(g2);
    if (model.getGameState() == GameState.GAME_OVER) {
        drawGameOver(g2);
    } else if (model.getGameState() == GameState.GAME_WON) {
        drawGameWon(g2);
    }
  }

  private void drawBoard(Graphics2D g2) {
    CellPositionToPixelConverter converter = this.getCellPositionToPixelConverter();
    for (CellPosition pos : model.getBoard().positions()) {
      Rectangle2D box = converter.getBoundsForCell(pos);
      GridCell cell = model.getBoard().get(pos);
      
      drawCellBackGround(g2, cell, box);
      drawCellContent(g2, cell, box, pos);
    }
  }
  
  private void drawCellBackGround(Graphics2D g2, GridCell cell, Rectangle2D box) {
    if (cell.revealed()) {
        g2.setColor(new Color(160, 160, 160));
        g2.fill(box);
        g2.setColor(new Color(100, 100, 100));
        g2.draw(box);
    } else {
        int x = (int) box.getX();
        int y = (int) box.getY();
        int w = (int) box.getWidth();
        int h = (int) box.getHeight();

        g2.setColor(new Color(189, 189, 189));
        g2.fillRect(x, y, w, h);

        g2.setColor(new Color(255, 255, 255));
        g2.fillRect(x, y, w - 3, 3);
        g2.fillRect(x, y, 3, h - 3);

        g2.setColor(new Color(80, 80, 80));
        g2.fillRect(x, y + h - 3, w, 3);
        g2.fillRect(x + w - 3, y, 3, h);
    }
  }
  
  private void drawCellContent(Graphics2D g2, GridCell cell, Rectangle2D box, CellPosition pos) {
    if (!cell.revealed() && cell.flagged()){
      drawFlag(g2, box);
      return;
    }
    if (cell.revealed() && cell.hasMine()) {
        if (pos.equals(model.getLastRevealedMine())){
          g2.setColor(Color.RED);
          g2.fill(box);
        }
        drawMine(g2, box);
        return;
    }

    if (cell.revealed() && cell.neighbormines() > 0) {
      g2.setColor(getNumberColor(cell.neighbormines()));
      g2.setFont(g2.getFont().deriveFont(java.awt.Font.BOLD, 14f));
      String text = String.valueOf(cell.neighbormines());
      float x = (float) (box.getCenterX() - g2.getFontMetrics().stringWidth(text) / 2);
      float y = (float) (box.getCenterY() + g2.getFontMetrics().getAscent() / 2);
      g2.drawString(text, x, y);
    }
    
  }
  private void drawFlag(Graphics2D g2, Rectangle2D box) {
    double x = box.getX();
    double y = box.getY();
    double w = box.getWidth();
    double h = box.getHeight();
    
    g2.setColor(Color.BLACK);
    g2.fillRect((int)(x + w * 0.45), (int)(y + h * 0.2),
    (int)(w * 0.1), (int)(h * 0.6));
    
    int[] xPoints = {
      (int)(x + w * 0.5),
      (int)(x + w * 0.8),
      (int)(x + w * 0.5)
    };
    
    int[] yPoints = {
      (int)(y + h * 0.2),
      (int)(y + h * 0.35),
      (int)(y + h * 0.5)
    };
    
    g2.setColor(Color.RED);
    g2.fillPolygon(xPoints, yPoints, 3);
  }
  private void drawMine(Graphics2D g2, Rectangle2D box) {
    int x = (int) box.getX();
    int y = (int) box.getY();
    int w = (int) box.getWidth();
    int h = (int) box.getHeight();
    int cx = x + w / 2;
    int cy = y + h / 2;
    int r = (int) (Math.min(w, h) * 0.3);

    g2.setColor(Color.BLACK);
    g2.setStroke(new java.awt.BasicStroke(1.5f));
    g2.drawLine(cx - r - 2, cy, cx + r + 2, cy); 
    g2.drawLine(cx, cy - r - 2, cx, cy + r + 2); 
    g2.drawLine(cx - r, cy - r, cx + r, cy + r); 
    g2.drawLine(cx + r, cy - r, cx - r, cy + r); 

    
    g2.fillOval(cx - r, cy - r, r * 2, r * 2);

    g2.setColor(Color.WHITE);
    g2.fillOval(cx - r / 3, cy - r / 2, r / 3, r / 3);
    
    g2.setStroke(new java.awt.BasicStroke(1f)); // reset stroke
}
  
  /**
   * Gets an object which converts between CellPosition in a grid and 
   * their pixel positions on the screen.
  */
 public CellPositionToPixelConverter getCellPositionToPixelConverter() {
   Rectangle2D bounds = new Rectangle2D.Double(
     OUTER_MARGIN,
     OUTER_MARGIN,
     this.getWidth() - 2 * OUTER_MARGIN,
     this.getHeight() - 2 * OUTER_MARGIN);
     GridDimension gridSize = this.model.getDimension();
     return new CellPositionToPixelConverter(bounds, gridSize, INNER_MARGIN);
    }
    
    private void drawGameOver(Graphics2D g2){
      g2.setColor(new Color(0, 0, 0, 150));
      g2.fillRect(0, 0, getWidth(), getHeight());

      float fontSize = Math.min(30f, getWidth() / 14f);
      g2.setFont(g2.getFont().deriveFont(java.awt.Font.BOLD, fontSize));
      g2.setColor(Color.RED);
      drawCentered(g2, "Game Over!", (int)(getHeight() / 2f - fontSize));

      g2.setFont(g2.getFont().deriveFont(fontSize * 0.55f));
      drawCentered(g2, "Enter = restart  |  Escape = menu", (int)(getHeight() / 2f + fontSize * 0.3f));
    }

    private void drawGameWon(Graphics2D g2) {
      g2.setColor(new Color(0, 0, 0, 150));
      g2.fillRect(0, 0, getWidth(), getHeight());

      float fontSize = Math.min(30f, getWidth() / 14f);
      g2.setFont(g2.getFont().deriveFont(java.awt.Font.BOLD, fontSize));
      g2.setColor(Color.GREEN);
      drawCentered(g2, "Game Won!", (int)(getHeight() / 2f - fontSize));

      g2.setFont(g2.getFont().deriveFont(fontSize * 0.55f));
      drawCentered(g2, "Enter = restart  |  Escape = meny", (int)(getHeight() / 2f + fontSize * 0.3f));
    }
    

    private void drawStartScreen(Graphics2D g2) {
      g2.setColor(Color.DARK_GRAY);
      g2.fillRect(0, 0, getWidth(), getHeight());
    
      g2.setColor(Color.WHITE);
      g2.setFont(g2.getFont().deriveFont(30f));
      drawCentered(g2, "MineSweeper", getHeight() / 4);
    
      g2.setFont(g2.getFont().deriveFont(18f));
      drawCentered(g2, "Velg vanskelighetsgrad:", getHeight() / 2 - 40);
    
      drawButton(g2, "1 - Enkel", getHeight() / 2);
      drawButton(g2, "2 - Middels", getHeight() / 2 + 40);
      drawButton(g2, "3 - Vanskelig", getHeight() / 2 + 80);
  }

    private void drawButton(Graphics2D g2, String text, int y) {
      g2.setColor(Color.LIGHT_GRAY);
      int w = g2.getFontMetrics().stringWidth(text) + 20;
      g2.fillRoundRect(getWidth() / 2 - w / 2, y - 20, w, 30, 10, 10);
      g2.setColor(Color.BLACK);
      drawCentered(g2, text, y);
  }

    private void drawCentered(Graphics2D g2, String text, int y) {
      float x = (float)(getWidth() / 2 - g2.getFontMetrics().stringWidth(text) / 2);
      g2.drawString(text, x, y);
  }

    private void drawHUD(Graphics2D g2) {
      g2.setColor(Color.BLACK);
      g2.setFont(g2.getFont().deriveFont(16f));
      g2.drawString("🚩 " + model.getFlagsLeft(), 20, 15);
      g2.drawString("⏱ " + model.getElapsedSeconds() + "s", getWidth() - 80, 15);
  }

    private Color getNumberColor(int number){
      switch(number){
        case 1:
          return Color.BLUE;
        case 2:
          return new Color(0, 128, 0);
        case 3:
          return Color.RED;
        case 4:
          return new Color(128, 0, 128);
        case 5:
          return new Color(128, 0, 0);
        case 6:
          return new Color(0, 128, 128); 
        case 7:
          return Color.GRAY;
        default:
          return Color.BLACK;
      }
  }
}
  