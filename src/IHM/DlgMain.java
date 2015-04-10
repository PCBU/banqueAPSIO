package IHM;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.Vector;
import Application.*;
import Metier.Clients;
import Metier.Comptes;


public class DlgMain extends JFrame {
    public DlgListeCompte theDlgListeCompte;
    public DlgListeClient theDlgListeClient;
    public DlgDetailCompte theDlgDetailCompte;
    public DlgCreateClient theDlgCreateClient;
    public DlgMessage theDlgMessage;
    public DlgAdmin theDlgAdmin;
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

        //initialisations
        listeClient = new ListeClient();
        listeCompte = new ListeCompte();


        loadAccount();

//

        bAdministrateur = false;



        dlgMain = this;

    }
    public void loadAccount(){
        Vector vc = new Vector();
        Vector vc2 = new Vector();

        FileReader reader;
        BufferedReader buffreader;
        String s;
        String texte;
        String[] result;
        try {
            reader = new FileReader("client.txt");
            buffreader = new BufferedReader(reader);
            while ((s = buffreader.readLine()) != null) {
                vc.add(s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < vc.size() ; i++){
            texte = (String)vc.elementAt(i);
            result = texte.split(";");
            listeClient.add(result[1],result[2],Integer.valueOf(result[0]));
            for(int j = 3; j < result.length;j++){
                listeClient.addCompteToClient(Integer.parseInt(result[j]) , Integer.valueOf(result[0]) );
                listeCompte.addCompte(Integer.parseInt(result[j]) );
            }
        }
        try {
            reader = new FileReader("compte.txt");
            buffreader = new BufferedReader(reader);
            while ((s = buffreader.readLine()) != null) {
                vc2.add(s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < vc2.size() ; i++){
            texte = (String)vc2.elementAt(i);
            result = texte.split(";");
            listeCompte.addMouvement(Integer.valueOf(result[0]), Double.valueOf(result[4]), result[6], true);
        }


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

                try {
                    FileWriter fw = new FileWriter("compte.txt");
                    BufferedWriter bw = new BufferedWriter(fw);

                        String sMvt[][] = new String[100][100];
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
                                        bw.write(sMouvement[j][4] + ";");
                                        bw.write(sMouvement[j][5] + ";");
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
                        }

                    bw.write("\n");
                }
                bw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            System.exit(0);
        }
    }
}
