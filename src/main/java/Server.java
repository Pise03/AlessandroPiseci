import java.net.*;
import java.io.*;

public class Server{

    String stringaRicevuta = null;
    String stringaModificata = null;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;


    public void start() {
    try {
        ServerSocket serverSocket = new ServerSocket(6789);
        System.out.println("1 Server in attesa ...");
        Socket client = serverSocket.accept();
        System.out.println("3 Server client " + client);
        //inizializzo il buffered e output stream
        
        inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        outVersoClient = new DataOutputStream(client.getOutputStream());

        for(;;){
            stringaRicevuta = inDalClient.readLine();
            if(stringaRicevuta.equals("STOP")) { //controllo sulla stringa STOP
                outVersoClient.writeBytes(stringaRicevuta + "(=>server in chiusura ...)" + '\n');
                System.out.println("6 Echo sul server in chiusura :" + stringaRicevuta);
                break;
            } else {
                outVersoClient.writeBytes(stringaRicevuta + " (ricevuta e ritrasmessa)" + '\n');
                System.out.println("6 Echo sul server :" + stringaRicevuta);
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

    public static void main(String[] args) {
        Server tcpServer = new Server();
        tcpServer.start();
    }
}
