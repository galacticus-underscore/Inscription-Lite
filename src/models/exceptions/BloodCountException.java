/**
*
* @throws BloodCountException < is thrown when a player's blood count is lower than the summoning cost of the card being played.> 
*
**/ 

package models.exceptions;

public class BloodCountException extends Exception {
    public BloodCountException() {
        super("You do not have enough blood to summon this card!");
    }
}
