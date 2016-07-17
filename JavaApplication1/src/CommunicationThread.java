
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
    private final long version;
    private OutputStream os;
    private PrintWriter pw = null;
    private BufferedReader br;
    boolean gameNotOver;
    private GameController gc;
    String userName = "";
    
    public CommunicationThread(Socket socket, long version){
        super();
        this.socket = socket;
        this.version = version;
        try{
            //Aufbau der Streams und Writer
            os = socket.getOutputStream();
            pw = new PrintWriter(os, false);
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch(IOException ioe){
            System.out.println("Fehler beim erstellen der Printer/Writer");
            try{
                socket.close();
            }
            catch(IOException ioe2){
               
            }
        }
        gc = Server.getGc();
    }
    @Override
    public void run(){
        try{
            pw.println(version);
            pw.flush();
            while(!br.ready()){}
            String str = br.readLine();        
            userName = str;
System.out.println("UserName: "+userName);            
            while (socket.isConnected() && !socket.isClosed()) {
                //Erstellen der Liste mit den ID's für die Spiele
                int numOfGames = gc.getNumOfGames();
                String listOfGameIds = "";
                for(int i = 0; i<=numOfGames;i++){
                    if(i == numOfGames){
                        listOfGameIds += i;
                    }
                    else{
                        listOfGameIds += i+";";
                    }
                }
                pw.println(listOfGameIds); 
                pw.flush();
                while(!br.ready()){}
                str = "";
                if(br.ready()){
                    while(str.equals("")){
                        str = br.readLine();
                    }
System.out.println("Gameauswahl: "+str);
                    int selectedGame = Integer.parseInt(str);
                    joinGame(selectedGame);
                    gameNotOver = true;
                    while(gameNotOver){
                        try{
                            this.sleep(1000);
                        }
                        catch(InterruptedException ie){

                        }
                    }
                }
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
    
    private void joinGame(int game){
        gc.joinGame(game, this);
    }
    public void setGameNotOver(boolean isGameNotOver){
        gameNotOver = isGameNotOver;
    }
    
    public String getUserName(){
        return userName;
    }
    
    public void sendMessage(String message){
        pw.println(message);
        pw.flush();
    }
    
    public String getResponse(){
        String response = "";
        try{
            while(!br.ready()){}
            response = br.readLine();
        }
        catch(IOException ioe){
        }
        return response;
    }
    
    public boolean checkTillConnected(){
        boolean isConnected;
        isConnected = socket.isConnected();
        if(isConnected){
            isConnected = !socket.isClosed();
        }
        return isConnected;
    }
    
    public void terminateThread(){
        gameNotOver = false;
    }
}
