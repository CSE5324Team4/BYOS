package com.uta.byos;

import java.util.*;
import android.content.res.Resources;
import com.uta.byos.Deck.DeckType;

public class Game {
	
    private ArrayList<Deck> mSourceDecks = new ArrayList<Deck>();  //Location on the tableau is contained by the Deck class
    private ArrayList<Deck> mTargetDecks = new ArrayList<Deck>();
    private int deckCount;
    private LinkedList sdLimits = new LinkedList(); 
    private Deck mWasteDeck;
    private Deck mWasteDeck2;
    
    //Build rules followed by play rules
    private String sourceRules;
    private String targetRules;
    //private int cardScale;
    
    public Game(){}
    
    /*
     * Rules formatting: Initial iteration will support flexibility for games
     * that do not belong in the pyramid family.  Provide enough flexibility
     * so testing can recreate Klondike, Freecell, and Spider.
     */

    /*
     * For now there is no constructor as the user will simply start out with
     * a blank template.  After iteration 2 is complete the constructor will
     * read from a file create the Game object from there.
     */
    
    public Game(int cardScale){
    	mWasteDeck = new Deck(Deck.DeckType.EWaste1, 0, 0, cardScale, (int) (1.5*cardScale));
    }
    
    private int getDeckCount(){
    	return deckCount;
    }
    
    private void setDeckCount(int in){
    	deckCount = in;
    }
    
    public void addSourceDeck(int x, int y, int cardScale){
    	Deck deck = new Deck(Deck.DeckType.ESource, x, y, cardScale,  (int) (cardScale*1.5));
    	mSourceDecks.add(deck);
    	sdLimits.add(-1);
    }
    public void removeSourceDeck(int index){
    	mSourceDecks.remove(index);
    	sdLimits.remove(index);
    }
    public void addTargetDeck(int x, int y, int cardScale){
    	Deck deck = new Deck(Deck.DeckType.ETarget, x, y, cardScale,  (int) (cardScale*1.5));
    	mTargetDecks.add(deck);
    }
    public void removeTargetDeck(int index){
    	mTargetDecks.remove(index);
    }
    
    public void setSourceRules(String in){
    	sourceRules = in;
    }
    
    public void setTargetRules(String in){
    	targetRules = in;
    }
}