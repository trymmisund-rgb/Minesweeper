package no.uib.inf101.grid;

public record GridCell(boolean hasMine, boolean revealed, boolean flagged, int neighbormines) {

}
