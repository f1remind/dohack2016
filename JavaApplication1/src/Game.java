
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
public class Game {
    private ArrayList<CommunicationThread> participants;
    private int id;
    private boolean gameOver;
    
    public Game(int i, ArrayList<CommunicationThread> participants){
        id = i;
        this.participants = participants;
    }
    
    public void startGame(int gameId){
        if(gameId==0){
            int[][] board = new int[3][3];
            gameOver = false;
            CommunicationThread p1 = participants.get(0);
            CommunicationThread p2 = participants.get(1);
            gameInit(p1,p2);
            while(!gameOver){
                doTurn(p1, p2, board, 1);
                if(!gameOver){
                    doTurn(p2, p1, board, 2);
                }
            }
            
        }
    }
    
    private void gameInit(CommunicationThread p1, CommunicationThread p2){
        String p1Name = p1.getUserName();
        String p2Name = p2.getUserName();
        p1.sendMessage("READY");
        p2.sendMessage("READY");
        p1.sendMessage(p2Name+";"+p1Name);
        p2.sendMessage(p1Name+";"+p2Name);
        
        
    }
    
    private String askForTurn(CommunicationThread p){        
        String response = p.getResponse();        
        return response;
    }
    
    private boolean checkTurn(String response, int[][] board, int signNum){
        boolean turnOk = false;
        String[] coords = response.split(";");
        int x = Integer.parseInt(coords[0]);
        int y = Integer.parseInt(coords[1]);
        if(board[x][y]==0){
            board[x][y]= signNum;
            turnOk = true;
        }
        return turnOk;
    }
    
    private void doTurn(CommunicationThread p1, CommunicationThread p2, int[][] board, int playerID){
        String response = "";
        int trys = 1;
        p2.sendMessage("NOTYOURTURN");
        p1.sendMessage("YOURTURN");
        response = askForTurn(p1);
        while(response.equals("") && trys < 3){
            response = askForTurn(p1);
            trys++;
        }
        if(trys == 3){
            System.out.println("No Response ---- HIER SOLLTE ETWAS PASSIEREN ----- TUT ES ABER NICHT :)");                
        }
        else{
            if(!checkTurn(response, board, playerID)){
                response = askForTurn(p1);
                while(response.equals("") && trys < 3){
                    response = askForTurn(p1);
                    trys++;
                }
                if(!checkTurn(response, board, playerID)){
                    response = "-1";
                }
            }
            else{
                p1.sendMessage(response);
                p2.sendMessage(response);
            }
        }
        if(isGameOver(board, playerID)){
            p1.sendMessage("WIN;"+p1.getName());
        }
        
    }
    
    private boolean isGameOver(int[][] board, int pnum){
        boolean isOver = false;
        //Schau ob waagerecht ein SIeg erzeilt wurde
        for(int i = 0; i<board.length;i++){
            for(int j = 0; j<board.length;j++){
                if(board[i][j] != pnum){
                    i++;
                }
                else if(j == 3){
                    isOver = true;
                }
            }
        }
        //Schau ob Vertikal ein SIegt erzeilt wurde
        for(int i = 0; i<board.length;i++){
            for(int j = 0; j<board.length;j++){
                if(board[j][i] != pnum){
                    i++;
                }
                else if(j == 3){
                    isOver = true;
                }
            }
        }
        if(board[0][0] == pnum && board[1][1] == pnum && board[2][2] == pnum){
            isOver = true;
        }
        if(board[0][2] == pnum && board[1][1] == pnum && board[2][0] == pnum){
            isOver = true;
        }
        return isOver;
    }
}
