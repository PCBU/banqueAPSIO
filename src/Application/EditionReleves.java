//Source file: c:\\Mes documents\\Lecomte - Barbieri\\Projet UML-Java\\Application\\EditionReleves.java

package Application;

import Metier.Comptes;

public class EditionReleves {
    public Comptes theComptes;

    /**
     * @roseuid 3D2461780032
     */
    public EditionReleves(Comptes Cpt) {
        theComptes = Cpt;
        Imprimer();
    }

    private void Imprimer() {
        System.out.println(theComptes.getSolde());
    }
}
