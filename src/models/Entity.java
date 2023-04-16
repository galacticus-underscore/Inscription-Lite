package models;

import java.util.ArrayList;

import models.exceptions.DeadAvatarException;
import models.exceptions.DeadCharacterException;
import models.exceptions.NullSessionException;
import models.exceptions.PointerConversionException;
import models.exceptions.ZeroHealthException;
import models.patterns.Sigil;

public interface Entity {
    public int getHealth();
    public void changeHealth(int hp, boolean is_attack) throws ZeroHealthException, NullSessionException, DeadAvatarException, DeadCharacterException, PointerConversionException, PointerConversionException;
    public ArrayList<Sigil> getSigils();
    public void addSigil(Sigil a);
    public void counter() throws NullSessionException, DeadAvatarException, DeadCharacterException, PointerConversionException, ZeroHealthException;
}
