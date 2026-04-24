package no.uib.inf101.main;

import javax.swing.JFrame;

import no.uib.inf101.controller.MineSweeperController;
import no.uib.inf101.model.MineSweeperModel;
import no.uib.inf101.view.MineSweeperView;

/**
 * Main class showing the demo for a clickable grid
 */
public class Main {
  public static void main(String[] args) {
    MineSweeperModel model = new MineSweeperModel();
    MineSweeperView view = new MineSweeperView(model);

    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setTitle("INF101 MineSweeper");
    frame.setContentPane(view);
    new MineSweeperController(model, view, frame);
    frame.pack();
    frame.setVisible(true);
  }
}
