//Source file: c:\\Mes documents\\Lecomte - Barbieri\\Projet UML-Java\\Application\\Crediter.java

package Application;

import Metier.Comptes;

public class Crediter {
    public Comptes theComptes;

    /**
     * @roseuid 3D2461670046
     */
    public Crediter(ListeCompte listeCpt, int CodeCpt, double montant, String description) {
        theComptes = listeCpt.getCompte(CodeCpt);
        if (theComptes != null) {
            theComptes.crediter(montant, description);
        } else {
            System.out.println("Crediter::Erreur de récupération du compte : " + CodeCpt);
        }
    }
}
