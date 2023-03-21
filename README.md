# LanternaTetris


## Controls
The Controls are safed in ```tetriminoCoordinater```
```java
switch (keyStroke.getCharacter()) {
    case 'd' -> newCoordinates = tetrimino.moveRight();
    case 'a' -> newCoordinates = tetrimino.moveLeft();
    case 'w' -> newCoordinates = tetrimino.rotateRight();
    case 'z' -> newCoordinates = tetrimino.rotateLeft();
    case 'k' -> hardDrop(tetrimino);
    case 's' -> delay = 1;
    default -> delay = 48;
}
```
|Key|Action|
|---|---|
| d | Move Right|
| a | Move Left|
| w | Rotate Right|
| z | Rotate Left|
| s | Soft Drop|
| k | Hard Drop|


## Features
- Right-handed Nintendo Rotation System
- Completly Random Tetrimino Spawns
- Game Over Screen
- Hard Drop
- Soft Drops
- Block Out
- 10x22 Board


## Things to add
- [ ] Good Input Handling
- [ ] Delayed Auto Shift
- [ ] Tetrimino Preview
- [ ] Tetrimino Hold
- [ ] Tetrimino Frequenzy Statistics
- [ ] Points
- [ ] Auto Repeat Rate
- [ ] Line Clear Animations
- [ ] More Tetrimino Randomizer (7-Bag, 14-Bag, GB-Style)
- [ ] Super Rotations System
- [ ] Ghost Tetrimino
- [ ] T-Spins
- [ ] Back to Back
- [ ] Combos
- [ ] Perfect Clear
- [ ] 180° Spins
- [ ] Custom Soft Drop Speed
- [ ] Lock Out
- [ ] Top Out
