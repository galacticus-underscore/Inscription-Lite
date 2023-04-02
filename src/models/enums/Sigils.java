/*
 * Sigils.java
 * 
 * This model enum lists down all the sigils that exist in the game by name.
 * The main purpose of this enum is to allow the controllers to easily replay
 * events caused by sigil effects triggering using a switch-case block.
 */

package models.enums;

public enum Sigils {
    DRAW,
    SUMMON_CHAR,
    SUMMON_SIGIL_CHAR,
    SUMMON_SIGIL_AVATAR,
    ATTACK,
    SIGIL_EFFECT,
    CHAR_DEATH,
    SACRIFICE,
    AVATAR_DEATH;
}
