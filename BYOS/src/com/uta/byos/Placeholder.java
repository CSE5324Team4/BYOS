package com.uta.byos;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Placeholder {

	private int mX;
	private int mY;
	private int size;  //Number of cards in the pile
	private Rect mRect;
	private int height;
	private int width;
	private Deck.DeckType type;
	private Bitmap mBitmap;
	private boolean mVisible;
	protected Paint paint = new Paint();
	protected Rect mBackgroundRect;
	
	public Placeholder(Placeholder copyFrom){
	}
	
	public Placeholder(int x, int y, int inS, int inH, int inW, Deck.DeckType in, Resources res){
		mX = x;
		mY = y;
		type = in;
		size = inS;
		width = inW;
		height = inH;
		mRect = new Rect(x, y, x + width, y + height);
		Bitmap tmp = BitmapFactory.decodeResource(res, R.raw.cardback);
		//tmp = BitmapFactory.decodeResource(res, bmpResId, options);  //<----Team 4 comment this is what was causing the cards to look ugly
		mBitmap = Bitmap.createScaledBitmap(tmp, width, height, true);
		tmp = null;
		paint.setAntiAlias(false);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(2);
		paint.setColor(Color.WHITE);
	}
	
	public void doDraw(Canvas canvas) {
		if (size > 0){
			canvas.drawBitmap(mBitmap, mRect.left, mRect.top, null);
			String sizGrph = String.valueOf(size);
			canvas.drawText(sizGrph, mRect.left + mRect.width()/4, mRect.bottom - mRect.height()/2, paint);
		}else{
			Rect tmp = new Rect(mRect);
			tmp.inset(3, 3);
			canvas.drawRect(tmp, paint);}
	}

	public void setPos(int x, int y) {
		mX = x;
		mY = y;
		mRect.set(x, y, x + mRect.width(), y + mRect.height());
	}

	public int getX(){ return mX;}
	public int getY(){ return mY;}
	public int getSize(){ return size;}
	
	public void setSize(int s){
		size = s;
	}
	
	public boolean isVisible() {
		return mVisible;
	}

	public void setVisible(boolean visible) {
		mVisible = visible;
	}

	public boolean isUnderTouch(int x, int y) {
		if (mRect.contains(x, y))
			return true;
		else
			return false;
	}

}
