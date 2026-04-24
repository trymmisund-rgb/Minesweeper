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
  
  private final ControllableMineSweeperModel controllableMineSweeperModel;
  private MineSweeperView view;
  private final Timer timer;
  private final JFrame frame;

  /**
   * Construct a controller recating to key presses.
   * 
   * @param model model to update on key press
   * @param view view to listen to key presses in, and to be repainted when model changes
   */
  public MineSweeperController(ControllableMineSweeperModel model, MineSweeperView view, JFrame frame) {
    this.controllableMineSweeperModel = model;
    this.view = view;
    this.frame = frame;
    view.addMouseListener(this);
    view.addKeyListener(this);
    view.setFocusable(true);
    this.timer = new Timer(1000, e -> {
      controllableMineSweeperModel.incrementTime();
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
        controllableMineSweeperModel.reveal(pos);
    } else if (event.getButton() == MouseEvent.BUTTON3) {
        controllableMineSweeperModel.toggleFlag(pos);
    }
    view.repaint();
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (controllableMineSweeperModel.getGameState() == GameState.START_SCREEN) {
        if (e.getKeyCode() == KeyEvent.VK_1) {
            controllableMineSweeperModel.setDifficulty(Difficulty.EASY);
            view.updateSize();
            frame.pack();
        } else if (e.getKeyCode() == KeyEvent.VK_2) {
            controllableMineSweeperModel.setDifficulty(Difficulty.MEDIUM);
            view.updateSize();
            frame.pack();
        } else if (e.getKeyCode() == KeyEvent.VK_3) {
            controllableMineSweeperModel.setDifficulty(Difficulty.HARD);
            view.updateSize();
            frame.pack();
        }
        view.repaint();
        return;
    }
    if (controllableMineSweeperModel.getGameState() == GameState.GAME_OVER || controllableMineSweeperModel.getGameState() == GameState.GAME_WON) {
      if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          controllableMineSweeperModel.resetGame();
          timer.restart();
          view.repaint();
      }
      if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
        controllableMineSweeperModel.goToStartScreen();
        timer.restart();
        view.repaint();
      }
    }
    if (controllableMineSweeperModel.getGameState() == GameState.ACTIVE || controllableMineSweeperModel.getGameState() == GameState.FIRST_MOVE){
      if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
        controllableMineSweeperModel.goToStartScreen();
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

