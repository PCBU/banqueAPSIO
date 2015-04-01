//Source file: c:\\Mes documents\\Lecomte - Barbieri\\Projet UML-Java\\Application\\DetailCompte.java

package Application;

import Metier.Comptes;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class OperationsCompte {
    public Vector theComptes;
    public JTable table;

    /**
     * @roseuid 3D24647900AA
     */
    public OperationsCompte(ListeCompte listeCompte, int codeCpt) {
        String sMvt[][] = new String[100][100];
        String nomColonnes[] = {"Compte", "Solde", "Date mouvement", "Mouvement", "Crédit", "Débit", "Description"};
        int k = 0;

        sMvt[k][0] = Integer.toString(listeCompte.getCodeCompte(codeCpt));
        Comptes c = (Comptes) listeCompte.theComptes.elementAt(codeCpt);
        sMvt[k][1] = Double.toString(c.getSolde());

        String[][] sMouvement;
        sMouvement = c.getMouvements();

        k++;
        for (int j = 0; j < c.theMouvements.size(); j++) {
            sMvt[k][2] = sMouvement[j][2];
            sMvt[k][3] = sMouvement[j][3];
            sMvt[k][4] = sMouvement[j][4];
            sMvt[k][5] = sMouvement[j][5];
            sMvt[k][6] = sMouvement[j][6];
            k++;
        }
        k++;

        table = new JTable(sMvt, nomColonnes);
        table.setPreferredScrollableViewportSize(new Dimension(750, 400));
    }

    public JTable getTable() {
        return table;
    }

}
