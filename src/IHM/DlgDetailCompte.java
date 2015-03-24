//Source file: c:\\Mes documents\\Lecomte - Barbieri\\Projet UML-Java\\IHM\\DlgDetailCompte.java

package IHM;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Application.*;

public class DlgDetailCompte extends JFrame {
    DlgMain dlgMain;
    JButton bFermer;
    AdaptateurBoutons unAdaptateurBoutons;

    public ConsulterCompte theConsulterCompte;

    public DlgDetailCompte(DlgMain dlg) {
        dlgMain = dlg;

        DetailCompte dc = new DetailCompte(dlgMain.listeCompte);
        JTable table = dc.getTable();
        JScrollPane scrollpane = new JScrollPane(table);

        bFermer = new JButton("Fermer");

        getContentPane().add("Center", scrollpane);
        getContentPane().add("South", bFermer);

        addWindowListener((WindowListener) new AdapFenetre());
        unAdaptateurBoutons = new AdaptateurBoutons();
        bFermer.addActionListener(unAdaptateurBoutons);

        setTitle("Détail des comptes");

        pack();
        show();
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
