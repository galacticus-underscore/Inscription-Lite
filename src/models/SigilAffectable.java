package models;
import java.util.ArrayList;

interface SigilAffectable {
    public ArrayList<SigilEffect> getEffects();
    public void addEffect(SigilEffect s);
}
