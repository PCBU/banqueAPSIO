//Source file: c:\\Mes documents\\Lecomte - Barbieri\\Projet UML-Java\\IHM\\DlgListeCompte.java

package IHM;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Application.*;

public class DlgListeCompte extends JFrame {
    DlgMain dlgMain;

    public DlgDetailCompte theDlgDetailCompte;
    public DlgCrediter theDlgCrediter;
    public DlgCreateCompte theDlgCreateCompte;
    public DlgDebiter theDlgDebiter;
    public DlgTransferer theDlgTransferer;

    AdaptateurBoutons unAdaptateurBoutons;
    JButton bCrediter;
    JButton bDebiter;
    JButton bNouveau;
    JButton bDetails;
    JButton bSupprimer;
    JButton bFermer;
    JButton bTransferer;
    List listCompte;
    int iCodeClient;

    public DlgListeCompte(DlgMain dlg, int iCClient) {
        dlgMain = dlg;
        iCodeClient = iCClient;

        listCompte = new List(5, false);

        ReloadListe();

        Panel pBouton = new Panel();
        FlowLayout flBouton = new FlowLayout();
        pBouton.setLayout(flBouton);
        bCrediter = new JButton("Cr�diter");
        pBouton.add(bCrediter);
        bDebiter = new JButton("D�biter");
        pBouton.add(bDebiter);
        bTransferer = new JButton("Transf�rer");
        pBouton.add(bTransferer);
        bNouveau = new JButton("Nouveau compte");
        pBouton.add(bNouveau);
        bDetails = new JButton("D�tails des comptes");
        pBouton.add(bDetails);
        bSupprimer = new JButton("Fermer compte");
        pBouton.add(bSupprimer);
        bFermer = new JButton("Fermer");
        pBouton.add(bFermer);

        unAdaptateurBoutons = new AdaptateurBoutons();
        bCrediter.addActionListener(unAdaptateurBoutons);
        bDebiter.addActionListener(unAdaptateurBoutons);
        bNouveau.addActionListener(unAdaptateurBoutons);
        bDetails.addActionListener(unAdaptateurBoutons);
        bFermer.addActionListener(unAdaptateurBoutons);
        bSupprimer.addActionListener(unAdaptateurBoutons);
        bTransferer.addActionListener(unAdaptateurBoutons);
        addWindowListener((WindowListener) new AdapFenetre());

        getContentPane().add("Center", listCompte);
        getContentPane().add("South", pBouton);
        setTitle("Liste des comptes");

        pack();
        show();
    }

    public void ReloadListe() {

        if (listCompte.getItemCount() > 0) {
            listCompte.removeAll();
        }


        if (iCodeClient == -1) {
            for (int i = 0; i < dlgMain.listeCompte.size(); i++) {
                listCompte.add(dlgMain.listeCompte.afficher(i));
            }
        } else {
            for (int i = 0; i < dlgMain.listeClient.size(); i++) {
                if (dlgMain.listeClient.getCode(i) == iCodeClient) {
                    for (int j = 0; j < dlgMain.listeClient.nbCptForClient(iCodeClient); j++) {
                        listCompte.add(Integer.toString(dlgMain.listeClient.cptClient(iCodeClient, j)));
                    }
                }
            }
        }

        if (dlgMain.listeCompte.size() > 0) {
            listCompte.select(0);
        }

    }

    class AdaptateurBoutons implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == bDetails) {
                theDlgDetailCompte = new DlgDetailCompte(dlgMain);
            } else if (e.getSource() == bNouveau) {
                int iNextCode = dlgMain.listeCompte.size();
                if (iNextCode != 0) {
                    iNextCode = dlgMain.listeCompte.getCodeCompte(dlgMain.listeCompte.size() - 1) + 1;
                }
                theDlgCreateCompte = new DlgCreateCompte(dlgMain, -1, iNextCode);
                theDlgCreateCompte.addWindowListener((WindowListener) new AdapFenetreCreate());
            } else if (e.getSource() == bDebiter) {
                theDlgDebiter = new DlgDebiter(dlgMain, listCompte.getSelectedIndex());
            } else if (e.getSource() == bTransferer) {
                theDlgTransferer = new DlgTransferer(dlgMain, null);
            } else if (e.getSource() == bFermer) {
                setVisible(false);
            } else if (e.getSource() == bCrediter) {
                theDlgCrediter = new DlgCrediter(dlgMain, listCompte.getSelectedIndex());
            } else if (e.getSource() == bSupprimer) {
                dlgMain.listeCompte.supprimerComptes(Integer.parseInt(listCompte.getSelectedItem()));
                ReloadListe();
                dlgMain.listeClient.removeCompteFromClient(Integer.parseInt(listCompte.getSelectedItem()));
            }
        }
    }//fin de AdaptateurBoutons

    class AdapFenetreCreate extends WindowAdapter {
        public void windowDeactivated(WindowEvent e) {
            ReloadListe();
        }
    }//fin de AdapFenetreCreate


    class AdapFenetre extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            setVisible(false);
        }
    }

}

