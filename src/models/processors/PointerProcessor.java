package models.processors;

import models.App;
import models.Avatar;
import models.Entity;

import models.enums.Locations;
import models.enums.Pointers;

import models.exceptions.PointerConversionException;

public class PointerProcessor {
    private static Pointers[] entity_pointers = {
        Pointers.PA, Pointers.P1, Pointers.P2, Pointers.P3, Pointers.P4,
        Pointers.OA, Pointers.O1, Pointers.O2, Pointers.O3, Pointers.O4
    };

    
    /** 
     * @param e
     * @return Pointers
     * @throws PointerConversionException
     */
    public static Pointers entityToPointer(Entity e) throws PointerConversionException {
        Pointers out = null;

        for (Pointers pointer : entity_pointers) { 
            if (toEntity(pointer) == e) {
                out = pointer;
                break;
            }
        }

        return out;
    }

    public static int toInt(Pointers pointer) {
        return Character.getNumericValue(pointer.name().charAt(1));
    }

    public static Locations toLocation(Pointers pointer) {
        char playing_side = App.getSession().getPlayingSide();
        Locations out = null;

        if (playing_side == 'h') {
            if (pointer.toString().charAt(0) == 'P')
                out = Locations.valueOf("H" + pointer.toString().charAt(1));
            else
                out = Locations.valueOf("A" + pointer.toString().charAt(1));
        }
        else {
            if (pointer.toString().charAt(0) == 'P')
                out = Locations.valueOf("A" + pointer.toString().charAt(1));
            else
                out = Locations.valueOf("H" + pointer.toString().charAt(1));
        }

        return out;
    }

    public static Entity toEntity(Pointers pointer) throws PointerConversionException {
        switch (pointer) {
            case PA:
                return App.getSession().getPlayingAvatar();
            case P1:
                return App.getSession().getPlayingAvatar().getCharInSlot(0);
            case P2:
                return App.getSession().getPlayingAvatar().getCharInSlot(1);
            case P3:
                return App.getSession().getPlayingAvatar().getCharInSlot(2);
            case P4:
                return App.getSession().getPlayingAvatar().getCharInSlot(3);
            case OA:
                return App.getSession().getObservingAvatar();
            case O1:
                return App.getSession().getObservingAvatar().getCharInSlot(0);
            case O2:
                return App.getSession().getObservingAvatar().getCharInSlot(1);
            case O3:
                return App.getSession().getObservingAvatar().getCharInSlot(2);
            case O4:
                return App.getSession().getObservingAvatar().getCharInSlot(3);
            default:
                throw new PointerConversionException(pointer.name() + " does not point to an entity");
        }
    }

    public static Avatar getAvatar(Pointers pointer) throws PointerConversionException {
        if (pointer.name().charAt(0) == 'P')
            return (Avatar)toEntity(Pointers.PA);
        else
            return (Avatar)toEntity(Pointers.OA);
    }

    public static Pointers getOppositePointer(Pointers pointer) {
        String pointer_name = pointer.name();
        char avatar_code = pointer_name.charAt(0);
        int slot = toInt(pointer);

        if (avatar_code == 'P') {
            Avatar opposite = App.getSession().getObservingAvatar();
            
            if (opposite.getCharInSlot(slot - 1) == null)
                return Pointers.OA;
            else
                return Pointers.valueOf(pointer_name.replace('P', 'O'));
        }
        else {
            Avatar opposite = App.getSession().getPlayingAvatar();

            if (opposite.getCharInSlot(slot - 1) == null)
                return Pointers.PA;
            else
                return Pointers.valueOf(pointer_name.replace('O', 'P'));
        }
    }

    public static Entity getOppositeEntity(Pointers pointer) throws PointerConversionException {
        return toEntity(getOppositePointer(pointer));
    }
}
