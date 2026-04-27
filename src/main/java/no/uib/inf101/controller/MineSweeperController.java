package no.uib.inf101.controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import javax.swing.Timer;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.model.Difficulty;
import no.uib.inf101.model.GameState;
import no.uib.inf101.view.CellPositionToPixelConverter;
import no.uib.inf101.view.MineSweeperView;

/**
 * The controller component of the Minesweeper game.
 * Listens to mouse clicks and key presses, translates them into model actions,
 * and triggers repaints of the view.
 */

public class MineSweeperController extends MouseAdapter implements java.awt.event.KeyListener{
  
  private final ControllableMineSweeperModel model;
  private final MineSweeperView view;
  private final Timer timer;
  private final JFrame frame;

  /**
   * Construct a controller recating to key presses and mouse clicks.
   * 
   * @param model model to update on key press
   * @param view view to listen to key presses in, and to be repainted when model changes
   * @param frame the JFrame, used to resize the window when difficulty changes
   */
  public MineSweeperController(ControllableMineSweeperModel model, MineSweeperView view, JFrame frame) {
    this.model = model;
    this.view = view;
    this.frame = frame;
    view.addMouseListener(this);
    view.addKeyListener(this);
    view.setFocusable(true);
    this.timer = new Timer(1000, e -> {
      model.incrementTime();
      this.view.repaint();
    });
    timer.start();
  }

  @Override
  public void mousePressed(MouseEvent event) {
    Point2D mouseCoordinate = event.getPoint();
    CellPositionToPixelConverter converter = this.view.getCellPositionToPixelConverter();
    CellPosition pos = converter.getCellPositionOfPoint(mouseCoordinate);
    if (pos == null) return;
    if (event.getButton() == MouseEvent.BUTTON1) {
        model.reveal(pos);
    } else if (event.getButton() == MouseEvent.BUTTON3) {
        model.toggleFlag(pos);
    }
    view.repaint();
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (model.getGameState() == GameState.START_SCREEN) {
        if (e.getKeyCode() == KeyEvent.VK_1) {
            model.setDifficulty(Difficulty.EASY);
            view.updateSize();
            frame.pack();
        } else if (e.getKeyCode() == KeyEvent.VK_2) {
            model.setDifficulty(Difficulty.MEDIUM);
            view.updateSize();
            frame.pack();
        } else if (e.getKeyCode() == KeyEvent.VK_3) {
            model.setDifficulty(Difficulty.HARD);
            view.updateSize();
            frame.pack();
        }
        view.repaint();
        return;
    }
    if (model.getGameState() == GameState.GAME_OVER || model.getGameState() == GameState.GAME_WON) {
      if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          model.resetGame();
          timer.restart();
          view.repaint();
      }
      if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
        model.goToStartScreen();
        timer.restart();
        view.repaint();
      }
    }
    if (model.getGameState() == GameState.ACTIVE || model.getGameState() == GameState.FIRST_MOVE){
      if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
        model.goToStartScreen();
        timer.restart();
        view.repaint();
      }
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    
  }

  @Override
  public void keyTyped(KeyEvent e) {
    
  }

}

