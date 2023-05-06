package models.processors;

import models.App;
import models.Avatar;
import models.Entity;
import models.enums.Pointers;

import models.exceptions.PointerConversionException;

public class PointerProcessor {
    private static Pointers[] entity_pointers = {
        Pointers.PA, Pointers.P1, Pointers.P2, Pointers.P3, Pointers.P4,
        Pointers.OA, Pointers.O1, Pointers.O2, Pointers.O3, Pointers.O4
    };

    public static Pointers entityToPointer(Entity e) throws PointerConversionException {
        Pointers out = null;

        for (Pointers pointer : entity_pointers) { 
            if (pointerToEntity(pointer) == e) {
                out = pointer;
                break;
            }
        }

        return out;
    }

    public static int toInt(Pointers pointer) {
        return Character.getNumericValue(pointer.name().charAt(1));
    }

    public static Entity pointerToEntity(Pointers pointer) throws PointerConversionException {
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

    public static Pointers getAvatarOfPointer(Pointers pointer) {
        if (pointer.name().charAt(0) == 'P') {
            return Pointers.PA;
        }
        else {
            return Pointers.OA;
        }
    }

    public static Pointers getOppositePointer(Pointers pointer) throws PointerConversionException {
        String pointer_name = pointer.name();
        char avatar_code = pointer_name.charAt(0);
        int slot = toInt(pointer);

        if (avatar_code == 'P') {
            Avatar opposite = App.getSession().getObservingAvatar();
            
            try {
                opposite.getCharInSlot(slot - 1);
                return Pointers.valueOf(pointer_name.replace('P', 'O'));
            }
            catch (NullPointerException e) {
                return Pointers.OA;
            }
        }
        else {
            Avatar opposite = App.getSession().getPlayingAvatar();

            try {
                opposite.getCharInSlot(slot - 1);
                return Pointers.valueOf(pointer_name.replace('O', 'P'));
            }
            catch (NullPointerException e) {
                return Pointers.PA;
            }
        }
    }

    public static Entity getOppositeEntity(Pointers pointer) throws PointerConversionException {
        return pointerToEntity(getOppositePointer(pointer));
    }
}
