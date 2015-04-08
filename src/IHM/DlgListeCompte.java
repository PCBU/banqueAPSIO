//Source file: c:\\Mes documents\\Lecomte - Barbieri\\Projet UML-Java\\IHM\\DlgListeCompte.java

package IHM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DlgListeCompte extends JFrame {
    DlgMain dlgMain;

    public DlgCrediter theDlgCrediter;
    public DlgCreateCompte theDlgCreateCompte;
    public DlgDebiter theDlgDebiter;
    public DlgTransferer theDlgTransferer;
    public DlgOperationsCompte theDlgOperations;

    AdaptateurBoutons unAdaptateurBoutons;
    JButton bCrediter;
    JButton bDebiter;
    JButton bNouveau;
    JButton bOperations;
    JButton bSupprimer;
    JButton bFermer;
    JButton bTransferer;
    JButton bDebitExceptionnel;
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
        bCrediter = new JButton("Créditer");
        pBouton.add(bCrediter);
        bDebiter = new JButton("Débiter");
        pBouton.add(bDebiter);
        bTransferer = new JButton("Transférer");
        pBouton.add(bTransferer);
        bNouveau = new JButton("Nouveau compte");
        pBouton.add(bNouveau);
        bOperations = new JButton("Détails du compte");
        pBouton.add(bOperations);
        bSupprimer = new JButton("Fermer compte");
        pBouton.add(bSupprimer);
        bFermer = new JButton("Fermer");
        pBouton.add(bFermer);
        bDebitExceptionnel = new JButton("Débit exceptionnel");
        if (dlgMain.bAdministrateur) {
            pBouton.add(bDebitExceptionnel);
        }

        unAdaptateurBoutons = new AdaptateurBoutons();
        bCrediter.addActionListener(unAdaptateurBoutons);
        bDebiter.addActionListener(unAdaptateurBoutons);
        bNouveau.addActionListener(unAdaptateurBoutons);
        bOperations.addActionListener(unAdaptateurBoutons);
        bFermer.addActionListener(unAdaptateurBoutons);
        bSupprimer.addActionListener(unAdaptateurBoutons);
        bTransferer.addActionListener(unAdaptateurBoutons);
        bDebitExceptionnel.addActionListener(unAdaptateurBoutons);
        addWindowListener(new AdapFenetre());

        getContentPane().add("Center", listCompte);
        getContentPane().add("South", pBouton);
        setTitle("Liste des comptes");

        pack();
        setVisible(true);
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

            if (e.getSource() == bNouveau) {
                int iNextCode = dlgMain.listeCompte.size();
                if (iNextCode != 0) {
                    iNextCode = dlgMain.listeCompte.getCodeCompte(dlgMain.listeCompte.size() - 1) + 1;
                }
                theDlgCreateCompte = new DlgCreateCompte(dlgMain, -1, iNextCode);
                theDlgCreateCompte.addWindowListener(new AdapFenetreCreate());
            } else if (e.getSource() == bDebiter || e.getSource() == bDebitExceptionnel) {
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
            } else if (e.getSource() == bOperations) {
                theDlgOperations = new DlgOperationsCompte(dlgMain, listCompte.getSelectedIndex());
            }
        }
    }

    class AdapFenetreCreate extends WindowAdapter {
        public void windowDeactivated(WindowEvent e) {
            ReloadListe();
        }
    }


    class AdapFenetre extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            setVisible(false);
        }
    }

}

