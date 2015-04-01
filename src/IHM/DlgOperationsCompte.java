//Source file: c:\\Mes documents\\Lecomte - Barbieri\\Projet UML-Java\\IHM\\DlgDetailCompte.java

package IHM;

import Application.ConsulterCompte;
import Application.DetailCompte;
import Application.OperationsCompte;
import Metier.Comptes;

import javax.swing.*;
import java.awt.event.*;

public class DlgOperationsCompte extends JFrame {
    DlgMain dlgMain;
    JButton bFermer;
    AdaptateurBoutons unAdaptateurBoutons;

    public ConsulterCompte theConsulterCompte;

    public DlgOperationsCompte(DlgMain dlg, int codeCpt) {
        dlgMain = dlg;

        OperationsCompte oc = new OperationsCompte(dlgMain.listeCompte, codeCpt);
        JTable table = oc.getTable();
        JScrollPane scrollpane = new JScrollPane(table);

        bFermer = new JButton("Fermer");

        getContentPane().add("Center", scrollpane);
        getContentPane().add("South", bFermer);

        addWindowListener(new AdapFenetre());
        unAdaptateurBoutons = new AdaptateurBoutons();
        bFermer.addActionListener(unAdaptateurBoutons);

        setTitle("Opérations du compte");

        pack();
        setVisible(true);
    }

    class AdaptateurBoutons implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == bFermer) {
                setVisible(false);
            }
        }
    }//fin de AdaptateurBoutons

    class AdapFenetre extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            setVisible(false);
        }
    }
}
