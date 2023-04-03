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

package models.patterns;

import models.enums.EventTypes;
import models.enums.Sigils;

public interface SigilEffect {
    public Sigils getEffectType();
    public boolean applyEffect(EventTypes event);
}

/*
 * More notes to self:
 * - Use details from event history to apply effect
 * - When applying an effect, create the sigil effect event in the apply
 *   effects method after the effect is applied
 * - No sigil effect event will be created if the sigil does not respond to the
 *   last event type in the event history array
 */
