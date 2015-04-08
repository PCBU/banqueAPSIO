//Source file: c:\\Mes documents\\Lecomte - Barbieri\\Projet UML-Java\\IHM\\DlgDebiter.java

package IHM;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Application.Debiter;

public class DlgDebiter extends JFrame {
    DlgMain dlgMain;

    public Debiter theDebiter;

    Choice cbCompte;
    JButton bDebiter;
    JButton bAnnuler;
    TextField tfMontant;
    TextField tfDesc;
    int iCptSel;
    AdaptateurBoutons unAdaptateurBoutons;

    public DlgDebiter(DlgMain dlg, int iIndex) {
        dlgMain = dlg;

        cbCompte = new Choice();
        ReloadListe();
        cbCompte.select(iIndex);
        iCptSel = Integer.parseInt(cbCompte.getItem(iIndex));

        Panel pCompte = new Panel();
        FlowLayout flCompte = new FlowLayout();
        pCompte.setLayout(flCompte);
        pCompte.add(new Label("Compte en cours :"));
        pCompte.add(cbCompte);

        Panel pBouton = new Panel();
        FlowLayout flBouton = new FlowLayout();
        pBouton.setLayout(flBouton);
        bDebiter = new JButton("Débiter");
        bAnnuler = new JButton("Annuler");
        pBouton.add(bDebiter);
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

        unAdaptateurBoutons = new AdaptateurBoutons();
        bDebiter.addActionListener(unAdaptateurBoutons);
        bAnnuler.addActionListener(unAdaptateurBoutons);

        addWindowListener((WindowListener) new AdapFenetre());

        setTitle("Débiter un compte");

        setLocationRelativeTo(null);
        pack();
        setVisible(true);

        cbCompte.addItemListener
                (new ItemListener() {
                     public void itemStateChanged(ItemEvent e) {
                         iCptSel = Integer.parseInt(cbCompte.getSelectedItem());
                     }
                 }

                );
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
            if (e.getSource() == bDebiter) {
                new Debiter(dlgMain.listeCompte, iCptSel, Double.parseDouble(tfMontant.getText()), tfDesc.getText(), dlgMain.bAdministrateur);
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

