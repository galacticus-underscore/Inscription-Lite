package models.sigils;

import models.App;
import models.Avatar;

import models.patterns.Sigil;

import models.enums.Pointers;

import models.processors.PointerProcessor;

import models.events.AttackEvent;

import models.exceptions.DeadAvatarException;
import models.exceptions.DeadCharacterException;
import models.exceptions.NullSessionException;
import models.exceptions.PointerConversionException;
import models.exceptions.ZeroHealthException;

public class Sniper implements Sigil {
    public void activateSigil() throws NullSessionException, PointerConversionException, ZeroHealthException, DeadAvatarException, DeadCharacterException {
        AttackEvent last_event = (AttackEvent)App.getSession().peekLastEvent();
        Pointers last_target = last_event.getTarget();
        int health_change = last_event.getHealthChange();
        
        if (PointerProcessor.pointerToInt(last_target) != 10) {
            Avatar target = (Avatar)PointerProcessor.pointerToEntity(PointerProcessor.getAvatarOfPointer(last_target));
            target.changeHealth(health_change, false);
        }
    }
}
