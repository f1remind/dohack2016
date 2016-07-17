
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
public class GameController {
    private static ArrayList<ArrayList<CommunicationThread>> gamesList;
    private static ArrayList<Game> runngingGames;
    private static int numberOfGames;
    
    public GameController(int games){
        int numberOfGames = games;
        gamesList = new ArrayList<ArrayList<CommunicationThread>>(games+1);
        System.out.println("Länge der gameList "+gamesList.size());
        for(int i =0; i< games+1;i++){
            gamesList.add(i,new ArrayList<>(2));
            
            System.out.println("Länge der gameList "+gamesList.get(i).size());
        }
        runngingGames = new ArrayList<>();
    }
    
    public void joinGame(int id, CommunicationThread ct){
        if(gamesList.get(id).isEmpty()){
            gamesList.get(id).add(ct);
            ct.sendMessage("WAIT");
        }
        else{
            ArrayList<CommunicationThread> ctl = new ArrayList<CommunicationThread>(2); 
            ctl.add(gamesList.get(id).get(0));
            gamesList.get(id).remove(0);
            ctl.add(ct);
            Game game = new Game(id, ctl);
            runngingGames.add(game);
            game.startGame(id);
        }        
    }
    
    public static int getNumOfGames(){
        return numberOfGames;
    }
    
}
