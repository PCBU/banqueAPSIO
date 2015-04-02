//Source file: c:\\Mes documents\\Lecomte - Barbieri\\Projet UML-Java\\Application\\CalculInteret.java

package Application;

import Metier.CompteEpargne;

public class CalculInteret {
    public CompteEpargne theCompteEpargne;

    /**
     * @roseuid 3D2461720186
     */
    public CalculInteret(ListeCompte lc, int iCodeCpt) {
        for (int i = 0; i < lc.size(); i++) {
            if (lc.getCodeCompte(i) == iCodeCpt) {
                theCompteEpargne = (CompteEpargne) lc.getCompte(i);
            }
        }
        theCompteEpargne.crediter(theCompteEpargne.calculerInteret(), "Intérêts");
    }
}
