//Source file: c:\\Mes documents\\Lecomte - Barbieri\\Projet UML-Java\\Metier\\Clients.java

package Metier;

import java.util.*;

public class Clients {
    private String nomClient;
    private String adresseClient;
    private int codeClient;
    public Vector theComptes;

    /**
     * @roseuid 3D24617D032A
     */
    public Clients(String nomCli, String adrCli, int codeCli) {
        nomClient = nomCli;
        adresseClient = adrCli;
        codeClient = codeCli;
        theComptes = new Vector();
    }

    /**
     * @return Void
     * @roseuid 3D24608203CB
     */
    public String afficher() {

        return codeClient + "  |  " + nomClient + "  |  " + adresseClient;
    }

    public void addCompteToClient(int iCodeCpt) {
        theComptes.add(Integer.toString(iCodeCpt));
    }

    public int getCodeClient() {
        return codeClient;
    }

    public String getNom() {
        return nomClient;
    }

    public String getAdresse() {
        return adresseClient;
    }
}
