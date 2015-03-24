//Source file: c:\\Mes documents\\Lecomte - Barbieri\\Projet UML-Java\\IHM\\DlgCrediter.java

package IHM;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Application.Crediter;

public class DlgCrediter extends JFrame {
    DlgMain dlgMain;
    public Crediter theCrediter;

    Choice cbCompte;
    JButton bCrediter;
    JButton bAnnuler;
    TextField tfMontant;
    TextField tfDesc;
    int iCptSel;
    AdaptateurBoutons unAdaptateurBoutons;

    public DlgCrediter(DlgMain dlg, int iIndex) {
        dlgMain = dlg;

        cbCompte = new Choice();
        ReloadListe();
        cbCompte.select(iIndex);
        iCptSel = Integer.parseInt(cbCompte.getSelectedItem());

        Panel pCompte = new Panel();
        FlowLayout flCompte = new FlowLayout();
        pCompte.setLayout(flCompte);
        pCompte.add(new Label("Compte en cours :"));
        pCompte.add(cbCompte);

        Panel pBouton = new Panel();
        FlowLayout flBouton = new FlowLayout();
        pBouton.setLayout(flBouton);
        bCrediter = new JButton("Créditer");
        bAnnuler = new JButton("Annuler");
        pBouton.add(bCrediter);
        pBouton.add(bAnnuler);


        Panel pMontant = new Panel();
        FlowLayout flMontant = new FlowLayout();
        pMontant.setLayout(flMontant);
        pMontant.add(new Label("Montant :"));
        tfMontant = new TextField("", 20);
        pMontant.add(tfMontant);

        Panel pDescription = new Panel();
        FlowLayout flDescription = new FlowLayout();
        pDescription.setLayout(flDescription);
        pDescription.add(new Label("Description :"));
        tfDesc = new TextField("", 20);
        pDescription.add(tfDesc);

        Panel pInfo = new Panel();
        pInfo.add(pMontant);
        pInfo.add(pDescription);


        getContentPane().add("North", pCompte);
        getContentPane().add("Center", pInfo);
        getContentPane().add("South", pBouton);

        cbCompte.addItemListener
                (new ItemListener() {
                     public void itemStateChanged(ItemEvent e) {
                         iCptSel = Integer.parseInt(cbCompte.getSelectedItem());
                     }
                 }

                );


        unAdaptateurBoutons = new AdaptateurBoutons();
        bCrediter.addActionListener(unAdaptateurBoutons);
        bAnnuler.addActionListener(unAdaptateurBoutons);

        addWindowListener((WindowListener) new AdapFenetre());

        setTitle("Créditer un compte");

        pack();
        show();
    }

    public void ReloadListe() {
        if (cbCompte.getItemCount() > 0) {
            cbCompte.removeAll();
        }

        for (int i = 0; i < dlgMain.listeCompte.size(); i++) {
            String sCode = dlgMain.listeCompte.afficher(i);
            cbCompte.add(sCode);
        }
    }

    class AdaptateurBoutons implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == bCrediter) {
                new Crediter(dlgMain.listeCompte, iCptSel, Double.parseDouble(tfMontant.getText()), tfDesc.getText());
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

