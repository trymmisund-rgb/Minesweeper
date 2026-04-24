package no.uib.inf101.grid;

/**
 * Represents a cell in a grid 
 * 
 * This record encapsulates the grid cell data immutably
 * 
 * @param hasMine       true if this cell contains a mine
 * @param revealed      true if this cell has been revealed by the player
 * @param flagged       true if this cell has been flagged by the player
 * @param neighbormines the number of mines in the 8 neighbouring cells 
 */
public record GridCell(boolean hasMine, boolean revealed, boolean flagged, int neighbormines) {

}
