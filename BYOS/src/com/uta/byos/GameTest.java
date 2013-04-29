package com.uta.byos;

import java.util.Random;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * This class is used for actual gameplay (or testing)
 * @author Matthew Waller
 *
 */

public class GameTest extends View {

	private Paint mCanvasPaint;
	private ArrayList<Card> mCards = new ArrayList<Card>();
	/** The dimensions GameTest instance resides within  */
	private Rect mScreenSize = new Rect();
	/** The dimensions for each card*/
	private Rect mCardSize = new Rect();
	/** The decks */
	private ArrayList<Deck> mDecks = new ArrayList<Deck>();
	private boolean mUseCache;
	/** Whether or not the game uses foundations (e.g. Klondike) */
	private boolean foundPres = false;
	private Bitmap mCacheBitmap;
	/** The card being moved about by the user */
	private Card mActiveCard;
	private String tableBuild;
	private int cardXCap;
	private int cardYCap;
	/** The maximum amount of cards that can be moved at any time */
	private int limit;
	/** The rule book used during gameplay */
	private String ruleBook = "b1ff0";

	public GameTest(Context context) {
		super(context);

		mCanvasPaint = new Paint();
		mCanvasPaint.setColor(0xFF228B22); // Green background
		mCanvasPaint.setAntiAlias(false);
		mCanvasPaint.setFilterBitmap(false);
		setClickable(true);

	}

	public GameTest(Context context, AttributeSet attrs){
		super(context, attrs);

		mCanvasPaint = new Paint();
		mCanvasPaint.setColor(0xFF228B22); // Green background
		mCanvasPaint.setAntiAlias(false);
		mCanvasPaint.setFilterBitmap(false);
		setClickable(true);
	}

	public GameTest(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);

