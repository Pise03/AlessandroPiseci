import java.io.*;
import java.net.*;

public class Client {
    String nomeServer = "localhost";
    int portaServer = 6789;
    Socket miosocket;
    BufferedReader tastiera;
    String stringaUtente;
    String stringaRicevutaDalServer;
    DataOutputStream outVersoServer;
    BufferedReader inDalServer;

    int controllo;
    
    
    /** 
     * @param ..."
     * @return Socket
     */
    public Socket connetti() { //collegamento al server
        System.out.println("Client partito in esecuzione ...");
        try {
            //input da tastiera
            tastiera = new BufferedReader(new InputStreamReader(System.in));

            miosocket = new Socket(nomeServer, portaServer);

            outVersoServer = new DataOutputStream(miosocket.getOutputStream());

            inDalServer = new BufferedReader(new InputStreamReader(miosocket.getInputStream()));

            System.out.println("Benvenuto utente, devi indovinare un numero generato dal server tra 1 e 100");

        } catch (UnknownHostException e) {
            System.err.println("Host sconosciuto");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la connessione");
            System.exit(1);
        }
        return miosocket;
    }

    public void comunica() {
        for (;;) {
            try {
                System.out.println("Utente, indovina il numero: ");
                stringaUtente = tastiera.readLine();
                controllo = Integer.parseInt(stringaUtente);

                if(controllo <= 100 && controllo >=1){
                    // la spedisco al server
                    System.out.println("Invio la risposta al server e attendo ...");
                    outVersoServer.writeBytes(stringaUtente + '\n');

                    // leggo la risposta del server
                    stringaRicevutaDalServer = inDalServer.readLine();
                    System.out.println("Server dice: " + stringaRicevutaDalServer);

                    if (stringaRicevutaDalServer.equals("Bravo! hai indovinato! Chiusura socket.")) { //se il numero è stato indovinato chiudo la connessione con il server
                        System.out.println("CLIENT: termina elaborazione e chiude connessione");
                        miosocket.close();
                        break;
                    }
                }else
                    System.out.println("Prego, inserire un numero tra 1 e 100.");

            } catch (Exception e) {
                System.out.println("Errore, impossibile inserire lettere, solo numeri.");
            }
        }
    }

    
    /** 
     * @param args
     */
    public static void main(String[] args) {
        Client client = new Client();
        client.connetti();
        client.comunica();
    }
}
