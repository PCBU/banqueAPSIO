//Source file: c:\\Mes documents\\Lecomte - Barbieri\\Projet UML-Java\\Metier\\Mouvements.java

package Metier;

import java.text.SimpleDateFormat;
import java.util.*;

public class Mouvements {
    private Date dateCreation;
    private double montant;
    private String description;
    private int codeMouvement;

    /**
     * @roseuid 3D2461860370
     */
    public Mouvements(double mont, String desc, int codeMouv) {
        Calendar cal = new GregorianCalendar();
        dateCreation = cal.getTime();
        montant = mont;
        description = desc;
        codeMouvement = codeMouv;
    }

    /**
     * @return Void
     * @roseuid 3D24608203FC
     */
    public Void afficher() {
        return null;
    }

    public String getDate() {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S").format(dateCreation);
    }

    public String getCodeMvt() {
        return Integer.toString(codeMouvement);
    }

    public double getMontant() {
        return montant;
    }

    public String getDescription() {
        return description;
    }
}
