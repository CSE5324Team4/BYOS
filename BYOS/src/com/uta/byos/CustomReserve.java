package com.uta.byos;

import java.util.ArrayList;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
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
		int i = 0;
		for(boolean b : mTurned){
			int top = mRect.top + (i++)*10;
			if(b)
				canvas.drawBitmap(faceUp, mRect.left, top, paint);
			else
				canvas.drawBitmap(getmBitmap(), mRect.left, top, paint);
		}
	}
	
	public boolean isTopOfStack(int x, int y){
		return topOfStack.contains(x, y);
	}
	
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
