package IHM;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Vector;

import Application.*;

public class DlgMain extends JFrame {
    public DlgListeCompte theDlgListeCompte;
    public DlgListeClient theDlgListeClient;
    public DlgCreateClient theDlgCreateClient;
    public CalculAgios theCalculAgios;
    public CalculInteret theCalculInteret;
    public EditionReleves theEditionReleves;
    public EditionAvertissements theEditionAvertissements;

    JButton bCompte;
    JButton bClient;
    JButton bAdmin;
    JButton bInteret;
    JButton bAgios;
    JButton bQuitter;
    AdaptateurBoutons unAdaptateurBoutons;

    Vector vCompte = new Vector();
    ListeClient listeClient;
    ListeCompte listeCompte;
    boolean bAdministrateur;

    DlgMain dlgMain;

    public DlgMain() {
        //Création des controles
        bCompte = new JButton("Comptes");
        bClient = new JButton("Clients");
        bAdmin = new JButton("Passer en mode Administrateur");
        bAgios = new JButton("Calculer les Agios");
        bInteret = new JButton("Calculer les Intérêts");
        bQuitter = new JButton("Quitter l'application");

        //evenements sur controles
        unAdaptateurBoutons = new AdaptateurBoutons();
        bCompte.addActionListener(unAdaptateurBoutons);
        bClient.addActionListener(unAdaptateurBoutons);
        bAdmin.addActionListener(unAdaptateurBoutons);
        bAgios.addActionListener(unAdaptateurBoutons);
        bInteret.addActionListener(unAdaptateurBoutons);
        bQuitter.addActionListener(unAdaptateurBoutons);

        addWindowListener((WindowListener) new AdapFenetre());


        //ajout des controles
        getContentPane().setLayout(new GridLayout(2, 3));
        getContentPane().add(bCompte);
        getContentPane().add(bAdmin);
        getContentPane().add(bClient);
        getContentPane().add(bAgios);
        getContentPane().add(bInteret);
        getContentPane().add(bQuitter);

        setTitle("Gestion des comptes - Employé");

        pack();
        show();

        //initialisations
        listeClient = new ListeClient();
        listeCompte = new ListeCompte();

        listeClient.addCompteToClient(0, 1);
        listeClient.addCompteToClient(2, 1);
        listeClient.addCompteToClient(1, 2);
        listeClient.addCompteToClient(3, 2);
        listeClient.addCompteToClient(4, 2);

        bAdministrateur = false;

        listeCompte.addMouvement(0, 100, "Création par défaut", true);
        listeCompte.addMouvement(0, -20, "Création par défaut", true);
        listeCompte.addMouvement(1, 10000, "Création par défaut", true);
        listeCompte.addMouvement(2, 100, "Création par défaut", true);

        dlgMain = this;

    }

    class AdaptateurBoutons implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == bCompte) {
                theDlgListeCompte = new DlgListeCompte(dlgMain, -1);
            } else if (e.getSource() == bClient) {
                theDlgListeClient = new DlgListeClient(dlgMain);
            } else if (e.getSource() == bQuitter) {
                System.exit(0);
            } else if (e.getSource() == bAdmin) {
                if (!bAdministrateur) {
                    bAdministrateur = true;
                    bAdmin.setText("Passer en mode Employé");
                    setTitle("Gestion des comptes - Administrateur");
                } else {
                    bAdministrateur = false;
                    bAdmin.setText("Passer en mode Administrateur");
                    setTitle("Gestion des comptes - Employé");
                }
            } else if (e.getSource() == bAgios) {
                for (int i = 0; i < listeCompte.size(); i++) {
                    String sClassName = (listeCompte.getCompte(listeCompte.getCodeCompte(i)).getClass()).getName();
                    if (sClassName.equals("Metier.CompteDepot")) {
                        new CalculAgios(listeCompte, listeCompte.getCodeCompte(i));
                    }
                }
            } else if (e.getSource() == bInteret) {
                for (int i = 0; i < listeCompte.size(); i++) {
                    String sClassName = (listeCompte.getCompte(listeCompte.getCodeCompte(i)).getClass()).getName();
                    if (sClassName.equals("Metier.CompteEpargne")) {
                        new CalculInteret(listeCompte, listeCompte.getCodeCompte(i));
                    }
                }
            }
        }
    }//fin de AdaptateurBoutons

    class AdapFenetre extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }


}//fin du main
