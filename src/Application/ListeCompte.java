//Source file: c:\\Mes documents\\Lecomte - Barbieri\\Projet UML-Java\\Application\\ListeCompte.java

package Application;

import Metier.CompteDepot;
import Metier.CompteEpargne;
import Metier.Comptes;

import java.util.Vector;

public class ListeCompte {
    public Vector theComptes;

    /**
     * @roseuid 3D24647900AA
     */
    public ListeCompte() {
        theComptes = new Vector();
        addCompteEpargne(0);
        addCompteEpargne(1);
        addCompteEpargne(2);
        addCompteDepot(3);
        addCompteDepot(4);
    }

    public ListeCompte(Vector<Comptes> comptes) {
        theComptes = new Vector(comptes);
    }

    public void addCompte(int iCodeCpt) {
        theComptes.add(new Comptes(iCodeCpt));
    }

    public void addCompteEpargne(int iCodeCpt) {
        theComptes.add(new CompteEpargne(iCodeCpt));
    }

    public void addCompteDepot(int iCodeCpt) {
        theComptes.add(new CompteDepot(iCodeCpt));
    }

    public void addCompteEpargne(CompteEpargne Cpt) {
        theComptes.add(Cpt);
    }

    public void addCompteDepot(CompteDepot Cpt) {
        theComptes.add(Cpt);
    }

    public String afficher(int iIndice) {
        if (iIndice > -1 && iIndice < theComptes.size()) {
            Comptes c = (Comptes) theComptes.elementAt(iIndice);
            return Integer.toString(c.getCodeCompte());
        } else {
            System.out.println("Erreur d'indice : " + iIndice);
            return "";
        }
    }

    public int getCodeCompte(int iIndice) {
        if (iIndice > -1 && iIndice < theComptes.size()) {
            Comptes c = (Comptes) theComptes.elementAt(iIndice);
            return c.getCodeCompte();
        } else {
            System.out.println("Erreur d'indice : " + iIndice);
            return -1;
        }
    }

    public Comptes getCompte(int iCode) {
        Comptes c = null;
        for (int i = 0; i < size(); i++) {
            if (getCodeCompte(i) == iCode) {
                c = (Comptes) theComptes.elementAt(i);
            }
        }
        if (c == null) {
            System.out.println("Erreur de r�cup�ration du compte : " + iCode);
        }
        return c;
    }

    public boolean supprimerComptes(int iCode) {
        for (int i = 0; i < size(); i++) {
            if (getCodeCompte(i) == iCode) {
                theComptes.remove(i);
            }
        }
        return true;
    }

    public int size() {
        return theComptes.size();
    }

    public int mouvementSize(int iCpt) {
        int iSize = 0;
        for (int i = 0; i < theComptes.size(); i++) {
            Comptes c = (Comptes) theComptes.elementAt(i);
            if (c.getCodeCompte() == iCpt) {
                iSize = c.theMouvements.size();
            }
        }

        return iSize;
    }

    public boolean addMouvement(int iCodeCpt, double dMontant, String sDesc, boolean bAdmin) {
        boolean bOK = true;

        for (int i = 0; i < theComptes.size(); i++) {
            Comptes c = (Comptes) theComptes.elementAt(i);
            if (c.getCodeCompte() == iCodeCpt) {
                if (dMontant < 0) {
                    if (c.getSolde() + dMontant < 0) {
                        if (bAdmin) {
                            c.debiter(dMontant * -1, sDesc + " (D�bit exceptionnel)");
                        } else {
                            bOK = false;
                        }
                    } else {
                        c.debiter(dMontant * -1, sDesc);
                    }
                } else {
                    c.crediter(dMontant, sDesc);
                }
            }
        }
        return bOK;

    }

}
