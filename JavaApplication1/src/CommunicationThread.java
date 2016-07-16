
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
    private Socket socket;
    public CommunicationThread(Socket socket){
        super();
        this.socket = socket;
    }
    
    public void run(){
        
    }
}
