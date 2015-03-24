//Source file: c:\\Mes documents\\Lecomte - Barbieri\\Projet UML-Java\\Application\\EditionAvertissements.java

package Application;

import Metier.CompteDepot;

public class EditionAvertissements {
    public CompteDepot theCompteDepot;

    public EditionAvertissements(CompteDepot Cpt) {
        theCompteDepot = Cpt;
        Imprimer();
    }

    private void Imprimer() {
        if (theCompteDepot.getSolde() < 0) {
            System.out.println(theCompteDepot.getSolde());
        }
    }
}
