package no.uib.inf101.model;

import java.util.ArrayList;
import java.util.List;

import no.uib.inf101.controller.ControllableMineSweeperModel;
import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.model.mines.MineFactory;
import no.uib.inf101.model.mines.RandomMineFactory;
import no.uib.inf101.view.ViewableMineSweeperModel;

/**
 * The main model for the Minesweeper game.
 * Implements all game logic including mine placement, cell revealing,
 * flagging, win/loss detection, and timer tracking.
 * Implements both ControllableMineSweeperModel and ViewableMineSweeperModel.
 */

public class MineSweeperModel implements ControllableMineSweeperModel, ViewableMineSweeperModel{

    private MineSweeperBoard board;
    private GameState gameState;
    private Difficulty difficulty;
    private int flagsLeft;
    private int elapsedSeconds;
    private final MineFactory mineFactory;
    private CellPosition lastRevealedMine = null;
   
    /**
     * Constructs a new MineSweeperModel with random mine placement.
     * The initial game state is START_SCREEN.
     */
    public MineSweeperModel(){
        this(new RandomMineFactory());
    }

    /**
     * Constructs a new MineSweeperModel with the given MineFactory.
     * Useful for testing with a FixedMineFactory to control mine placement.
     *
     * @param mineFactory the factory used to generate mine positions
     */
    public MineSweeperModel(MineFactory mineFactory) {
        this.mineFactory = mineFactory;
        this.difficulty = Difficulty.EASY;
        this.gameState = GameState.START_SCREEN;
        this.board = createBoard(difficulty);
        this.flagsLeft = board.getMines();
    }    

    @Override
    public void reveal(CellPosition pos) {
        if (pos == null || !board.positionIsOnGrid(pos))
            return;
        
        if (gameState == GameState.GAME_OVER || gameState == GameState.GAME_WON || gameState == GameState.START_SCREEN) {
            return;
        }
        if (!isValidClick(pos)) return;
        
        if (gameState == GameState.FIRST_MOVE){
            startGame(pos);
        }
        
        GridCell cell = board.get(pos);
        if(ignoredCells(cell)) return;
        
        revealCell(pos, cell);
        if (cell.hasMine()){
            lastRevealedMine = pos;
            board.revealAllMines();
            gameState = GameState.GAME_OVER;
            return;
        }
        if (cell.neighbormines() == 0){
            revealNeighbors(pos);
        }
        if (board.allSafeCellsRevealed()){
            gameState = GameState.GAME_WON;
        }
        
    }
    
    @Override
    public void toggleFlag(CellPosition pos) {
        if (gameState == GameState.GAME_OVER || gameState == GameState.GAME_WON) {
            return;
        }
        if (pos == null || !board.positionIsOnGrid(pos)) 
            return;
        GridCell cell = board.get(pos);
        if (!cell.revealed()) {
            if (!cell.flagged() && flagsLeft == 0) 
                return;
            flagsLeft += cell.flagged() ? 1 : -1;
            board.set(pos, new GridCell(cell.hasMine(), false, !cell.flagged(), cell.neighbormines()));
        }
    }
    
    @Override
    public void startGame(CellPosition pos) {
        if (pos == null || !board.positionIsOnGrid(pos))
            throw new IllegalArgumentException();
        placeMines(pos);
        calculateNeighborMines();
        gameState = GameState.ACTIVE;
    }

    @Override
    public void resetGame(){
        this.board = createBoard(difficulty);
        this.flagsLeft = board.getMines();
        this.elapsedSeconds = 0;
        gameState = GameState.FIRST_MOVE;
    }
    
    @Override
    public void setDifficulty(Difficulty difficulty) {
        if (difficulty == null)
            throw new IllegalArgumentException();
        this.difficulty = difficulty;
        this.board = createBoard(difficulty);
        this.flagsLeft = board.getMines();
        this.elapsedSeconds = 0;
        this.gameState = GameState.FIRST_MOVE;
    }
    
    
    @Override
    public void goToStartScreen() {
        gameState = GameState.START_SCREEN;
    }
    
    @Override
    public void incrementTime() {
        if (gameState == GameState.ACTIVE && elapsedSeconds < 999) {
            elapsedSeconds++;
        }
    }

    @Override
    public int getFlagsLeft(){
        return flagsLeft;
    }

    @Override
    public MineSweeperBoard getBoard() {
        return board;
    }
    
    @Override
    public GameState getGameState(){
        return gameState;
    }
    
    @Override
    public int getElapsedSeconds() {
        return elapsedSeconds;
    }
    
    
    @Override
    public CellPosition getLastRevealedMine() {
        return lastRevealedMine;
    }
    
    private void calculateNeighborMines() {
        for(CellPosition pos : board.positions()){
            int count = 0;
            for (CellPosition neighbor : board.getNeighbors(pos)) {
                if (board.get(neighbor).hasMine()) {
                    count++;
                }
            }
            GridCell cell = board.get(pos);
            board.set(pos, new GridCell(cell.hasMine(), false, false, count)); 
        }
    }

    private void placeMines(CellPosition firstClick) {
        List<CellPosition> excludedCellPositions = new ArrayList<>();
        excludedCellPositions.add(firstClick);
        excludedCellPositions.addAll(board.getNeighbors(firstClick));
        
        List<CellPosition> mines = mineFactory.getMinePositions(board, board.getMines(), excludedCellPositions);
        for (CellPosition pos : mines) {
            board.set(pos, new GridCell(true, false, false, 0));
        }
    }

    private void revealCell(CellPosition pos, GridCell cell){
        board.set(pos, new GridCell(cell.hasMine(), true, false, cell.neighbormines()));
    }
    
    private void revealNeighbors(CellPosition pos){
        for (CellPosition neighbor : board.getNeighbors(pos)) {
            reveal(neighbor);
        }
    }
    
    private boolean ignoredCells(GridCell cell){
        return cell.flagged() || cell.revealed();
    }
    
    private boolean isValidClick(CellPosition pos){
        return pos != null && board.positionIsOnGrid(pos);
    }
    
    private MineSweeperBoard createBoard(Difficulty difficulty) {
        switch (difficulty){
            case EASY:
                return new MineSweeperBoard(8, 10, 10);
            case MEDIUM:
                return new MineSweeperBoard(14, 18, 40);
            case HARD:
                return new MineSweeperBoard(20, 24, 99);
            default:
                throw new IllegalArgumentException("Unknown difficulty");
        }
        
    }
    
    
    
}