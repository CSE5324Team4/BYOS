package com.uta.byos;

import java.util.ArrayList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class Deck {
	private int mX;
	private int mY;
	public int mWidth;
	public int mHeight;
	protected Rect mBackgroundRect;
	protected int mDeckCardsInternalZ = 0;
	public int mCardTopCap = 10;

	protected Rect mRect;
	protected Paint paint = new Paint();
	protected ArrayList<Card> mCards = new ArrayList<Card>();
	
	private String buildRule;

	/*
	 * <Team 4> Comment
	 * Respectively
	 * -Stack
	 * -Foundation
	 * -Stock
	 * -Waste
	 */

	public enum DeckType {
		ESource,
		ETarget,
		EWaste1,
		EWaste2
	}
	public DeckType mDeckType;


	public Deck(DeckType type, int x, int y, int width, int height) {
		mDeckType = type;
		mX = x;
		mY = y;
		mWidth = width;
		mHeight = height;
		mRect = new Rect(x,y,x+width,y+height);
		mBackgroundRect = new Rect(mRect);
		paint.setAntiAlias(false);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(2);
		paint.setColor(Color.WHITE);
		mCardTopCap = height / 5;
	}

	public void doDraw(Canvas canvas) {
		// Draw deck background rectangle
		Rect tmp = new Rect(mBackgroundRect);
		tmp.inset(3, 3);
		canvas.drawRect(tmp, paint);

		// Draw cards
		for (Card card : mCards) {
			card.doDraw(canvas);
		}
	}

	public void addCard(Deck fromDeck, Card newCard, boolean justOnTopOfOthers) {
		// Add card to new deck
		addCard(newCard, justOnTopOfOthers);

		// Remove card from old deck
		fromDeck.removeCard(newCard);

		// Null old deck parent card reference
		if(fromDeck.mCards.size()>0) {
			Card c = fromDeck.mCards.get(fromDeck.mCards.size()-1);
			c.mParentCard = null;
		}

		// Add card parent also to this new deck if them exists?
		if (newCard.mParentCard != null && newCard.mOwnerDeck != null
				&& newCard.mOwnerDeck.mDeckType == Deck.DeckType.ESource) {
			addCard(fromDeck,newCard.mParentCard,justOnTopOfOthers);
		}

		//Log.v("from", Integer.toString(fromDeck.mCards.size()));
		//Log.v("to", Integer.toString(mCards.size()));
	}


	public void addCard(Card newCard, boolean justOnTopOfOthers) {
		newCard.setDeck(this);

		if (justOnTopOfOthers) {
			// All cards are just top of other cards
			// Set card new position
			newCard.mRect.set(this.mRect);
		} else {
			// There is cap on top of all cards
			// Change deck rectangle height
			mHeight = newCard.mRect.height() + mCardTopCap*mCards.size();
			mRect = new Rect(mX,mY,mX+mWidth,mY+mHeight);

			// Set card new position
			newCard.mRect.set(mX,
					mY + mCards.size()*mCardTopCap,
					mX+mWidth,
					mY + mCards.size()*mCardTopCap + newCard.mRect.height());
		}


		// Give new z to this most upper card in this deck
		mDeckCardsInternalZ++;
		newCard.mZ = mDeckCardsInternalZ;

		// Set new card to be previous card parent
		if (!mCards.isEmpty()) {
			mCards.get(mCards.size()-1).mParentCard = newCard;
		}

		// Add card
		mCards.add(newCard);
	}
	
	public void adjust(int w, int h, int oldw, int oldh){
		mRect.set(mRect.left*w/oldw, mRect.top*h/oldh, mRect.right*w/oldw, mRect.bottom*h/oldh);
		Card c;
		Rect cRect;
		for(int i = 0; i < mCards.size(); i++){
			c = mCards.get(i);
			cRect = c.mRect;
			c.mX *= w/oldw;
			c.mY *= h/oldh;
			c.mRect.set(cRect.left*w/oldw, cRect.top*h/oldh, cRect.right*w/oldw, cRect.bottom*h/oldh);
			mCards.set(i, c);
		}
	}

	public void removeCard(Card removeThis) {
		mCards.remove(removeThis);
	}

	public Card getCardFromPos(int x, int y) {
		Card c = null;
		// Return most upper card from this deck in this position
		for (Card card : mCards) {
			if(card.mRect.contains(x,y)) {
				if (c!=null && c.mZ < card.mZ)
					c = card;
				else if(c==null)
					c = card;
			}
		}

		// If card has parent AND
		// it is NOT turned, do not give it
		if(c != null && c.mParentCard!=null && c.mTurned==false)
			return null;

		// Turn card if needed
//		if (c != null && (mDeckType == Deck.DeckType.ESource || mDeckType == Deck.DeckType.EWaste1
//				|| mDeckType == Deck.DeckType.EWaste2))
//			c.mTurned = true;

		return c;
	}

	public boolean isUnderTouch(int x, int y) {
		if (mRect.contains(x, y))
			return true;
		else
			return false;
	}
	
	public void setPos(int x, int y){
		mX = x;
		mY = y;
		mRect.set(x, y, x+mRect.width(), y+mRect.height());
	}
	public int getX(){
		return mX;
	}
	public int getY(){
		return mY;
	}

	public void revealTopCard() {
		int index = mCards.size();
		if(index == 0)
			return;
		Card top = mCards.get(index-1);
		top.mTurned = true;
	}
	
	public Card getTopCard(){
		return mCards.get(mCards.size()-1);
	}
	
	public void flipTopCard(){
		int index = mCards.size();
		if(index == 0)
			return;
		Card top = mCards.get(index-1);
		top.mTurned = !top.mTurned;
	}
}
