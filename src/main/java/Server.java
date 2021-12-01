import java.net.*;
import java.io.*;

public class Server{

    String stringaRicevuta = null;
    int stringaConvertita;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;
    int guess = new java.util.Random().nextInt(100) + 1;


    public void start() throws Exception{
    try {
        ServerSocket serverSocket = new ServerSocket(6789);
        System.out.println("1 Server in attesa ...");
        Socket client = serverSocket.accept();
        System.out.println("3 Server client " + client);
        //inizializzo il buffered e output stream
        
        inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        outVersoClient = new DataOutputStream(client.getOutputStream());

        System.out.println("numero da indovinare: " + guess); //per debug

        for(;;){
            stringaRicevuta = inDalClient.readLine();
            stringaConvertita = Integer.parseInt(stringaRicevuta); //trasformo la stringa in un int

            if(stringaConvertita == guess) { //controllo sulla stringa STOP
                outVersoClient.writeBytes("Bravo! hai indovinato! Chiusura socket." + '\n');
                System.out.println("6 Echo sul server in chiusura :" + stringaRicevuta);
                break;
            }else if(stringaConvertita < guess){
                outVersoClient.writeBytes("il numero selezionato e' troppo piccolo" + '\n');
                
            }else if(stringaConvertita > guess){
                outVersoClient.writeBytes("il numero selezionato e' troppo grande" + '\n');
            }
        }
        outVersoClient.close();
        inDalClient.close();
        System.out.println("9 chiusura client" + client);
        client.close();        
    } catch (Exception e) {
        System.out.println(e.getMessage());
        System.out.println("Messaggio non desiderato!");
    }
}

    public static void main(String[] args) throws Exception{
        Server tcpServer = new Server();
        tcpServer.start();
    }
}
