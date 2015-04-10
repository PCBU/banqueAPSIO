package IHM;

import Metier.CompteEpargne;
import Metier.Comptes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Dikyx on 10/04/2015.
 */
public class DlgSetInteret extends JFrame {
    DlgMain dlgMain;
    AdaptateurBoutons unAdaptateurBoutons;
    JButton bSetInteret;
    List listCompteEpargne;
    TextField tfNewInteret;
    int iCodeClient;

    public DlgSetInteret(DlgMain dlg) {
        dlgMain = dlg;

        listCompteEpargne = new List();
        ReloadListeCompteEpargne();

        Panel pBouton = new Panel();
        FlowLayout flBouton = new FlowLayout();

        tfNewInteret = new TextField("", 10);
        bSetInteret = new JButton("Modifier taux Interet");
        pBouton.add(bSetInteret);

        unAdaptateurBoutons = new AdaptateurBoutons();
        bSetInteret.addActionListener(unAdaptateurBoutons);

        addWindowListener(new AdapFenetre());

        getContentPane().add("North", tfNewInteret);
        getContentPane().add("Center", listCompteEpargne);
        getContentPane().add("South", pBouton);
        setTitle("Liste des comptes");

        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    class AdaptateurBoutons implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == bSetInteret) {

                //dlgMain.listeCompte.getCompte(i).setTauxInteret(tfNewInteret.getText());
                double tauxInteret = Double.parseDouble(tfNewInteret.getText());
                int selectedIndex = listCompteEpargne.getSelectedIndex();
                String item = listCompteEpargne.getItem(selectedIndex);
                Integer integer = Integer.parseInt(item);
                Comptes compte = dlgMain.listeCompte.getCompte(integer);
                ((CompteEpargne) compte).setTauxInteret(tauxInteret);
                new DlgMessage(String.valueOf(((CompteEpargne) compte).getTauxInteret()));
            }
        }
    }

    public void ReloadListeCompteEpargne() {

        if (listCompteEpargne.getItemCount() > 0) {
            listCompteEpargne.removeAll();
        }

        for (int i = 0; i < dlgMain.listeCompte.size(); i++) {
            if (dlgMain.listeCompte.getCompte(i) instanceof CompteEpargne) {
                listCompteEpargne.add(dlgMain.listeCompte.afficher(i));
            }
        }
    }

    class AdapFenetreCreate extends WindowAdapter {
        public void windowDeactivated(WindowEvent e) {
            ReloadListeCompteEpargne();
        }
    }


    class AdapFenetre extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            setVisible(false);
        }
    }
}
