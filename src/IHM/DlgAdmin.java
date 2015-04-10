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


    public CalculAgios theCalculAgios;
    public CalculInteret theCalculInteret;

    JButton bInteret;
    JButton bAgios;
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
        bInteret = new JButton("Calculer les Intérêts");
        bTousComptes = new JButton("Opérations de tous les comptes");
        bQuitter = new JButton("Passer en mode Employé");


        //evenements sur controles
        unAdaptateurBoutons = new AdaptateurBoutons();
        bAgios.addActionListener(unAdaptateurBoutons);
        bInteret.addActionListener(unAdaptateurBoutons);
        bTousComptes.addActionListener(unAdaptateurBoutons);
        bQuitter.addActionListener(unAdaptateurBoutons);

        addWindowListener(new AdapFenetre());

        setLocationRelativeTo(null);

        //ajout des controles
        getContentPane().setLayout(new GridLayout(2, 3));
        getContentPane().add(bAgios);
        getContentPane().add(bInteret);
        getContentPane().add(bTousComptes);
        getContentPane().add(bQuitter);

        setTitle("Gestion des comptes - Employé");

        pack();
        setVisible(true);

        //initialisations

        this.dlgMain = dlgMain;
        listeCompte = dlgMain.listeCompte;
        listeClient = dlgMain.listeClient;
    }

    class AdaptateurBoutons implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == bQuitter) {
                setVisible(false);
            } else if (e.getSource() == bTousComptes) {
                theDlgDetailCompte = new DlgDetailCompte(dlgMain);
            } else if (e.getSource() == bAgios) {
                double agiotot = 0;
                for (int i = 0; i < listeCompte.size(); i++) {
                    String sClassName = (listeCompte.getCompte(listeCompte.getCodeCompte(i)).getClass()).getName();
                    if (sClassName.equals("Metier.CompteDepot")) {

                        theCalculAgios = new CalculAgios(listeCompte, listeCompte.getCodeCompte(i));
                        agiotot += theCalculAgios.getMontantAGIO();
                    }
                }
                new DlgMessage("Agios calculées : " + agiotot);

            } else if (e.getSource() == bInteret) {
                double intererTot = 0;
                for (int i = 0; i < listeCompte.size(); i++) {
                    String sClassName = (listeCompte.getCompte(listeCompte.getCodeCompte(i)).getClass()).getName();
                    if (sClassName.equals("Metier.CompteEpargne")) {
                        theCalculInteret = new CalculInteret(listeCompte, listeCompte.getCodeCompte(i));

                        intererTot += theCalculInteret.getInteret();
                    }
                    new DlgMessage("Agios calculées : " + intererTot);
                }
            }
        }
    }


    class AdapFenetre extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            setVisible(false);
        }
    }
}
