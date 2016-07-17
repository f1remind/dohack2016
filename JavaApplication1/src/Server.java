
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

    private static GameController gc;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int games = 0;
        int port = 4586;
        
        if(args.length==1){
            int temp = Integer.parseInt(args[0]);
            if(temp>1000){
                port = temp; 
            }
            else{
                System.out.println("Bitte gib als Parameter einen Port > 1000 an.");
                System.exit(1);
            }
        }
        if(args.length>1){
            port = Integer.parseInt(args[0]);
            games = Integer.parseInt(args[1]);
        }
        gc = new GameController(games);   
        
        long v = 201607161730L;
        ArrayList<CommunicationThread> threads = new ArrayList<CommunicationThread>();
        if(args.length==1){
            port = Integer.parseInt(args[0]);
        }
        
        try{
            ServerSocket ss = new ServerSocket(port);
            System.out.println("Server wurde aufgesetzt");
             while (true) {
                Socket socket = ss.accept();
                CommunicationThread newThread = new CommunicationThread(socket, v);
                threads.add(newThread);
                newThread.start();
                System.out.println("Server wartet auf neue Verbindung");                
             }
        }
        catch(IOException ioe){
            System.out.println("Can't setup Server Socket");
            ioe.printStackTrace();
        }
        finally{
            System.out.println("Server wure herunter gefahren");
        }
    }
    
    public static GameController getGc(){
        return gc;
    }
    
    
}
 