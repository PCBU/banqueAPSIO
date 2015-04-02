//Source file: c:\\Mes documents\\Lecomte - Barbieri\\Projet UML-Java\\IHM\\DlgTransferer.java

package IHM;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DlgTransferer extends JFrame {
    DlgMain dlgMain;

    Choice cbCompteDepart;
    Choice cbCompteArrivee;
    Choice cbClient;
    JButton bTransferer;
    JButton bAnnuler;
    TextField tfMontant;
    TextField tfDesc;
    int iCptSelDep, iCptSelArr;
    AdaptateurBoutons unAdaptateurBoutons;

    public DlgTransferer(DlgMain dlg, String sClient) {
        dlgMain = dlg;

        cbClient = new Choice();
        cbCompteDepart = new Choice();
        cbCompteArrivee = new Choice();
        iCptSelDep = 0;
        iCptSelArr = 0;

        for (int i = 0; i < dlgMain.listeClient.size(); i++) {
            cbClient.add(dlgMain.listeClient.afficher(i));
        }

        if (sClient != null) {
            cbClient.select(sClient);
        }

        ReloadListes();

        Panel pClient = new Panel();
        pClient.setLayout(new FlowLayout());
        pClient.add(new Label("Client :"));
        pClient.add(cbClient);

        Panel pCompteDepart = new Panel();
        pCompteDepart.setLayout(new FlowLayout());
        pCompteDepart.add(new Label("Compte source :"));
        pCompteDepart.add(cbCompteDepart);

        Panel pCompteArrivee = new Panel();
        pCompteArrivee.setLayout(new FlowLayout());
        pCompteArrivee.add(new Label("Compte destination :"));
        pCompteArrivee.add(cbCompteArrivee);


        Panel pMontant = new Panel();
        FlowLayout flMontant = new FlowLayout();
        pMontant.setLayout(flMontant);
        pMontant.add(new Label("Montant :"));
        tfMontant = new TextField("", 20);
        pMontant.add(tfMontant);

        Panel pBouton = new Panel();
        FlowLayout flBouton = new FlowLayout();
        pBouton.setLayout(flBouton);
        bTransferer = new JButton("Transférer");
        bAnnuler = new JButton("Annuler");
        pBouton.add(bTransferer);
        pBouton.add(bAnnuler);

        getContentPane().setLayout(new GridLayout(5, 1));
        getContentPane().add(pClient);
        getContentPane().add(pCompteDepart);
        getContentPane().add(pCompteArrivee);
        getContentPane().add(pMontant);
        getContentPane().add(pBouton);

        cbClient.addItemListener
                (new ItemListener() {
                     public void itemStateChanged(ItemEvent e) {
                         ReloadListes();
                     }
                 }

                );

        cbCompteDepart.addItemListener
                (new ItemListener() {
                     public void itemStateChanged(ItemEvent e) {
                         iCptSelDep = Integer.parseInt(cbCompteDepart.getSelectedItem());
                     }
                 }

                );

        cbCompteArrivee.addItemListener
                (new ItemListener() {
                     public void itemStateChanged(ItemEvent e) {
                         iCptSelArr = Integer.parseInt(cbCompteArrivee.getSelectedItem());
                     }
                 }

                );


        unAdaptateurBoutons = new AdaptateurBoutons();
        bTransferer.addActionListener(unAdaptateurBoutons);
        bAnnuler.addActionListener(unAdaptateurBoutons);

        addWindowListener((WindowListener) new AdapFenetre());

        setTitle("Transférer compte à compte");

        pack();
        show();
    }

    private void ReloadListes() {
        String sCodeCli = cbClient.getSelectedItem();
        int iEspace = sCodeCli.indexOf(" ");
        sCodeCli = sCodeCli.substring(0, iEspace);
        int iCode = Integer.parseInt(sCodeCli);

        if (cbCompteDepart.getItemCount() > 0) {
            cbCompteDepart.removeAll();
            cbCompteArrivee.removeAll();
        }

        for (int i = 0; i < dlgMain.listeCompte.size(); i++) {
            if (dlgMain.listeClient.appartientCompte(iCode, dlgMain.listeCompte.getCodeCompte(i))) {
                String sCode = dlgMain.listeCompte.afficher(i);
                cbCompteDepart.add(sCode);
                cbCompteArrivee.add(sCode);
            }
        }
    }

    class AdaptateurBoutons implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == bTransferer) {
                double dMont = Double.parseDouble(tfMontant.getText());
                int iCptDep = Integer.parseInt(cbCompteDepart.getSelectedItem());
                int iCptArr = Integer.parseInt(cbCompteArrivee.getSelectedItem());
                String sDescDep = "Transfert vers le compte " + iCptArr;
                String sDescArr = "Transfert du compte " + iCptDep;
                //Débit du compte source
                boolean bOK = dlgMain.listeCompte.addMouvement(iCptDep, dMont * -1, sDescDep, dlgMain.bAdministrateur);
                if (bOK) {
                    //Crédit du compte destination
                    dlgMain.listeCompte.addMouvement(iCptArr, dMont, sDescArr, dlgMain.bAdministrateur);
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

