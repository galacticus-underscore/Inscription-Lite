package models.processors;

import models.App;
import models.enums.EventPointers;
import models.exceptions.NullSessionException;
import models.patterns.Entity;

public class PointerProcessor {
    public static EventPointers toPointer(int column) {
        switch (column) {
            case 0:
                return EventPointers.P1;
            case 1:
                return EventPointers.P2;
            case 2:
                return EventPointers.P3;
            case 3:
                return EventPointers.P4;
            default:
                return null;
        }
    }

    public static EventPointers toPointer(char avatar) {
        switch (avatar) {
            case 'p':
                return EventPointers.PA;
            case 'o':
                return EventPointers.OA;
            default:
                return null;
        }
    }

    public static EventPointers toPointer(char avatar, int column) {
        switch (avatar) {
            case 'p':
                switch (column) {
                    case 0:
                        return EventPointers.P1;
                    case 1:
                        return EventPointers.P2;
                    case 2:
                        return EventPointers.P3;
                    case 3:
                        return EventPointers.P4;
                    default:
                        return null;
                }
            case 'o':
                switch (column) {
                    case 0:
                        return EventPointers.O1;
                    case 1:
                        return EventPointers.O2;
                    case 2:
                        return EventPointers.O3;
                    case 3:
                        return EventPointers.O4;
                    default:
                        return null;
                }
            default:
                return null;
        }
  
    }

    public static Entity fromPointer(EventPointers pointer) throws NullSessionException {
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
                return null;
        }
    }

    public static EventPointers getOpposite(EventPointers pointer) throws NullSessionException {
        switch (pointer) {
            case P1:
                return fromPointer(pointer) != null ? EventPointers.O1 : EventPointers.OA;
            case P2:
                return fromPointer(pointer) != null ? EventPointers.O2 : EventPointers.OA;
            case P3:
                return fromPointer(pointer) != null ? EventPointers.O3 : EventPointers.OA;
            case P4:
                return fromPointer(pointer) != null ? EventPointers.O4 : EventPointers.OA;
            case O1:
                return fromPointer(pointer) != null ? EventPointers.P1 : EventPointers.PA;
            case O2:
                return fromPointer(pointer) != null ? EventPointers.P2 : EventPointers.PA;
            case O3:
                return fromPointer(pointer) != null ? EventPointers.P3 : EventPointers.PA;
            case O4:
                return fromPointer(pointer) != null ? EventPointers.P4 : EventPointers.PA;
            default:
                return null;
        }
    }
}
