package IHM;

import Application.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

public class DlgAdmin extends JFrame {

    public CalculInteret theCalculInteret;
    public DlgSetAgios theDlgSetAgios;
    public DlgSetInteret theDlgSetInteret;

    JButton bInteret;
    JButton bAgios;
    JButton bModifAgios;
    JButton bModifInteret;
    JButton bTousComptes;
    JButton bQuitter;

    AdaptateurBoutons unAdaptateurBoutons;

    ListeClient listeClient;
    ListeCompte listeCompte;
    boolean bAdministrateur;

    DlgMain dlgMain;
    public DlgDetailCompte theDlgDetailCompte;

    public DlgAdmin(DlgMain dlgMain) {
        //Création des controles
        bAgios = new JButton("Calculer les Agios");
        bModifAgios = new JButton("Modifier le taux d'agios d'un compte");
        bInteret = new JButton("Calculer les Intérêts");
        bTousComptes = new JButton("Opérations de tous les comptes");
        bModifInteret = new JButton("Modifier le taux d'intérêt d'un compte");

        bQuitter = new JButton("Passer en mode Employé");


        //evenements sur controles
        unAdaptateurBoutons = new AdaptateurBoutons();
        bAgios.addActionListener(unAdaptateurBoutons);
        bModifAgios.addActionListener(unAdaptateurBoutons);
        bTousComptes.addActionListener(unAdaptateurBoutons);
        bInteret.addActionListener(unAdaptateurBoutons);
        bModifInteret.addActionListener(unAdaptateurBoutons);
        bQuitter.addActionListener(unAdaptateurBoutons);

        addWindowListener(new AdapFenetre());

        setLocationRelativeTo(null);

        //ajout des controles
        getContentPane().setLayout(new GridLayout(2, 3));
        getContentPane().add(bAgios);
        getContentPane().add(bModifAgios);
        getContentPane().add(bTousComptes);
        getContentPane().add(bInteret);
        getContentPane().add(bModifInteret);
        getContentPane().add(bQuitter);

        setTitle("Gestion des comptes - Admin");

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

        this.dlgMain = dlgMain;

    }

    class AdaptateurBoutons implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == bQuitter) {
                setVisible(false);
            } else if (e.getSource() == bInteret) {
                for (int i = 0; i < listeCompte.size(); i++) {
                    String sClassName = (listeCompte.getCompte(listeCompte.getCodeCompte(i)).getClass()).getName();
                    if (sClassName.equals("Metier.CompteEpargne")) {
                        theCalculInteret = new CalculInteret(listeCompte, listeCompte.getCodeCompte(i));
                    }
                }
            } else if (e.getSource() == bTousComptes) {
                theDlgDetailCompte = new DlgDetailCompte(dlgMain);
            } else if (e.getSource() == bModifAgios ){
                theDlgSetAgios = new DlgSetAgios(dlgMain);
            } else if (e.getSource() == bModifInteret){
                theDlgSetInteret = new DlgSetInteret(dlgMain);
            }
        }
    }

    class AdapFenetre extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            setVisible(false);
        }
    }
}
