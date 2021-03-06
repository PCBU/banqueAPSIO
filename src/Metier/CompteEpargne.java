package Metier;


import IHM.DlgMessage;

public class CompteEpargne extends Comptes {
    private double tauxInteret = 0.2;

    /**
     * @roseuid 3D246180033E
     */
    public CompteEpargne(int iCodeCpt) {
        super(iCodeCpt);
    }

    /**
     * @return Double
     * @roseuid 3D24608203E9
     */
    public double calculerInteret() {

        double dResultat = 0, dSolde;
        dSolde = getSolde();
        if (dSolde  > 0) {
            dResultat = tauxInteret * dSolde;
        }
        return dResultat;
    }

    public void debiter(double montant, String description, boolean bAdmin) {

        if (this.getSolde() - montant >= 0) {
            this.debiter(montant, description);
        } else {
            new DlgMessage("Debit impossible");
        }
    }

}
