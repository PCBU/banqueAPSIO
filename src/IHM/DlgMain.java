package IHM;

import Application.*;
import Metier.Clients;
import Metier.CompteDepot;
import Metier.CompteEpargne;
import Metier.Comptes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Vector;

public class DlgMain extends JFrame {
    public DlgListeCompte theDlgListeCompte;
    public DlgListeClient theDlgListeClient;
    public DlgMessage theDlgMessage;
    public DlgAdmin theDlgAdmin;
    public CalculAgios theCalculAgios;
    public CalculInteret theCalculInteret;

    JButton bCompte;
    JButton bClient;
    JButton bAdmin;
    JButton bInteret;
    JButton bAgios;
    JButton bQuitter;

    AdaptateurBoutons unAdaptateurBoutons;

    JFrame passwordQuerying;
    JTextField username;
    JTextField password;
    JButton bValidate;

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

        listeClient = new ListeClient();
        listeCompte = new ListeCompte();
        
        loadAccount();

        bAdministrateur = false;
        dlgMain = this;
    }
    public void loadAccount(){
        Vector vc = new Vector();
        Vector vc2 = new Vector();
        FileReader reader;
        BufferedReader bufferedReader;
        String s;
        String[] result;

        try {
            reader = new FileReader("client.txt");
            bufferedReader = new BufferedReader(reader);
            while ((s = bufferedReader.readLine()) != null) {
                vc.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        for(int i = 0; i < vc.size() ; i++){
            s = (String)vc.elementAt(i);
            result = s.split(";");
            listeClient.add(result[1],result[2],Integer.valueOf(result[0]));
            for(int j = 3; j < result.length;j=j+2){
                if (("CE").equals(result[j + 1])){
                    listeCompte.addCompteEpargne(Integer.parseInt(result[j]));
                }
                if (("CD").equals(result[j + 1])){
                    listeCompte.addCompteDepot(Integer.parseInt(result[j]));
                }
                listeClient.addCompteToClient(Integer.parseInt(result[j]), listeClient.getCode(Integer.parseInt(result[0])));
            }
        }


        try {
            reader = new FileReader("compte.txt");
            bufferedReader = new BufferedReader(reader);
            while ((s = bufferedReader.readLine()) != null) {
                vc2.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(int i = 0; i < vc2.size() ; i++){
            s = (String)vc2.elementAt(i);
            result = s.split(";");
            listeCompte.addMouvement(Integer.valueOf(result[0]), Double.valueOf(result[4]), result[5], true);
        }
    }

    class AdaptateurBoutons implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == bCompte) {
                theDlgListeCompte = new DlgListeCompte(dlgMain, -1);
            } else if (e.getSource() == bClient) {
                theDlgListeClient = new DlgListeClient(dlgMain);
            } else if (e.getSource() == bQuitter) {
                save();
                System.exit(0);
            } else if (e.getSource() == bAdmin) {
                if (!bAdministrateur) {

                    passwordQuerying = new JFrame();
                    username = new JTextField("Username", 20);
                    password = new JTextField("Password", 20);
                    bValidate = new JButton("OK");
                    bValidate.addActionListener(unAdaptateurBoutons);

                    Panel pFields = new Panel(new GridLayout(1, 2));
                    pFields.add(username);
                    pFields.add(password);

                    Panel pButton = new Panel(new GridLayout(1, 1));
                    pButton.add(bValidate);

                    passwordQuerying.getContentPane().setLayout(new GridLayout(2, 1));
                    passwordQuerying.getContentPane().add(pFields);
                    passwordQuerying.getContentPane().add(pButton);
                    setFocusable(false);
                    passwordQuerying.setLocationRelativeTo(null);
                    passwordQuerying.pack();
                    passwordQuerying.setVisible(true);
                }
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
            } else if (e.getSource() == bValidate) {
                if (username.getText().equals("admin") && password.getText().equals("password")) {
                    theDlgAdmin = new DlgAdmin(dlgMain);
                } else {
                    theDlgMessage = new DlgMessage("Identifiants incorrects");
                }

                passwordQuerying.setVisible(false);
                setFocusable(true);
            }
        }
    }

    class AdapFenetre extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            save();
            System.exit(0);
        }
    }
    public void save(){
        try {
            FileWriter fw = new FileWriter("compte.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            String[][] sMouvement;

            for (int i = 0; i < listeCompte.size(); i++) {
                Comptes c = (Comptes) listeCompte.theComptes.elementAt(i);
                sMouvement = c.getMouvements();
                for (int j = 0; j < c.theMouvements.size(); j++) {
                    if(sMouvement != null){
                        bw.write( Integer.toString(listeCompte.getCodeCompte(i)) + ";");
                        bw.write(sMouvement[j][1] + ";");
                        bw.write(sMouvement[j][2] + ";");
                        bw.write(sMouvement[j][3] + ";");
                        if(sMouvement[j][5] == null){
                            bw.write(sMouvement[j][4] + ";");
                        }else {
                            bw.write(sMouvement[j][5] + ";");
                        }
                        bw.write(sMouvement[j][6] + ";");
                        bw.write("\n");
                    }
                }
            }
            bw.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            FileWriter fw = new FileWriter("client.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            Clients client;
            for (int i = 0; i < listeClient.size();i++){
                client =  (Clients)listeClient.theClients.elementAt(i);
                bw.write(client.getCodeClient() + ";");
                bw.write(client.getNom() + ";");
                bw.write(client.getAdresse() + ";");

                for (int j = 0; j < dlgMain.listeClient.nbCptForClient(client.getCodeClient()); j++) {
                    bw.write(Integer.toString(listeClient.cptClient(client.getCodeClient(),j)) + ";" );
                    if(listeCompte.getCompte(listeClient.cptClient(client.getCodeClient(),j)) instanceof CompteEpargne){
                        bw.write("CE;");
                    }
                    if(listeCompte.getCompte(listeClient.cptClient(client.getCodeClient(),j)) instanceof CompteDepot){
                        bw.write("CD;");
                    }
                }
                bw.write("\n");
            }
            bw.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
