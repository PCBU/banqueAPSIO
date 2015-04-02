//Source file: c:\\Mes documents\\Lecomte - Barbieri\\Projet UML-Java\\Application\\OuvrirCompte.java

package Application;

import Metier.*;


public class OuvrirCompte {
    public Comptes theComptes;
    double dDepot;
    int iCodeCompte;

    /**
     * @roseuid 3D246164006E
     */
    public OuvrirCompte(ListeClient lCli, int iCodeCpt, int iCodeClient, double dMontant) {
        Clients cClients = null;
        for (int i = 0; i < lCli.theClients.size(); i++) {
            int iCodeCli = lCli.getCode(i);
            if (iCodeCli == iCodeClient) {
                cClients = (Clients) lCli.theClients.elementAt(i);
            }
        }

        iCodeCompte = iCodeCpt;
        dDepot = dMontant;
        cClients.addCompteToClient(iCodeCompte);
    }


    public CompteEpargne createCompteEpargne() {
        CompteEpargne ce = new CompteEpargne(iCodeCompte);
        ce.crediter(dDepot, "Dépôt initial");
        return ce;
    }

    public CompteDepot createCompteDepot() {
        CompteDepot cd = new CompteDepot(iCodeCompte);
        cd.crediter(dDepot, "Dépôt initial");
        return cd;
    }
    public CompteDepot createCompteDepot(double montantDecouvert) {
        CompteDepot cd = new CompteDepot(iCodeCompte, montantDecouvert);
        cd.crediter(dDepot, "Dépôt initial");
        return cd;
    }
}
