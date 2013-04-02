package com.uta.byos;

/*
 * This class provides the "crafting tableaux" so-to-speak for the user to create his own custom card games.
 * All javadoc was written by Matthew Waller
 * @author Matthew Waller
 */

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
    private Rect cCell = new Rect();
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

    protected ArrayList<Placeholder> places = new ArrayList<Placeholder>();
    protected Placeholder mActiveStack;
	protected Placeholder menuDummyS;
	protected Placeholder menuDummyT;

    
    private boolean buildInProgress = false;
    
    /*
     * Standard constructors for view objects in Android
     * @see android.view.View
     */
	
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
    
    /*
     * (non-Javadoc)
     * Creates a new table if it is newly initialized otherwise
     * preserves and redraws the current elements for user
     * friendliness
     * @see android.view.View#onSizeChanged(int, int, int, int)
     */
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
		mScreenSize.set(0, 0, w, h);

		// Calculate card and decks sizes and positions
		int cw = w / 11;
		mCardSize.set(0, 0, cw, (int) (cw * 1.5));
		cCell.set(0, 0, cw, h/((int) (cw*1.5)));
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
    
    /*
     * (non-Javadoc)
     * Based on Tero's original algorithm
     * @see android.view.View#onDraw(android.graphics.Canvas)
     * @see TableauView#onDraw
     */
    
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
    
    /*
     * Based on Tero's original algorithm
     * @see TableauView enableCache
     */
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
    
    /*
     * (non-Javadoc)
     * Handles interactivity within the view
     * @see android.view.View#onTouchEvent(android.view.MotionEvent)
     * @see TableauView#onTouchEvent
     */
	
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	int action = event.getAction();
    	if (action == MotionEvent.ACTION_DOWN) {
    		initX = (int) event.getX();
    		initY = (int) event.getY();
    		mActiveStack = getDeckUnderTouch(initX, initY);             
    		return true;
    	} else if (action == MotionEvent.ACTION_MOVE) {
    		int x = getXCell((int) event.getX());
    		int y = getYCell((int) event.getY());
    		if (mActiveStack != null) 
    			mActiveStack.setPos(getXCell(x)-mCardSize.width()/2, getYCell(y)-mCardSize.height()/2);	
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
    				switch(sType.charAt(0)){
    				case 'R':
    					sType = "W";
    					break;
    				case 'W':
    					sType = "F";
    					break;
    				case 'F':
    					sType = "R";
    					break;
    				}
    			}else
    				addStack(alloc, sType, getXCell(x), getYCell(y));
    		}else
    			removeUnderTouch(x, y);
    		//handleCardMove(x, y);
    		invalidate();
    		mActiveStack = null;
    		return true;
    	}
    	return false;

    }
    
//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event){
//    	switch(keyCode){
//    	case(KeyEvent.KEYCODE_DPAD_DOWN):
//    		if(alloc > 1)
//    			alloc--;
//    	break;
//    	case(KeyEvent.KEYCODE_DPAD_UP):
//    		if(alloc < 52)
//    			alloc++;
//    	break;
//    	}
//    	invalidate();
//    	return true;
//    }
    
 
//    public Placeholder getDeckForActivity(int x, int y){
//    	return getDeckUnderTouch(x, y);
//    }
    
    /*
     * Adds a stack to the crafting table
     * @param s		Size of the deck to be added
     * @param type	Any string denoting the type of deck to be added (Reserve/Waste/Foundation/Stock)
     * @param x		Target x-coordinate
     * @param y		Target y-coordinate
     * @throws ArithmeticException	If there are not enough cards in the main waste or if
     * 								there was in error with the argument to param type
     * @see Deck#DeckType 
     */
    
    
    public void addStack(int s, String type, int x, int y) throws ArithmeticException{
    	Placeholder main;
    	main = places.get(0);
    	Deck.DeckType set;
    	switch(type.charAt(0)){
    	case 'S':
    		set = Deck.DeckType.EWaste1;
    		break;
    	case 'W':
    		s = 0;
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
    	if(s > main.getSize())
    		throw new ArithmeticException("Not enough cards!");
    	main.setSize(main.getSize() - s);
    	places.add(new Placeholder(x-mCardSize.width()/2, y-mCardSize.height()/2, s, mCardSize.height(), mCardSize.width(), set, getResources()));
    }
    
    /*
     * Finds the placeholder selected the user is "touching"
     * @param  x			X-coordinate of the click
     * @param  y			Y-coordinate of the click
     * @return Placeholder
     */


    private Placeholder getDeckUnderTouch(int x, int y) {
    	for(Placeholder stack : places)
    		if(stack.isUnderTouch(x, y))
    			return stack;
    	return null;
    }
    
    /*
     * Finds and removes the placeholder selected by the user
     * @param x	X-coordinate of the tap
     * @param y	Y-coordinate of the tap
     */
    
    private int getXCell(int x){
    	int adj = x/mCardSize.width();
    	return adj * mCardSize.width() + mCardSize.width()/2;
    }
    
    private int getYCell(int y){
    	int adj = y/mCardSize.height();
    	return adj*mCardSize.height() + mCardSize.height()/2;
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
    
    @Override
    public String toString(){
    	String data = "";
    	for(Placeholder item : places){
    		Deck.DeckType type = item.type;
    		if(type == Deck.DeckType.ESource)
    			data += "R";
    		else if(type == Deck.DeckType.ETarget)
    			data += "F";
    		else if(type == Deck.DeckType.EWaste1)
    			data += "S";
    		else
    			data.concat("W");
    		data += ("," + item.getX() + "," + item.getY() + "," + item.getSize() + ";");
    	}
    	return data;
    }

    
}
