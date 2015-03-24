//Source file: c:\\Mes documents\\Lecomte - Barbieri\\Projet UML-Java\\IHM\DlgCreateCompte.java

package IHM;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import Application.*;

public class DlgCreateCompte extends JFrame {
    DlgMain dlgMain;

    public OuvrirCompte theOuvrirCompte;

    Choice cbClient;
    Choice cbTypeCompte;
    JButton bValider;
    JButton bAnnuler;
    TextField tfDepot;
    JRadioButton rbCptEp;
    JRadioButton rbCptDep;
    int iNumCpt;
    AdaptateurBoutons unAdaptateurBoutons;

    public DlgCreateCompte(DlgMain dlg, int iCodeClient, int iCodeCpt) {
        dlgMain = dlg;

        Panel pNumCpt = new Panel();
        FlowLayout flNumCpt = new FlowLayout();
        pNumCpt.setLayout(flNumCpt);
        iNumCpt = iCodeCpt;
        Label lNum = new Label("Numéro de compte :");
        Label lNumCpt = new Label(Integer.toString(iNumCpt));
        pNumCpt.add(lNum);
        pNumCpt.add(lNumCpt);

        cbClient = new Choice();
        ReloadListe();
        Panel pClient = new Panel();
        FlowLayout flClient = new FlowLayout();
        pClient.setLayout(flClient);
        Label lCli = new Label("Numéro de client :");
        pClient.add(lCli);
        pClient.add(cbClient);

        Panel pCptED = new Panel();
        FlowLayout flCptED = new FlowLayout();
        pCptED.setLayout(flCptED);
        Label lTypeCpt = new Label("Type de compte :");
        rbCptEp = new JRadioButton("Compte Epargne", true);
        rbCptDep = new JRadioButton("Compte Dépôt");
        pCptED.add(lTypeCpt);
        pCptED.add(rbCptEp);
        pCptED.add(rbCptDep);

        ButtonGroup group = new ButtonGroup();
        group.add(rbCptEp);
        group.add(rbCptDep);


        Panel pDepot = new Panel();
        FlowLayout flDepot = new FlowLayout();
        pDepot.setLayout(flDepot);
        Label lDepot = new Label("Dépôt initial :");
        tfDepot = new TextField("", 20);
        pDepot.add(lDepot);
        pDepot.add(tfDepot);

        Panel pBouton = new Panel();
        FlowLayout flBouton = new FlowLayout();
        pBouton.setLayout(flBouton);
        bValider = new JButton("Créer compte");
        bAnnuler = new JButton("Annuler");
        pBouton.add(bValider);
        pBouton.add(bAnnuler);

        getContentPane().setLayout(new GridLayout(5, 1));
        getContentPane().add(pNumCpt);
        getContentPane().add(pClient);
        getContentPane().add(pCptED);
        getContentPane().add(pDepot);
        getContentPane().add(pBouton);

        //préselection du client
        if (iCodeClient != -1) {
            for (int i = 0; i < cbClient.getItemCount(); i++) {
                if (Integer.parseInt(cbClient.getItem(i)) == iCodeClient) {
                    cbClient.select(i);
                }
            }
        } else {    //selection par défaut
            if (cbClient.getItemCount() > 0) {
                cbClient.select(0);
            } else //blocage de la fenetre
            {
                cbClient.setEnabled(false);
            }
        }

        unAdaptateurBoutons = new AdaptateurBoutons();
        bValider.addActionListener(unAdaptateurBoutons);
        bAnnuler.addActionListener(unAdaptateurBoutons);
        addWindowListener((WindowListener) new AdapFenetre());

        setTitle("Créer un compte");

        pack();
        show();
    }

    public void ReloadListe() {
        if (cbClient.getItemCount() > 0) {
            cbClient.removeAll();
        }

        for (int i = 0; i < dlgMain.listeClient.size(); i++) {
            String sCode = dlgMain.listeClient.afficher(i);
            int iEspace = sCode.indexOf(" ");
            sCode = sCode.substring(0, iEspace);
            cbClient.add(sCode);
        }
    }

    class AdaptateurBoutons implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == bValider) {
                OuvrirCompte oc = new OuvrirCompte(dlgMain.listeClient, iNumCpt, Integer.parseInt(cbClient.getSelectedItem()), Double.parseDouble(tfDepot.getText()));
                if (rbCptDep.isSelected()) {
                    dlgMain.listeCompte.addCompteDepot(oc.createCompteDepot());
                } else {
                    dlgMain.listeCompte.addCompteEpargne(oc.createCompteEpargne());
                }
                setVisible(false);
            } else if (e.getSource() == bAnnuler) {
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

