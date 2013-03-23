package com.uta.byos;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class GameBuilder extends View {

	private Paint mCanvasPaint;
	
    private Bitmap mCacheBitmap;
    private Rect mScreenSize = new Rect();
    private boolean mUseCache = false;

    private Rect mCardSize = new Rect();
    private int cardXCap;
    private int cardYCap;
    private int alloc = 1;
    private int initX;
    private int initY;
    private int mCardCap;
    private Paint textP = new Paint();
    private String sType;
    private Paint typeP = new Paint();

    private ArrayList<Placeholder> places = new ArrayList<Placeholder>();
    private Placeholder mActiveStack;
	private Placeholder menuDummyS;
	private Placeholder menuDummyT;

    
    private boolean buildInProgress;
    
	
    public GameBuilder(Context context) {
    	super(context);

    	mCanvasPaint = new Paint();
    	mCanvasPaint.setColor(0xFF228B22); // Green background
    	mCanvasPaint.setAntiAlias(false);
    	mCanvasPaint.setFilterBitmap(false);
        textP.setColor(0xFF000000);
        typeP.setColor(0xFF000000);
    	setClickable(true);
    	
    }
    
    public GameBuilder(Context context, AttributeSet attrs){
        super(context, attrs);

        mCanvasPaint = new Paint();
        mCanvasPaint.setColor(0xFF228B22); // Green background
        mCanvasPaint.setAntiAlias(false);
        mCanvasPaint.setFilterBitmap(false);
        textP.setColor(0xFF000000);
        typeP.setColor(0xFF000000);
    	setClickable(true);
    }
    
    public GameBuilder(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);

        mCanvasPaint = new Paint();
        mCanvasPaint.setColor(0xFF228B22); // Green background
        mCanvasPaint.setAntiAlias(false);
        mCanvasPaint.setFilterBitmap(false);
        textP.setColor(0xFF000000);
        typeP.setColor(0xFF000000);
    	setClickable(true);
    	
    }
    
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
		mScreenSize.set(0, 0, w, h);

		// Calculate card and decks sizes and positions
		int cw = w / 11;
		mCardSize.set(0, 0, cw, (int) (cw * 1.5));
		Log.v("card size", mCardSize.toString());

		int freeSize = w - cw * 7;
		mCardCap = freeSize / (6 + 4 * 2);

		int cy = (int) (mScreenSize.height() * 0.35);
		String allocStr = String.valueOf(alloc);
		sType = "R";

    	if(buildInProgress){
    		int index, x, y, size;
    		Placeholder item;
    		for(index = 0; index < places.size(); index++){
    			item = places.get(index);
    			x = item.getX()*h/oldh;
    			y = item.getY()*w/oldw;
    			size = item.getSize();
    			places.set(index, new Placeholder(x, y, size, mCardSize.height(), mCardSize.width(), Deck.DeckType.EWaste1, getResources()));}
    	}else{
    		places.add(new Placeholder((w-mCardSize.width())/2, (h-mCardSize.height())/2, 52, mCardSize.height(), mCardSize.width(), Deck.DeckType.EWaste1, getResources()));
    		buildInProgress = true;
    	}
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
                    canvas.drawText(String.valueOf(alloc), mScreenSize.width()*3/4, mScreenSize.height()*7/8, textP);
                    canvas.drawText(sType, mScreenSize.width()/4, mScreenSize.height()*7/8, typeP);
                    // Draw decks
                    for(Placeholder card : places)
                    	card.doDraw(canvas);
            }
            if (mActiveStack != null) {
                mActiveStack.doDraw(canvas);}
           

    }
    private void enableCache(boolean enabled) {
    	if (enabled && !mUseCache) { //<Team 4 comment> Panaanen had this written as enabled && mUseCache != enabled
    		mActiveStack.setVisible(false);
    		setDrawingCacheEnabled(true);
    		//buildDrawingCache();
    		mCacheBitmap = Bitmap.createBitmap(getDrawingCache());
    		mActiveStack.setVisible(true);
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
    		initX = (int) event.getX();
    		initY = (int) event.getY();
    		mActiveStack = getDeckUnderTouch(initX, initY);             
    		return true;
    	} else if (action == MotionEvent.ACTION_MOVE) {
    		int x = (int) event.getX();
    		int y = (int) event.getY();
    		if (mActiveStack != null) 
    			mActiveStack.setPos(x - cardXCap, y - cardYCap);	
    		return true;

    	} else if (action == MotionEvent.ACTION_UP) {
    		int x = (int) event.getX();
    		int y = (int) event.getY();  
    		enableCache(false);
    		if(mActiveStack == null){
    			if(Math.abs(x - mScreenSize.width()*3/4) < 32  && Math.abs(y - mScreenSize.height()*7/8) < 32){
    				if(alloc < 52  && y < mScreenSize.height()*7/8)
    					alloc++;
    				else if(alloc > 1 && y > mScreenSize.height()*7/8)
    					alloc--;
    			}else if(Math.abs(y - mScreenSize.height()*7/8) < 32 && Math.abs(x - mScreenSize.width()/4) < 32){
    				if(sType.equals("R"))
    					sType = "F";
    				else
    					sType = "R";
    			}else
    				addStack(alloc, sType, x, y);
    		}else if(Math.abs(x-initX) < event.getXPrecision() || Math.abs(y-initY) < event.getYPrecision())
    			removeUnderTouch(x, y);
    		//handleCardMove(x, y);
    		invalidate();
    		mActiveStack = null;
    		return true;
    	}
    	return false;

    }
    
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event){
    	switch(keyCode){
    	case(KeyEvent.KEYCODE_DPAD_DOWN):
    		if(alloc > 1)
    			alloc--;
    	break;
    	case(KeyEvent.KEYCODE_DPAD_UP):
    		if(alloc < 52)
    			alloc++;
    	break;
    	}
    	invalidate();
    	return true;
    }
    
    public Placeholder getDeckForActivity(int x, int y){
    	return getDeckUnderTouch(x, y);
    }
    
    public void addStack(int s, String type, int x, int y) throws ArithmeticException{
    	Placeholder main;
    	main = places.get(0);
    	if(s > main.getSize())
    		throw new ArithmeticException("Not enough cards!");
    	else{
    		Deck.DeckType set;
    		switch(type.charAt(0)){
    		case 'S':
    			set = Deck.DeckType.EWaste1;
    			break;
    		case 'W':
    			set = Deck.DeckType.EWaste2;
    			break;
    		case 'R':
    			set = Deck.DeckType.ESource;
    			break;
    		case 'F':
    			s = 0;
    			set = Deck.DeckType.ETarget;
    			break;
    		default:
    			throw new ArithmeticException("Not a valid deck type");
    		}
    		main.setSize(main.getSize() - s);
    		places.add(new Placeholder(x, y, s, mCardSize.height(), mCardSize.width(), set, getResources()));
    	}
    }
    



    private Placeholder getDeckUnderTouch(int x, int y) {
    	for(Placeholder stack : places)
    		if(stack.isUnderTouch(x, y))
    			return stack;
    	return null;
    }
    
    private void removeUnderTouch(int x, int y){
    	Placeholder index;
    	int dec;
    	for(int i = 0; i < places.size(); i++){
    		index = places.get(i);
    		dec = index.getSize();
    		if(index.isUnderTouch(x, y) && i != 0){
    			index = places.get(0);
    			index.setSize(index.getSize() + dec);
    			places.set(0, index);
    			places.remove(i);
    			break;
    		}}
    }
    

    
}
