/*
 * SigilEffect.java
 * 
 * This model interface serves as the template for every sigil effect that can
 * be invoked in a game. When a character or avatar attacks or takes damage,
 * their sigil effects arrays will be checked. From there, every sigil effect
 * whose trigger matches the most recent event will be applied. All possible
 * sigil effects will be stored in a static array in Sigil.java as anonymous
 * subclasses of this interface, and the value of a sigil's effect variable
 * will be taken from this array. As in Events.java, we did this to make future
 * development easier, as updates to the game will inevitably add more sigil
 * effects.
 */

package models;

public interface SigilEffect {
    // use details from event history to apply effect
    public void applyEffect();
}
