import java.net.*;
import java.io.*;

public class Server{

    String stringaRicevuta = null;
    int stringaConvertita;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;
    int guess = new java.util.Random().nextInt(100) + 1; //numero da indovianre


    
    /** 
     * @throws Exception
     */
    public void start() throws Exception{
    try {
        ServerSocket serverSocket = new ServerSocket(6789);
        System.out.println("Server in attesa ...");
        Socket client = serverSocket.accept();
        System.out.println("Server client connesso: " + client);
        
        inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        outVersoClient = new DataOutputStream(client.getOutputStream());

        System.out.println("numero da indovinare: " + guess); //per debug, il client non vede il numero da indovinare

        for(;;){
            stringaRicevuta = inDalClient.readLine(); //ricevo il numero inviato dal client
            stringaConvertita = Integer.parseInt(stringaRicevuta); //trasformo la stringa in un int

            if(stringaConvertita == guess) { //if se il client ha indovinato
                outVersoClient.writeBytes("Bravo! hai indovinato! Chiusura socket." + '\n');
                System.out.println("Chiudo il server ed il client");
                break; //esco dal ciclo infinto
            }
            else if(stringaConvertita < guess){ //condizione valida se il numero inviato dal client è minore di quello da indovinare
                outVersoClient.writeBytes("il numero selezionato e' troppo piccolo" + '\n');                
            }
            else if(stringaConvertita > guess){ //condizione valida se il numero inviato dal client è maggiore di quello da indovinare
                outVersoClient.writeBytes("il numero selezionato e' troppo grande" + '\n');
            }
        }
        //chiudo la comunicazione
        outVersoClient.close();
        inDalClient.close();
        System.out.println("Chiusura client" + client); 
        client.close();

    } catch (Exception e) {
        System.out.println(e.getMessage());
        System.out.println("Messaggio non desiderato!");
    }
}

    
    /** 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        Server tcpServer = new Server();
        tcpServer.start();
    }
}
