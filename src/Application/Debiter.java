//Source file: c:\\Mes documents\\Lecomte - Barbieri\\Projet UML-Java\\Application\\Debiter.java

package Application;

import IHM.DlgMessage;
import Metier.*;

import javax.swing.*;

public class Debiter {
    public Comptes theComptes;

    /**
     * @roseuid 3D24616903A2
     */
    public Debiter(ListeCompte listeCpt, int CodeCpt, double montant, String description, boolean bAdmin) {
        theComptes = listeCpt.getCompte(CodeCpt);
        if ((theComptes.getSolde() + theComptes.getAutorisationDecouvert()) - montant > 0) {
            theComptes.debiter(montant, description);
        } else {
            if (bAdmin) {
                String sClassName = (theComptes.getClass()).getName();
                if (sClassName.equals("Metier.CompteDepot")) {
                    CompteDepot CD = (CompteDepot) theComptes;
                    CD.debiterExceptionnel(montant, description + " (Débit exceptionnel)");
                }
            }else{
                JOptionPane.showMessageDialog(new JFrame(), "Solde inssufisante","Erreur",JOptionPane.WARNING_MESSAGE);
            }
            else{
                new DlgMessage("Debit impossible");
            }
        }
    }
}
