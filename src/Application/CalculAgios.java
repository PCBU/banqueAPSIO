//Source file: c:\\Mes documents\\Lecomte - Barbieri\\Projet UML-Java\\Application\\CalculAgios.java

package Application;

import Metier.CompteDepot;

public class CalculAgios {
    public CompteDepot theCompteDepot;
    private  double montantAGIO;


    public double getMontantAGIO() {
        return montantAGIO;
    }


    /**
     * @roseuid 3D24617500BE
     */
    public CalculAgios(ListeCompte lc, int iCodeCpt) {

        theCompteDepot = (CompteDepot) lc.getCompte(iCodeCpt);


        if (theCompteDepot.getSolde() < 0) {
           montantAGIO =  theCompteDepot.calculerAgios();
           theCompteDepot.debiter(montantAGIO, "Agios");

        }

    }
}
