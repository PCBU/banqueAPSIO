//Source file: c:\\Mes documents\\Lecomte - Barbieri\\Projet UML-Java\\Application\\CreerClient.java

package Application;

import Metier.Clients;

public class CreerClient {
    public Clients theClients;

    /**
     * @roseuid 3D24647900AA
     */
    public CreerClient(String nomCli, String adrCli, int codeCli) {
        theClients = new Clients(nomCli, adrCli, codeCli);
    }
}
