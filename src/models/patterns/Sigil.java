package models.patterns;

import models.exceptions.DeadAvatarException;
import models.exceptions.DeadCharacterException;
import models.exceptions.NullSessionException;
import models.exceptions.PointerConversionException;
import models.exceptions.ZeroHealthException;

public interface Sigil {
    public void activateSigil() throws NullSessionException, PointerConversionException, ZeroHealthException, DeadAvatarException, DeadCharacterException;
}
