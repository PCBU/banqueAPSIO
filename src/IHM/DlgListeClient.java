//Source file: c:\\Mes documents\\Lecomte - Barbieri\\Projet UML-Java\\IHM\\DlgListeClient.java

package IHM;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DlgListeClient extends JFrame {
    DlgMain dlgMain;
    public DlgCreateClient theDlgCreateClient;

    AdaptateurBoutons unAdaptateurBoutons;
    JButton bNouvCpt;
    JButton bNouveau;
    JButton bSupprimer;
    JButton bFermer;
    JButton bTransferer;
    JButton bListeCpt;
    List listClient;

    /**
     * @roseuid 3D246158023A
     */
    public DlgListeClient(DlgMain dlg) {
        dlgMain = dlg;

        listClient = new List(5, false);

        ReloadListe();

        Panel pBouton = new Panel();
        FlowLayout flBouton = new FlowLayout();
        pBouton.setLayout(flBouton);
        bNouveau = new JButton("Nouveau client");
        pBouton.add(bNouveau);
        bSupprimer = new JButton("Supprimer client");
        pBouton.add(bSupprimer);
        bNouvCpt = new JButton("Nouveau compte");
        pBouton.add(bNouvCpt);
        bListeCpt = new JButton("Lister comptes");
        pBouton.add(bListeCpt);
        bTransferer = new JButton("Transférer");
        pBouton.add(bTransferer);
        bFermer = new JButton("Fermer");
        pBouton.add(bFermer);

        unAdaptateurBoutons = new AdaptateurBoutons();
        bNouveau.addActionListener(unAdaptateurBoutons);
        bSupprimer.addActionListener(unAdaptateurBoutons);
        bFermer.addActionListener(unAdaptateurBoutons);
        bNouvCpt.addActionListener(unAdaptateurBoutons);
        bListeCpt.addActionListener(unAdaptateurBoutons);
        bTransferer.addActionListener(unAdaptateurBoutons);
        addWindowListener((WindowListener) new AdapFenetre());

        getContentPane().add("Center", listClient);
        getContentPane().add("South", pBouton);

        setTitle("Liste des clients");

        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    public void ReloadListe() {

        if (listClient.getItemCount() > 0) {
            listClient.removeAll();
        }

        for (int i = 0; i < dlgMain.listeClient.size(); i++) {
            listClient.add(dlgMain.listeClient.afficher(i));
        }

        if (dlgMain.listeClient.size() > 0) {
            listClient.select(0);
        }

    }

    class AdaptateurBoutons implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == bSupprimer) {
                String sCode = listClient.getSelectedItem();
                if (sCode != null) {
                    int iEspace = sCode.indexOf(" ");
                    sCode = sCode.substring(0, iEspace);
                    int iCode = Integer.parseInt(sCode);

                    for (int i = 0; i < dlgMain.listeClient.size(); i++) {
                        int iCodeCli = dlgMain.listeClient.getCode(i);
                        if (iCodeCli == iCode) {
                            dlgMain.listeClient.supprimerClient(i);
                            System.out.println("Client supprimé : " + sCode);
                            ReloadListe();
                        }
                    }
                }
            } else if (e.getSource() == bNouveau) {
                int iNextCode = dlgMain.listeClient.size();
                if (iNextCode != 0) {
                    iNextCode = dlgMain.listeClient.getCode(dlgMain.listeClient.size() - 1) + 1;
                }

                theDlgCreateClient = new DlgCreateClient(dlgMain, iNextCode);
                theDlgCreateClient.addWindowListener((WindowListener) new AdapFenetreCreate());
            } else if (e.getSource() == bFermer) {
                setVisible(false);
            } else if (e.getSource() == bListeCpt) {
                String sCode = listClient.getSelectedItem();
                int iEspace = sCode.indexOf(" ");
                sCode = sCode.substring(0, iEspace);
                int iCode = Integer.parseInt(sCode);
                new DlgListeCompte(dlgMain, iCode);

            } else if (e.getSource() == bNouvCpt) {
                int iNextCode = dlgMain.listeCompte.size();
                if (iNextCode != 0) {
                    iNextCode = dlgMain.listeCompte.getCodeCompte(dlgMain.listeCompte.size() - 1) + 1;
                }

                String sCode = listClient.getSelectedItem();
                int iEspace = sCode.indexOf(" ");
                sCode = sCode.substring(0, iEspace);
                int iCode = Integer.parseInt(sCode);

                new DlgCreateCompte(dlgMain, iCode, iNextCode);
            } else if (e.getSource() == bTransferer) {
                new DlgTransferer(dlgMain, listClient.getSelectedItem());
            }
        }//fin de AdaptateurBoutons

        class AdapFenetreCreate extends WindowAdapter {
            public void windowDeactivated(WindowEvent e) {
                ReloadListe();
            }
        }//fin de AdapFenetreCreate

    }//fin de AdaptateurBoutons

    class AdapFenetre extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            setVisible(false);
        }
    }//fin de AdapFenetre

}//fin de DlgListeClient