		mCanvasPaint = new Paint();
		mCanvasPaint.setColor(0xFF228B22); // Green background
		mCanvasPaint.setAntiAlias(false);
		mCanvasPaint.setFilterBitmap(false);
		setClickable(true);

	}

	//    @Override
	//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
	//    	mCardSize.set(0, 0, widthMeasureSpec, (int) (widthMeasureSpec * 1.5));
	//    }



	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		// Store current screen size
		mScreenSize.set(0, 0, w, h);

		// Calculate card and decks sizes and positions
		int cw = w / 11;
		mCardSize.set(0, 0, cw, (int) (cw * 1.5));
		
		for(int i = 0; i < Integer.parseInt(tableBuild.split(":")[0]); i++){
			mCards.add(new Card(1, Card.CardLand.EClub, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.club1));
			mCards.add(new Card(2, Card.CardLand.EClub, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.club2));
			mCards.add(new Card(3, Card.CardLand.EClub, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.club3));
			mCards.add(new Card(4, Card.CardLand.EClub, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.club4));
			mCards.add(new Card(5, Card.CardLand.EClub, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.club5));
			mCards.add(new Card(6, Card.CardLand.EClub, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.club6));
			mCards.add(new Card(7, Card.CardLand.EClub, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.club7));
			mCards.add(new Card(8, Card.CardLand.EClub, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.club8));
			mCards.add(new Card(9, Card.CardLand.EClub, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.club9));
			mCards.add(new Card(10, Card.CardLand.EClub, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.club10));
			mCards.add(new Card(11, Card.CardLand.EClub, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.club11));
			mCards.add(new Card(12, Card.CardLand.EClub, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.club12));
			mCards.add(new Card(13, Card.CardLand.EClub, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.club13));

			mCards.add(new Card(1, Card.CardLand.EDiamond, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.diamond1));
			mCards.add(new Card(2, Card.CardLand.EDiamond, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.diamond2));
			mCards.add(new Card(3, Card.CardLand.EDiamond, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.diamond3));
			mCards.add(new Card(4, Card.CardLand.EDiamond, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.diamond4));
			mCards.add(new Card(5, Card.CardLand.EDiamond, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.diamond5));
			mCards.add(new Card(6, Card.CardLand.EDiamond, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.diamond6));
			mCards.add(new Card(7, Card.CardLand.EDiamond, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.diamond7));
			mCards.add(new Card(8, Card.CardLand.EDiamond, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.diamond8));
			mCards.add(new Card(9, Card.CardLand.EDiamond, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.diamond9));
			mCards.add(new Card(10, Card.CardLand.EDiamond, 0, getResources(), 0,
					0, mCardSize.width(), mCardSize.height(), R.raw.diamond10));
			mCards.add(new Card(11, Card.CardLand.EDiamond, 0, getResources(), 0,
					0, mCardSize.width(), mCardSize.height(), R.raw.diamond11));
			mCards.add(new Card(12, Card.CardLand.EDiamond, 0, getResources(), 0,
					0, mCardSize.width(), mCardSize.height(), R.raw.diamond12));
			mCards.add(new Card(13, Card.CardLand.EDiamond, 0, getResources(), 0,
					0, mCardSize.width(), mCardSize.height(), R.raw.diamond13));

			mCards.add(new Card(1, Card.CardLand.EHeart, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.heart1));
			mCards.add(new Card(2, Card.CardLand.EHeart, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.heart2));
			mCards.add(new Card(3, Card.CardLand.EHeart, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.heart3));
			mCards.add(new Card(4, Card.CardLand.EHeart, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.heart4));
			mCards.add(new Card(5, Card.CardLand.EHeart, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.heart5));
			mCards.add(new Card(6, Card.CardLand.EHeart, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.heart6));
			mCards.add(new Card(7, Card.CardLand.EHeart, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.heart7));
			mCards.add(new Card(8, Card.CardLand.EHeart, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.heart8));
			mCards.add(new Card(9, Card.CardLand.EHeart, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.heart9));
			mCards.add(new Card(10, Card.CardLand.EHeart, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.heart10));
			mCards.add(new Card(11, Card.CardLand.EHeart, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.heart11));
			mCards.add(new Card(12, Card.CardLand.EHeart, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.heart12));
			mCards.add(new Card(13, Card.CardLand.EHeart, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.heart13));

			mCards.add(new Card(1, Card.CardLand.ESpade, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.spade1));
			mCards.add(new Card(2, Card.CardLand.ESpade, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.spade2));
			mCards.add(new Card(3, Card.CardLand.ESpade, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.spade3));
			mCards.add(new Card(4, Card.CardLand.ESpade, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.spade4));
			mCards.add(new Card(5, Card.CardLand.ESpade, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.spade5));
			mCards.add(new Card(6, Card.CardLand.ESpade, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.spade6));
			mCards.add(new Card(7, Card.CardLand.ESpade, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.spade7));
			mCards.add(new Card(8, Card.CardLand.ESpade, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.spade8));
			mCards.add(new Card(9, Card.CardLand.ESpade, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.spade9));
			mCards.add(new Card(10, Card.CardLand.ESpade, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.spade10));
			mCards.add(new Card(11, Card.CardLand.ESpade, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.spade11));
			mCards.add(new Card(12, Card.CardLand.ESpade, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.spade12));
			mCards.add(new Card(13, Card.CardLand.ESpade, 0, getResources(), 0, 0,
					mCardSize.width(), mCardSize.height(), R.raw.spade13));}
		Random deal = new Random();
		String[] tok = tableBuild.split(":")[1].split(";");
		Deck.DeckType set; Deck tar; Card c; int limit; boolean waste;
		for(String i : tok){
			String[] p = i.split(",");
			switch(p[0].charAt(0)){
			case 'S':
				waste = true;
				set = Deck.DeckType.EWaste1;
				break;
			case 'W':
				waste = false;
				set = Deck.DeckType.EWaste2;
				break;
			case 'R':
				waste = false;
				set = Deck.DeckType.ESource;
				break;
			case 'F':
				waste = false;
				foundPres = true;
				set = Deck.DeckType.ETarget;
				break;
			default:
				throw new ArithmeticException("Not a valid deck type");
			}
			tar = new Deck(set, Integer.parseInt(p[1]), Integer.parseInt(p[2]),mCardSize.width(),mCardSize.height());
			if(set == Deck.DeckType.ESource){
				for(int l=0; l < p[3].length(); l++){
					c = mCards.remove(deal.nextInt(mCards.size()));
					if(p[3].charAt(l) == 'U'){
						c.mTurned = true;
					}else{
						c.mTurned = false;
					}
					tar.addCard(c, false);
				}
				tar.revealTopCard();}
			else{			
				limit = Integer.parseInt(p[3]) - ((set == Deck.DeckType.ESource) ? 1 : 0);
				for(int j = 0; j < limit; j++){
					c = mCards.remove(deal.nextInt(mCards.size()));
					tar.addCard(c, waste);}}
			mDecks.add(tar);
		}
	}
	
	/**
	 * Reads input sent by Intent to TestActivity and finally to this class
	 * @param	layout	A string containing the coordinates and types for each deck in the game
	 * @param	inputR	A string that creates the ruleBook for the app to interpret
	 * @param	lim		An integer that limits the number of cards that can be moved
	 * @see				Intent
	 * @see				GameBuilder#toString()
	 */

	public void constructFromInput(String layout, String inputR, int lim){
		tableBuild = layout;
		ruleBook = inputR;
		limit = lim;
	}



	@Override
	public void onDraw(Canvas canvas) {

		// Cache?
		if (mUseCache) {
			// Yes
			canvas.drawBitmap(mCacheBitmap, 0, 0, null);
		} else {
			// No
			mCanvasPaint.setStyle(Style.FILL);
			canvas.drawRect(mScreenSize, mCanvasPaint);
			// Draw decks
			for (Deck deck : mDecks) {
				deck.doDraw(canvas);
			}
		}

		// Draw active card last
		if (mActiveCard != null) {
			mActiveCard.doDraw(canvas);
		}
	}
	/**
	 * Based on Tero's original algorithm
	 * @see TableauView enableCache
	 */
	private void enableCache(boolean enabled) {
		if (enabled && !mUseCache) {
			mActiveCard.setVisible(false);
			setDrawingCacheEnabled(true);
			//buildDrawingCache();
			mCacheBitmap = Bitmap.createBitmap(getDrawingCache());
			mActiveCard.setVisible(true);
		} else if (!enabled && mUseCache) {
			setDrawingCacheEnabled(false);
			mCacheBitmap = null;
		}
		mUseCache = enabled;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		if (action == MotionEvent.ACTION_DOWN) {
			int x = (int) event.getX();
			int y = (int) event.getY();
			mActiveCard = null;

			// Search card under source decks
			Deck deck = getDeckUnderTouch(x, y);
			if (deck != null){
				mActiveCard = deck.getCardFromPos(x, y);}

			

			// Card founds?
			if (mActiveCard != null) {
				if (!cardIsMoveable(mActiveCard, limit))
					mActiveCard = null;
				else{
					cardXCap = x - mActiveCard.mRect.left;
					cardYCap = y - mActiveCard.mRect.top;
					mActiveCard.storePosition();
					enableCache(true);
					invalidate();}
			}

			// Log.v("", "down");
			invalidate();
			return true;

		} else if (action == MotionEvent.ACTION_MOVE) {
			int x = (int) event.getX();
			int y = (int) event.getY();
			if (mActiveCard != null) {
				if (mActiveCard.mOwnerDeck.mDeckType != Deck.DeckType.EWaste1) {
					mActiveCard.setPos(x - cardXCap, y - cardYCap,true);
					invalidate();
				}
			}
			return true;

		} else if (action == MotionEvent.ACTION_UP) {
			int x = (int) event.getX();
			int y = (int) event.getY();

			enableCache(false);
			handleCardMove(x, y);
			invalidate();
			return true;
		}
		return false;

	}
	
	/**
	 * Checks to see whether the card is movable
	 * @param	card	The card and those on top of it the user is trying to move
	 * @param	limit2	The limit to how many cards can be moved
	 * @return			Whether or not the card(s) can be moved
	 * @see				ruleBook
	 */

	private boolean cardIsMoveable(Card card, int limit2) {
		// TODO Auto-generated method stubint tV = toCard.mCardValue;
		int cV = card.mCardValue;
		int val = 0;
		boolean mov;
		if(limit2 != -1 && limit2 == 0)
			return false;
		if(card.mParentCard == null)
			return true;
		else if(card.mTurned){
			int pV = card.mParentCard.mCardValue;
			switch(ruleBook.charAt(4)){
			case '1':
			case '3':
				val = pV+Integer.parseInt(ruleBook.substring(1, 2), 13); break;
			case '0':
			case '2':
				val = pV-Integer.parseInt(ruleBook.substring(1, 2), 13); break;
			default:
				val = (Math.abs(pV - cV) == Integer.parseInt(ruleBook.substring(1,2), 13)) ? cV:val;
			}
			switch(ruleBook.charAt(3)){
			case 't':
				mov = (cV == val%13); break; //Wrappable
			case 'f':
				mov = (cV == val); break; //Not wrappable
			default:
				mov = false;	
			}
			if(mov)
				switch (ruleBook.charAt(0)){
				case '0':  //Only stacks with the same suit are movable
				case '2':
				case 'b':
					if(card.mCardLand == card.mParentCard.mCardLand)
						return cardIsMoveable(card.mParentCard, limit2-1);
					else
						return false;
				case '1': //Only stacks with the same color are movable
				case '5':
				case '9':
					if(card.mBlack == card.mParentCard.mBlack)
						return cardIsMoveable(card.mParentCard, limit2-1);
					else
						return false;
				case '3': //Only stacks with alternating colors are movable
				case '6':
				case 'a':
					if(card.mBlack != card.mParentCard.mBlack)
						return cardIsMoveable(card.mParentCard, limit2-1);
					else
						return false;
				case '4':
				case '8':
					if(card.mCardLand != card.mParentCard.mCardLand)
						return cardIsMoveable(card.mParentCard, limit2-1);
					else
						return false;
				case '7':
					return true;
				}}
		return false;
	}
	
	/**
	 * Finds the deck under the user's tap
	 * @param x, y	Coordinates of the user's tap
	 * @return		The deck under the user's tap
	 */

	private Deck getDeckUnderTouch(int x, int y) {
		for (Deck deck : mDecks) 
			if (deck.isUnderTouch(x, y))
				return deck;
		return null;
	}
	
	/**
	 * Based on tero's original algorithm
	 * @see TableauView#handleCardMove
	 */

	private void handleCardMove(int x, int y) {
		Deck fromDeck = null;
		Deck toDeck = getDeckUnderTouch(x, y);
		boolean topOfOtherCards = true;
		boolean flag = false;

		if (mActiveCard != null) {
			fromDeck = mActiveCard.mOwnerDeck;
			if (fromDeck.mDeckType == Deck.DeckType.EWaste1) {
				for(Deck to : mDecks)
					if(to.mDeckType == Deck.DeckType.EWaste2){
						to.addCard(fromDeck, mActiveCard, topOfOtherCards);
						flag = true;
						break;}
				if(!flag)
					dealToTableau(fromDeck);	
			} else {
				// Handle all other card move
				if (toDeck!=null) {
					if (toDeck.mDeckType == Deck.DeckType.ESource) {
						topOfOtherCards = false; // Drawn exactly top of other cards in the deck
					}
					// Accept card move or not?
					if (acceptCardMove(fromDeck, toDeck, mActiveCard)) {
						toDeck.addCard(fromDeck, mActiveCard, topOfOtherCards);
						if(!foundPres)
							removeIfComplete(mActiveCard);
						fromDeck.revealTopCard();
					} else {
						mActiveCard.cancelMove(true);
					}
				} else {
					mActiveCard.cancelMove(true);
				}
			}
		} else if (toDeck!=null && toDeck.mDeckType==Deck.DeckType.EWaste1) {
			// Handle Waste1 to/back Waste2 card moves
			if (toDeck.mCards.size()==0) {
				// Copy back from Waste2 to Waste1
				Deck waste = null;
				for(Deck to : mDecks)
					if(to.mDeckType == Deck.DeckType.EWaste2){
						waste = to;
						break;}
				Card card = null;
				for (int i = waste.mCards.size()-1; i >= 0; i--) {
					card = waste.mCards.get(i);
					card.mTurned = false;
					toDeck.addCard(waste, card, true);}}
		}
		mActiveCard = null;
	}
	
	/**
	 * Removes cards from Tableau if they form a complete pile and no foundations are present
	 * @param base	The card most recently added
	 */
	
	private void removeIfComplete(Card base){
		Deck scan = base.mOwnerDeck;
		int option = Integer.parseInt(ruleBook.substring(5,6));
		int old;
		if(base.mCardValue == option || option == 0){
			for(int i = 0; i < 12; i++){
				old = base.mCardValue;
				base = scan.mCards.get(scan.mCards.indexOf(base)-1);
				if(old != base.mCardValue - 1 || !base.mTurned)
					return;}
			scan.removeCard(base);}
	}
	
	/**
	 * This function is called when there is no waste pile present
	 * @param stock	The deck currently being dealt to
	 */
	
	private void dealToTableau(Deck stock){
		Card draw;
		for(Deck to : mDecks)
			if(to.mDeckType == Deck.DeckType.ESource){
				try{
					draw = stock.mCards.get(stock.mCards.size()-1);
				}catch(IndexOutOfBoundsException ioobe){
					return;
				}
				draw.mTurned = true;
				to.addCard(stock, draw, false);}		
	}
	
	/**
	 * Determines whether or not an attempted buildup is valid
	 * @param card	The card being moved
	 * @param from	The deck card is coming from
	 * @param to	The deck card is being moved to
	 * @return		Whether or not the move is valid	
	 */

	private boolean acceptCardMove(Deck from, Deck to, Card card) {
		Card topOfThisCard=null;
		// Check deck issuess
		if (from.mDeckType != Deck.DeckType.ESource && from.mDeckType != Deck.DeckType.EWaste2)
			return false;

		if (to.mDeckType != Deck.DeckType.ESource && to.mDeckType != Deck.DeckType.ETarget)
			return false;

		// Card decks must differ
		if (from == to)
			return false;

		// Check card issues
		if(to.mCards.size()>0) {
			topOfThisCard = to.mCards.get(to.mCards.size()-1);
			if (topOfThisCard!=null && !topOfThisCard.mTurned) {
				return false;
			}
			if (to.mDeckType == Deck.DeckType.ESource) {           		
				if (!checkTableauMove(card, topOfThisCard))
					return false;
			} else if (to.mDeckType == Deck.DeckType.ETarget) {
				if (card.mCardValue != topOfThisCard.mCardValue + 1 || card.mCardLand != topOfThisCard.mCardLand)
					return false;
			}
		} else {
			// Moving top of empty deck

			
			if (card.mCardValue != Integer.parseInt(ruleBook.substring(6,7), 14) &&
					Integer.parseInt(ruleBook.substring(6,7), 14) != 0
					&& to.mDeckType == Deck.DeckType.ESource)
				return false;

			// Ace card must be the first card in foundation <Team 4 comment> Changeable
			if (to.mDeckType == Deck.DeckType.ETarget && to.mCards.size() == 0 && (Integer.parseInt(ruleBook.substring(5, 6), 14) != 0
					&& card.mCardValue != Integer.parseInt(ruleBook.substring(5, 6), 14)))
				return false;
		}

		return true;
	}
	
	/**
	 * Same as acceptCardMove, has its own function for modularity
	 * @param movedCard	Card being moved
	 * @param toCard	Card movedCard is being placed on top of
	 * @return			Whether or not the move is valid
	 * @see	ruleBook
	 * @see	acceptCardMove
	 */

	private boolean checkTableauMove(Card movedCard, Card toCard){
		int tV = toCard.mCardValue;
		int mV = movedCard.mCardValue;
		int val = 0;
		boolean mov = false;
		switch(ruleBook.charAt(4)){
		case '0':
			val = tV+Integer.parseInt(ruleBook.substring(1,2), 13); break;
		case '1':
			val = tV-Integer.parseInt(ruleBook.substring(1,2), 13); break;
		default:
			val = (Math.abs(tV - mV) == Integer.parseInt(ruleBook.substring(1,2), 13)) ? mV:0;
		}
		switch(ruleBook.charAt(2)){
		case 't':
			mov = (mV == val%13); break; //Wrappable
		case 'f':
			mov = (mV == val); break; //Not wrappable
		default:
			mov = false;	
		}
		if(mov)
			switch(ruleBook.charAt(0)){
			case '0': //If cards are to be built up in suit
				return movedCard.mCardLand == toCard.mCardLand;
			case '1': //If cards are to be built up by the same color
			case '2':
				return movedCard.mBlack == toCard.mBlack;
			case '3': //If cards are to be built up by alternating color
				return movedCard.mBlack != toCard.mBlack;
			case '4': //If cards are to be built up by changing suit
			case '5':
			case '6':
				return movedCard.mCardLand != toCard.mCardLand;
			case '7':
			case '8':
			case '9':
			case 'a':
			case 'b':
				return true;
			default:
				return false;	
			}
		else
			return false;
	}


	private boolean checkFoundationMove(Card movedCard, Card toCard){
		if(toCard.mCardValue == movedCard.mCardValue + 1 && toCard.mOwnerDeck.mDeckType == Deck.DeckType.ETarget)
			switch(ruleBook.charAt(2)){
			case 's': //If cards are to be built up in suit
				return movedCard.mCardLand == toCard.mCardLand;
			case 'c': //If cards are to be built up by the same color
				return movedCard.mBlack && toCard.mBlack;
			case 'a': //If cards are to be built up by alternating color
				return !(movedCard.mBlack && toCard.mBlack);
			case 'o': //If cards are to be built up by changing suit
				return movedCard.mCardLand != toCard.mCardLand;
			case 'x': //Suit is irrelavant
				return true;
			default:
				return false;	
			}
		else
			return false;
	}
	
	



}
