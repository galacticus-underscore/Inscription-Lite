package models;

import java.util.ArrayList;

import models.enums.Pointers;
import models.enums.SigilCodes;

import models.exceptions.DeadAvatarException;
import models.exceptions.DeadCharacterException;
import models.exceptions.PointerConversionException;
import models.exceptions.ZeroHealthException;

public interface Entity {
    public int getHealth();
    public void changeHealth(int hp, Pointers source) throws ZeroHealthException, DeadAvatarException, DeadCharacterException, PointerConversionException, PointerConversionException;
    public void changeHealth(int hp) throws ZeroHealthException;
    public ArrayList<SigilCodes> getSigils();
    public boolean hasSigil(SigilCodes c);
    public void addSigil(SigilCodes c);
}
