package com.uta.byos;

/**
 * An extension of Placeholder to allow the user to more accurately portray it's appearance in GameTest and to allow further
 * customization
 */

import java.util.ArrayList;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.uta.byos.Deck.DeckType;

public class CustomReserve extends Placeholder {
	private ArrayList<Boolean> mTurned;
	private Rect topOfStack;
	private Bitmap faceUp;

	public CustomReserve(Placeholder copyFrom) {
		super(copyFrom);
		// TODO Auto-generated constructor stub
	}

	public CustomReserve(int x, int y, int inS, int inH, int inW, 
			Resources res, boolean faceup) {
		super(x, y, inS, inH, inW, Deck.DeckType.ESource, res);
		mTurned = new ArrayList<Boolean>();
		addCards(inS, faceup);
		Rect r = super.mRect;
		topOfStack = new Rect(r.left, r.top+(inS-1)*10, r.right, r.bottom+(inS-1)*10);
		faceUp = Bitmap.createBitmap(getmBitmap());
		faceUp.eraseColor(Color.WHITE);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void doDraw(Canvas canvas){
		int i = 0; Rect tmp = new Rect(mRect); Paint black = new Paint();
		black.setColor(Color.BLACK);
		tmp.inset(3, 3);
		canvas.drawRect(tmp, paint);
		for(boolean b : mTurned){
			int top = mRect.top + (i++)*10;
			if(b){
				canvas.drawBitmap(faceUp, mRect.left, top, paint);
				canvas.drawLine(mRect.left, top, mRect.left + mRect.width(), top, black);
			}else
				canvas.drawBitmap(getmBitmap(), mRect.left, top, paint);
		}
	}
	
	/**
	 * Determines whether or not the user's tap was on the card that is on top of the "Deck"
	 * @param x	X-Coordinate of the tap
	 * @param y	Y-Coordinate of the tap
	 * @return	Whether or not the coordinates are within the bounds of the top "card"
	 */
	
	public boolean isTopOfStack(int x, int y){
		return topOfStack.contains(x, y);
	}
	
	/**
	 * Adds cards to the stack
	 * @param i		Number of cards to be added
	 * @param in	Whether or not the cards are face up
	 */
	
	public void addCards(int i, boolean in){
		for(int j = 0; j<i; j++)
			mTurned.add(Boolean.valueOf(in));
		setSize(mTurned.size());
	}
	
	@Override
	public void setPos(int x, int y) {
		super.setPos(x, y);
		topOfStack.set(x, y, x + topOfStack.width(), y + topOfStack.height());
	}
	
	public String toString(){
		String data = "";
		for(boolean b : mTurned)
			if(b)
				data += "U";
			else
				data += "D";
		return data;
	}

}
