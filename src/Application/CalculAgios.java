//Source file: c:\\Mes documents\\Lecomte - Barbieri\\Projet UML-Java\\Application\\CalculAgios.java

package Application;

import Metier.CompteDepot;

public class CalculAgios {
    public CompteDepot theCompteDepot;

    /**
     * @roseuid 3D24617500BE
     */
    public CalculAgios(ListeCompte lc, int iCodeCpt) {
        for (int i = 0; i < lc.size(); i++) {
            if (lc.getCodeCompte(i) == iCodeCpt) {
                theCompteDepot = (CompteDepot) lc.getCompte(i);
            }
        }
        if (theCompteDepot.getSolde() < 0) {
            theCompteDepot.debiter(theCompteDepot.calculerAgios(), "Agios");
        }
    }
}
