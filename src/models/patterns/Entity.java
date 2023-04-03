package models.patterns;

import java.util.ArrayList;

import models.exceptions.DeadAvatarException;
import models.exceptions.DeadCharacterException;
import models.exceptions.NullSessionException;
import models.exceptions.ZeroHealthException;

public interface Entity {
    public void changeHealth(int hp, boolean is_counter) throws ZeroHealthException, NullSessionException, DeadAvatarException, DeadCharacterException;
    public ArrayList<SigilEffect> getEffects();
    public void addEffect(SigilEffect s);
}
