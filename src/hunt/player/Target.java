/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hunt.player;

import hunt.board.*;
import java.awt.Point;

/**
 *
 * @author Fanni
 */
public class Target extends Player {
    
    
    /**
     * Initializes a Target object.
     * @param board the Board object the Player object belongs to
     * @param position the initial position of the player on the board
     */
    public Target(Board board, Point position) {
        super(board, position);
    }
    
    /**
     * @return true if the player cannot move in any direction, false otherwise
     */
    public boolean Lost() {
        int x = position.x;
        int y = position.y;
        return !board.CanMove(new Point(x, y - 1)) 
            && !board.CanMove(new Point(x - 1, y)) 
            && !board.CanMove(new Point(x, y + 1)) 
            && !board.CanMove(new Point(x + 1, y));
    }
}
