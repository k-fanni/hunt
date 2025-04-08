/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hunt.board;

import hunt.player.Player;

/**
 *
 * @author Fanni
 */
public class Field {
    private Player player;

    /**
     * Initializes a Field object. Sets player to null.
     */
    public Field() {
        player = null;
    }
    
    /**
     * Checks if the field has a player standing on it or not.
     * @return true if player is null, false otherwise
     */
    public boolean isFree() {
        return player == null;
    }
    
    /**
     * 
     * @param player the Player object that stepped on the field
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * 
     * @return the Player object standing on the field
     */
    public Player getPlayer() {
        return player;
    }
    
    
}