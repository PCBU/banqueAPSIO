package IHM;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Vector;

import Application.*;

public class DlgMain extends JFrame {
    public DlgListeCompte theDlgListeCompte;
    public DlgListeClient theDlgListeClient;
    public DlgDetailCompte theDlgDetailCompte;
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
    JButton bTousComptes;
    JButton bQuitter;
    //wtf

    AdaptateurBoutons unAdaptateurBoutons;

    JFrame passwordQuerying;
    JTextField username;
    JTextField password;
    JButton bValidate;


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
        bTousComptes = new JButton("Opérations de tous les comptes");
        bQuitter = new JButton("Quitter l'application");


        //evenements sur controles
        unAdaptateurBoutons = new AdaptateurBoutons();
        bCompte.addActionListener(unAdaptateurBoutons);
        bClient.addActionListener(unAdaptateurBoutons);
        bAdmin.addActionListener(unAdaptateurBoutons);
        bAgios.addActionListener(unAdaptateurBoutons);
        bInteret.addActionListener(unAdaptateurBoutons);
        bTousComptes.addActionListener(unAdaptateurBoutons);
        bQuitter.addActionListener(unAdaptateurBoutons);

        addWindowListener(new AdapFenetre());

        setLocationRelativeTo(null);

        //ajout des controles
        getContentPane().setLayout(new GridLayout(2, 3));
        getContentPane().add(bCompte);
        getContentPane().add(bAdmin);
        getContentPane().add(bClient);
        getContentPane().add(bQuitter);

        setTitle("Gestion des comptes - Employé");

        pack();
        setVisible(true);

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

                    passwordQuerying = new JFrame();
                    username = new JTextField("Username", 20);
                    password = new JTextField("Password", 20);
                    bValidate = new JButton("OK");

                    passwordQuerying.getContentPane().setLayout(new GridLayout(2, 2));
                    passwordQuerying.getContentPane().add(username);
                    passwordQuerying.getContentPane().add(password);
                    bValidate.addActionListener(unAdaptateurBoutons);
                    passwordQuerying.getContentPane().add(bValidate);
                    setFocusable(false);
                    passwordQuerying.setVisible(true);
                } else {
                    getContentPane().remove(bAgios);
                    getContentPane().remove(bInteret);
                    getContentPane().remove(bTousComptes);
                    getContentPane().setLayout(new GridLayout(2, 3));

                    bAdministrateur = false;
                    bAdmin.setText("Passer en mode Administrateur");
                    setTitle("Gestion des comptes - Employé");
                }
            } else if (e.getSource() == bAgios) {
                for (int i = 0; i < listeCompte.size(); i++) {
                    String sClassName = (listeCompte.getCompte(listeCompte.getCodeCompte(i)).getClass()).getName();
                    if (sClassName.equals("Metier.CompteDepot")) {
                        theCalculAgios = new CalculAgios(listeCompte, listeCompte.getCodeCompte(i));
                    }
                }
            } else if (e.getSource() == bInteret) {
                for (int i = 0; i < listeCompte.size(); i++) {
                    String sClassName = (listeCompte.getCompte(listeCompte.getCodeCompte(i)).getClass()).getName();
                    if (sClassName.equals("Metier.CompteEpargne")) {
                        theCalculInteret = new CalculInteret(listeCompte, listeCompte.getCodeCompte(i));
                    }
                }
            } else if (e.getSource() == bTousComptes) {
                theDlgDetailCompte = new DlgDetailCompte(dlgMain);
            } else if (e.getSource() == bValidate) {
                if (username.getText().equals("admin") && password.getText().equals("password")) {
                    getContentPane().add(bAgios);
                    getContentPane().add(bInteret);
                    getContentPane().add(bTousComptes);
                    getContentPane().setLayout(new GridLayout(3, 3));

                    bAdministrateur = true;
                    bAdmin.setText("Passer en mode Employé");
                    setTitle("Gestion des comptes - Administrateur");
                } else {
                    JFrame alert = new JFrame("Error");
                    alert.add(new JLabel("Identifiants incorrects"));
                    alert.setVisible(true);
                }

                passwordQuerying.setVisible(false);
                setFocusable(true);
            }
        }
    }

    class AdapFenetre extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }
}
