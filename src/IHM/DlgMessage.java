package IHM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Application.*;

/**
 * Created by S219 on 01/04/2015.
 */
public class DlgMessage extends JFrame{
    JLabel JMessage;
    JButton bOk;
    AdaptateurBoutons unAdaptateurBoutons;


    public DlgMessage(String msg) {


        //Panel pMessage = new Panel();
        //FlowLayout flCompte = new FlowLayout();
       // pMessage.setLayout(flCompte);
       // pMessage.add(new Label("Message :"));

        Panel pBouton = new Panel();
        FlowLayout flBouton = new FlowLayout();
        pBouton.setLayout(flBouton);
        bOk = new JButton("OK");
        pBouton.add(bOk);

        Panel pMessage = new Panel();
        FlowLayout flDescription = new FlowLayout();
        pMessage.setLayout(flDescription);
        pMessage.add(new Label(""));
        JMessage = new JLabel(msg);
        pMessage.add(JMessage);


        Panel pInfo = new Panel();

        pInfo.add(pMessage);
        unAdaptateurBoutons = new AdaptateurBoutons();
        bOk.addActionListener(unAdaptateurBoutons);

        getContentPane().add("Center", pMessage);
        getContentPane().add("South", pBouton);

        addWindowListener(new AdapFenetre());

        setTitle("Message");

        pack();
        setVisible(true);

    }

    class AdapFenetre extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            setVisible(false);
        }
    }


    class AdaptateurBoutons implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == bOk) {
                setVisible(false);
            }
        }
    }

}



