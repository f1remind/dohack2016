
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
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
public class CommunicationThread extends Thread {
    private final Socket socket;
    public CommunicationThread(Socket socket){
        super();
        this.socket = socket;
    }
    @Override
    public void run(){
        OutputStream os;
        PrintWriter pw = null;
        BufferedReader br;
        
        String userName = "";
        System.out.println("Thread wurde gestartet");
        try{
            os = socket.getOutputStream();
            pw = new PrintWriter(os, true);
            pw.println("What's you name?");
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String str = br.readLine();
            pw.println("Hello, " + str);
            userName = str;
            while (socket.isConnected()) { 
                str = br.readLine();
                System.out.println(str);



    //                System.out.println("Just said hello to:" + str);
            }
        }
        catch(IOException ioe){
            ioe.printStackTrace();
            if(pw != null){
                pw.close();
            }
            try{
                socket.close();
            }
            catch(IOException ioe2){
                ioe2.printStackTrace();
            }
        }
        System.out.println("Es besteht keiner Verbindung mehr");
        
         
    }
}
