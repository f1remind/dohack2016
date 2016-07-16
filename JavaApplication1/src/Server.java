
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

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
        if(args.length>0){
            port = Integer.parseInt(args[0]);
        }
        try{
            ServerSocket ss = new ServerSocket(port);
            while (true) {
                Socket socket = ss.accept();
                OutputStream os = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(os, true);
                pw.println("What's you name?");

                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String str = br.readLine();

                pw.println("Hello, " + str);
                pw.close();
                socket.close(); 

                System.out.println("Just said hello to:" + str);
               // CommunicationThread thread = new CommunicationThread(socket);
               // thread.run();
            }
        }
        catch(IOException ioe){
            System.out.println("Can't setup Server Socket");
            ioe.printStackTrace();
        }
    }
    
} 
