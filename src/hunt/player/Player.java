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
public abstract class Player {
    
    protected Board board;
    protected Point position;

    
    public Player() {}
    
    protected Player(Board board, Point position) {
        this.board = board;
        setPosition(position);
    }

    /**
     * 
     * @return the Board object the Player object belongs to
     */
    public Board getBoard() {
        return board;
    }
    
    /**
     * Moves the player to the given point on the board. 
     * Sets the player of the field in the previous position to null.
     * @param position a point on the board
     */
    public void Move(Point position) {
        board.get(this.position).setPlayer(null);
        setPosition(position);
    }
    
    protected void setPosition(Point position) {
        this.position = position;
        board.get(position).setPlayer(this);
    }
    
    /**
     * 
     * @return the current position of the player on the board
     */
    public Point Position() {
        return position;
    }
    
    
}
