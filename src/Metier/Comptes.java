//Source file: c:\\Mes documents\\Lecomte - Barbieri\\Projet UML-Java\\Metier\\Comptes.java

package Metier;

import Application.ListeCompte;
import IHM.DlgMessage;

import java.util.*;

public class Comptes {
    private Date dateOuverture;
    private double debit = 0.0;
    private double credit = 0.0;
    private int codeCompte;
    public Vector theMouvements = new Vector();

    /**
     * @roseuid 3D246189026C
     */
    public Comptes(int iCodeCpt) {
        Calendar cal = new GregorianCalendar();
        dateOuverture = cal.getTime();
        codeCompte = iCodeCpt;
    }

    /**
     * @param montant
     * @param description
     * @return Boolean
     * @roseuid 3D24608203D6
     */
    public void debiter(double montant, String description) {
        debit = debit + montant;

        int iNbMvt = theMouvements.size();
        Mouvements mvt = new Mouvements(montant * -1, description, iNbMvt);
        theMouvements.add(mvt);
    }

    /**
     * @return Double
     * @roseuid 3D24608203D9
     */
    public double getSolde() {
        double dResultat;
        dResultat = credit - debit;
        return dResultat;
    }

    /**
     * @param montant
     * @param description
     * @return Boolean
     * @roseuid 3D24608203DA
     */
    public void crediter(double montant, String description) {
        credit = credit + montant;

        int iNbMvt = theMouvements.size();
        Mouvements mvt = new Mouvements(montant, description, iNbMvt);
        theMouvements.add(mvt);
    }

    /**
     * @return Integer
     * @roseuid 3D24608203DD
     */
    public int getCodeCompte() {
        return codeCompte;
    }

    /**
     * @param compteCible
     * @param montant
     * @return Boolean
     * @roseuid 3D24608203DE
     */
    public void transferer(Comptes compteCible, double montant) {
        debit = debit + montant;
        compteCible.credit = compteCible.credit + montant;

    }
    public double getAutorisationDecouvert(){
        return 0.0;
    }

    /**
     * @return Void
     * @roseuid 3D24608203E1
     */
    public String afficher() {

        return Double.toString(getSolde());
    }

    public String[][] getMouvements() {
        String[][] sMvt = new String[100][10];
        for (int i = 0; i < theMouvements.size(); i++) {
            Mouvements mvt = (Mouvements) theMouvements.elementAt(i);
            sMvt[i][2] = mvt.getDate();
            sMvt[i][3] = mvt.getCodeMvt();
            double dMvt = mvt.getMontant();
            if (dMvt > 0) {
                sMvt[i][4] = Double.toString(dMvt);
            } else {
                sMvt[i][5] = Double.toString(dMvt);
            }
            sMvt[i][6] = mvt.getDescription();
        }
        return sMvt;
    }

}


