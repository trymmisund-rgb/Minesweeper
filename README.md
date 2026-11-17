Minesweeper

Minesweeper in Java/Swing, built as a semester assignment in INF101 Object-Oriented Programming at the University of Bergen (spring 2026).

Features: three difficulty levels, safe first click, flood-fill reveal, flagging, timer and win/loss detection.

Controls: left click to reveal, right click to flag, 1/2/3 to choose difficulty, Enter to restart, Escape for the menu.

Architecture: Model-View-Controller, with separate interfaces for what the view and controller can access. Mine placement goes through a MineFactory interface, so the game logic can be tested deterministically with JUnit 5 (mvn test).

Run: Java 17 + Maven. Run no.uib.inf101.main.Main.
