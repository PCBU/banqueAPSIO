package IHM;

import Metier.CompteDepot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Dikyx on 10/04/2015.
 */
public class DlgSetAgios extends JFrame {
    DlgMain dlgMain;
    AdaptateurBoutons unAdaptateurBoutons;
    JButton bSetAgios;
    List listCompteDepot;
    int iCodeClient;

    public DlgSetAgios(DlgMain dlg) {
        dlgMain = dlg;

        listCompteDepot = new List();
        ReloadListeCompteDepot();

        Panel pBouton = new Panel();
        FlowLayout flBouton = new FlowLayout();

        bSetAgios = new JButton("Modifier taux Agios");
        pBouton.add(bSetAgios);

        unAdaptateurBoutons = new AdaptateurBoutons();
        bSetAgios.addActionListener(unAdaptateurBoutons);

        addWindowListener(new AdapFenetre());

        getContentPane().add("Center", listCompteDepot);
        getContentPane().add("South", pBouton);
        setTitle("Liste des comptes");

        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    class AdaptateurBoutons implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == bSetAgios) {

            }
        }
    }

    public void ReloadListeCompteDepot() {

        if (listCompteDepot.getItemCount() > 0) {
            listCompteDepot.removeAll();
        }

        for (int i = 0; i < dlgMain.listeCompte.size(); i++) {
            if (dlgMain.listeCompte.getCompte(i) instanceof CompteDepot) {
                listCompteDepot.add(dlgMain.listeCompte.afficher(i));
            }
        }
    }

    class AdapFenetreCreate extends WindowAdapter {
        public void windowDeactivated(WindowEvent e) {
            ReloadListeCompteDepot();
        }
    }


    class AdapFenetre extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            setVisible(false);
        }
    }
}
