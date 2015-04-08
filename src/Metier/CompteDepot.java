//Source file: c:\\Mes documents\\Lecomte - Barbieri\\Projet UML-Java\\Metier\\CompteDepot.java

package Metier;


import IHM.DlgMessage;

public class CompteDepot extends Comptes {
    private double tauxAgios = 0.2;
    private double decouvertAutorise =0 ;

    /**
     * @roseuid 3D246183035C
     */
    public CompteDepot(int iCodeCpt) {
        super(iCodeCpt);
    }
    public CompteDepot(int iCodeCpt, double decouvertAutorise) {
        super(iCodeCpt);
        this.decouvertAutorise = decouvertAutorise;
    }

    /**
     * @return Double
     * @roseuid 3D24608203EF
     */
    public double calculerAgios() {
        double dResultat = 0, dSolde;
        dSolde = getSolde();
        if (dSolde < 0) {
            dResultat = tauxAgios * dSolde * -1;
        }
        return dResultat;
    }

    /**
     * @param montant
     * @param description
     * @return Boolean
     * @roseuid 3D24608203F0
     */
    public Boolean debiterExceptionnel(double montant, String description) {
        debiter(montant, description);
        return true;
    }

    public double getDecouvertAutorise() {
        return this.decouvertAutorise;
    }

    public void debiter(double montant, String description, boolean bAdmin) {
          if ( (this.getSolde() - montant) >= -(this.getDecouvertAutorise())) {
            this.debiter(montant, description);
        } else {
            if (bAdmin) {
                this.debiterExceptionnel(montant, description + " (Débit exceptionnel)");
            }
            else{
                new DlgMessage("Debit impossible");
            }
        }
    }
}
