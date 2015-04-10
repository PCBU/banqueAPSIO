//Source file: c:\\Mes documents\\Lecomte - Barbieri\\Projet UML-Java\\Application\\CalculInteret.java

package Application;

import Metier.CompteEpargne;

public class CalculInteret {
    public CompteEpargne theCompteEpargne;
    private double interet;

    public double getInteret() {
        return interet;
    }



    /**
     * @roseuid 3D2461720186
     */
    public CalculInteret(ListeCompte lc, int iCodeCpt) {

            theCompteEpargne = (CompteEpargne) lc.getCompte(iCodeCpt);

        theCompteEpargne.crediter(theCompteEpargne.calculerInteret(), "Intérêts");
        interet = theCompteEpargne.calculerInteret();
    }
}
