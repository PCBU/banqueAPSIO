//Source file: c:\\Mes documents\\Lecomte - Barbieri\\Projet UML-Java\\IHM\\DlgCreateClient.java

package IHM;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Application.*;

public class DlgCreateClient extends JFrame {
    DlgMain dlgMain;
    public CreerClient theCreerClient;

    JButton bValider;
    JButton bAnnuler;
    JButton bCompte;
    Label lCode;
    TextField tfNom;
    TextField tfAdr;
    AdaptateurBoutons unAdaptateurBoutons;

    /**
     * @roseuid 3D2461550226
     */
    public DlgCreateClient(DlgMain dlg, int iCodeClient) {
        dlgMain = dlg;

        Panel pCode = new Panel();
        FlowLayout flCode = new FlowLayout();
        pCode.setLayout(flCode);
        pCode.add(new Label("Code :"));
        lCode = new Label(Integer.toString(iCodeClient));
        pCode.add(lCode);

        Panel pNom = new Panel();
        FlowLayout flNom = new FlowLayout();
        pNom.setLayout(flNom);
        pNom.add(new Label("Nom :"));
        tfNom = new TextField("", 20);
        pNom.add(tfNom);

        Panel pAdresse = new Panel();
        FlowLayout flAdresse = new FlowLayout();
        pAdresse.setLayout(flAdresse);
        pAdresse.add(new Label("Adresse :"));
        tfAdr = new TextField("", 20);
        pAdresse.add(tfAdr);

        Panel pClient = new Panel();
        BorderLayout blClient = new BorderLayout();
        pClient.setLayout(blClient);
        pClient.add("North", pCode);
        pClient.add("Center", pNom);
        pClient.add("South", pAdresse);

        Panel pBouton = new Panel();
        FlowLayout flBouton = new FlowLayout();
        pBouton.setLayout(flBouton);
        bValider = new JButton("Créer client");
        bCompte = new JButton("Compte");
        bAnnuler = new JButton("Annuler");
        bCompte.setEnabled(false);

        pBouton.add(bValider);
        pBouton.add(bCompte);
        pBouton.add(bAnnuler);

        getContentPane().add("North", pClient);
        getContentPane().add("South", pBouton);

        unAdaptateurBoutons = new AdaptateurBoutons();
        bAnnuler.addActionListener(unAdaptateurBoutons);
        bValider.addActionListener(unAdaptateurBoutons);
        bCompte.addActionListener(unAdaptateurBoutons);
        addWindowListener((WindowListener) new AdapFenetre());

        setTitle("Nouveau client");

        pack();
        show();
    }

    class AdaptateurBoutons implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == bAnnuler) {
                setVisible(false);
            } else if (e.getSource() == bValider) {
                if (bValider.getText().equals("Créer client")) {
                    String sNom, sAdr, sCode;
                    sNom = tfNom.getText();
                    sAdr = tfAdr.getText();
                    sCode = lCode.getText();

                    if (!sNom.equals("") && !sAdr.equals("")) {
                        dlgMain.listeClient.add(sNom, sAdr, Integer.parseInt(sCode));
                        bAnnuler.setText("Fermer");
                        bValider.setText("Nouveau");
                        bCompte.setEnabled(true);
                        tfAdr.setEnabled(false);
                        tfNom.setEnabled(false);
                    } else {
                        System.out.println("Les champs ne doivent pas etre vide");
                    }
                } else if (bValider.getText().equals("Nouveau")) {
                    bValider.setText("Créer client");
                    bCompte.setEnabled(false);
                    tfAdr.setEnabled(true);
                    tfNom.setEnabled(true);
                    tfAdr.setText("");
                    tfNom.setText("");
                    lCode.setText(Integer.toString(Integer.parseInt(lCode.getText()) + 1));
                }
            } else if (e.getSource() == bCompte) {
                int iNextCode = dlgMain.listeCompte.size();
                if (iNextCode != 0) {
                    iNextCode = dlgMain.listeCompte.getCodeCompte(dlgMain.listeCompte.size() - 1) + 1;
                }
                new DlgCreateCompte(dlgMain, Integer.parseInt(lCode.getText()), iNextCode);

            }
        }
    }

    class AdapFenetre extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            setVisible(false);
        }
    }

}
