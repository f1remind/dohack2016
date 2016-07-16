
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Yann
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int port = 4586;
        ArrayList<CommunicationThread> threads = new ArrayList<CommunicationThread>();
        if(args.length>0){
            port = Integer.parseInt(args[0]);
        }
        try{
            ServerSocket ss = new ServerSocket(port);
            System.out.println("Server wurde aufgesetzt");
             while (true) {
                Socket socket = ss.accept();
                CommunicationThread newThread = new CommunicationThread(socket);
                threads.add(newThread);
                newThread.run();
                 System.out.println("Server wartet auf neue Verbindung");
                
             }
        }
        catch(IOException ioe){
            System.out.println("Can't setup Server Socket");
            ioe.printStackTrace();
        }
        catch(Exception e){
            System.out.println("Fehler");
            e.printStackTrace();
        }
        finally{
            System.out.println("Server wure herunter gefahren");
        }
    }
    
}
