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

    public CompteDepot(int iCodeCpt, double decouvertAutorise, boolean isExceptional) {
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
    public double getAutorisationDecouvert(){
        return decouvertAutorise;
    }

    public void debiter(double montant, String description, boolean isExceptional) {
        if ((this.getSolde() - montant) >= -(this.getAutorisationDecouvert())) {
            this.debiter(montant, description);
        } else {
            if (isExceptional) {
                this.debiter(montant, description);
            } else {
                new DlgMessage("Debit impossible");
            }
        }
    }

    public void setTauxAgios(double tauxAgios){
        this.tauxAgios = tauxAgios;
    }
    public double getTauxAgios(){
        return this.tauxAgios;
    }
}
