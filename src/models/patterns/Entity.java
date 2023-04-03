package models.patterns;

import java.util.ArrayList;

import models.exceptions.NullSessionException;
import models.exceptions.ZeroHealthException;

public interface Entity {
    public void changeHealth(int hp) throws ZeroHealthException, NullSessionException;
    public ArrayList<SigilEffect> getEffects();
    public void addEffect(SigilEffect s);
}
