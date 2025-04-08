/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hunt.board;

import hunt.player.*;
import java.awt.Point;

/**
 *
 * @author Fanni
 */
public class Board {
    
    private final Target target;
    private final Hunter[] hunter;
    private final Field[][] fields;
    private Player currentPlayer;
    private int stepsLeft;
    
    
    /**
     * Initializes a Board object.
     * @param n the size of the board (n x n)
     */
    public Board(int n) {
        fields = new Field[n][n];
        hunter = new Hunter[4];
        stepsLeft = 4 * n;
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Field field = new Field();
                fields[i][j] = field;
            }
        }
        
        target = new Target(this, new Point(n / 2, n / 2));
        hunter[0] = new Hunter(this, new Point(0, 0));
        hunter[1] = new Hunter(this, new Point(0, n - 1));
        hunter[2] = new Hunter(this, new Point(n - 1, 0));
        hunter[3] = new Hunter(this, new Point(n - 1, n - 1));
        
        currentPlayer = target;
        
    }

    /**
     * 
     * @return the current player's Player object 
     *         or null if no Hunter has been selected
     */
    public Player CurrentPlayer() {
        return currentPlayer;
    }
    
    /**
     * 
     * @return the current player's name
     */
    public String PlayerName() {
        if (currentPlayer == null) return "Hunter";
        return "Target";
    }
    
    
    /**
     * 
     * @param p a point on the board
     * @return the field at the given point on the board, 
     *         null if the point is not on the board
     */
    public Field get(Point p) {
        if (p == null || p.x < 0 || p.y < 0 
            || p.x == fields.length || p.y == fields.length) {
            return null;
        }
        return fields[p.x][p.y];
    }  
    
    /**
     * Calls the current player's Move method and changes currentPlayer 
     * to the next player. Decreases stepsLeft if the current player was Hunter
     * @param p a point on the board
     */
    public void MovePlayer(Point p) {
        currentPlayer.Move(p);
        if (currentPlayer instanceof Target) {
            currentPlayer = null;
        }
        else {
            stepsLeft--;
            currentPlayer = target;
        }
    }
    
    /**
     * Sets currentPlayer to the Hunter object that has p point as its position
     * @param p a point on the board
     */
    public void SetActiveHunter(Point p) {
        for (int i = 0; i < hunter.length; i++) {
            if (p.equals(hunter[i].Position())) {
                currentPlayer = hunter[i];
            }
        }
    }
    
    /**
     * 
     * @param p the point where the current player wants to move
     * @return true if the field is empty, 
     *         false if the point is not on the board or is not empty
     */
    public boolean CanMove(Point p) {
        return get(p) == null ? false : get(p).isFree();
    }
    
    /**
     * 
     * @return true if target lost or stepsLeft is zero, false otherwise
     */
    public boolean GameOver() {
        return target.Lost() || stepsLeft == 0;
    }
    
    /**
     * 
     * @return the position of the current player on the board
     */
    public Point PlayerPosition() {
        if (currentPlayer == null) return null;
        return currentPlayer.Position();
    }
    
    /**
     * 
     * @return the number of steps the Hunter player has left
     */
    public int getStepsLeft() {
        return stepsLeft;
    }
    
    /**
     * 
     * @return the name of the winning player
     */
    public String getWinner() {
        if (target.Lost()) return "Hunter";
        else return "Target";
    }
}
