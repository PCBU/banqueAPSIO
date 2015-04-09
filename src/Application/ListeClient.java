//Source file: c:\\Mes documents\\Lecomte - Barbieri\\Projet UML-Java\\Application\\ListeClient.java

package Application;

import Metier.Clients;
import Metier.Comptes;

import java.util.Vector;

public class ListeClient {
    public Vector theClients;

    /**
     * @roseuid 3D24647900AA
     */
    public ListeClient() {
        theClients = new Vector();



    }

    public void add(String nomCli, String adrCli, int codeCli) {
        theClients.add(new Clients(nomCli, adrCli, codeCli));
    }

    public boolean appartientCompte(int codeCli, int iCodeCpt) {
        boolean bTrouve = true;
        for (int i = 0; i < theClients.size(); i++) {
            Clients c = (Clients) theClients.elementAt(i);
            if (c.getCodeClient() == codeCli) {
                for (int j = 0; j < nbCptForClient(codeCli); j++) {
                    int iCodeCptReturn = Integer.parseInt((String) c.theComptes.elementAt(j));
                    if (iCodeCptReturn == iCodeCpt) {
                        bTrouve = false;
                    }
                }
            }
        }
        return bTrouve;
    }

    public void addCompteToClient(int codeCpt, int codeCli) {
        for (int i = 0; i < theClients.size(); i++) {
            Clients c = (Clients) theClients.elementAt(i);
            if (c.getCodeClient() == codeCli) {
                c.addCompteToClient(codeCpt);
            }
        }
    }

    public void removeCompteFromClient(int codeCpt) {
        for (int i = 0; i < theClients.size(); i++) {
            Clients c = (Clients) theClients.elementAt(i);
            for (int j = 0; j < c.theComptes.size(); j++) {
                int cpt = Integer.parseInt((String) c.theComptes.elementAt(j));
                if (cpt == codeCpt) {
                    c.theComptes.remove(j);
                }
            }
        }
    }

    public String afficher(int iIndice) {
        if (iIndice > -1 && iIndice < theClients.size()) {
            Clients c = (Clients) theClients.elementAt(iIndice);
            return c.afficher();
        } else {
            System.out.println("Erreur d'indice : " + iIndice);
            return "";
        }
    }

    public int getCode(int iIndice) {
        if (iIndice > -1 && iIndice < theClients.size()) {
            Clients c = (Clients) theClients.elementAt(iIndice);
            return c.getCodeClient();
        } else {
            System.out.println("Erreur d'indice : " + iIndice);
            return -1;
        }
    }

    public boolean supprimerClient(int iIndice) {
        if (iIndice > -1 && iIndice < theClients.size()) {
            theClients.remove(iIndice);
            return true;
        } else {
            System.out.println("Erreur d'indice : " + iIndice);
            return false;
        }
    }

    public int size() {
        return theClients.size();
    }

    public int nbCptForClient(int CodeClient) {
        int iSize = 0;
        for (int i = 0; i < theClients.size(); i++) {
            Clients c = (Clients) theClients.elementAt(i);
            if (c.getCodeClient() == CodeClient) {
                iSize = c.theComptes.size();
            }
        }
        return iSize;
    }

    public int cptClient(int CodeClient, int iIndice) {
        int iCptCli = -1;
        for (int i = 0; i < theClients.size(); i++) {
            Clients c = (Clients) theClients.elementAt(i);
            if (c.getCodeClient() == CodeClient) {
                String sCptCli = (String) c.theComptes.elementAt(iIndice);
                iCptCli = Integer.parseInt(sCptCli);
            }
        }
        return iCptCli;
    }

}
