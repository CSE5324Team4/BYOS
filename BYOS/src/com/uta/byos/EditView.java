package com.uta.byos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class EditView extends View {

	private Rect mScreenSize = new Rect();

	private Rect mCardSize = new Rect();
	private int cardXCap;
	private int cardYCap;
	private int mCardCap;
	private int recBinX;
	private int recBinY;
	
	private Bitmap bin =  BitmapFactory.decodeResource(getResources(), R.raw.recyclebin);
	private Paint mCanvasPaint;

	private Placeholder menuDummyS;
	private Placeholder menuDummyT;

	private boolean mUseCache;

	private Bitmap mCacheBitmap;

	public EditView(Context context) {
		super(context);
		mCanvasPaint = new Paint();
		mCanvasPaint.setColor(0xFF000000);
		mCanvasPaint.setAntiAlias(false);
		mCanvasPaint.setFilterBitmap(false);

	}

	public EditView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mCanvasPaint = new Paint();
		mCanvasPaint.setColor(0xFF000000);
		mCanvasPaint.setAntiAlias(false);
		mCanvasPaint.setFilterBitmap(false);
	}

	public EditView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mCanvasPaint = new Paint();
		mCanvasPaint.setColor(0xFF000000);
		mCanvasPaint.setAntiAlias(false);
		mCanvasPaint.setFilterBitmap(false);
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

		menuDummyS = new Placeholder((w-mCardSize.width())/5, (h-mCardSize.height())/2, 52, mCardSize.height(), mCardSize.width(), Deck.DeckType.EWaste1, getResources());
		menuDummyT = new Placeholder((w-mCardSize.width())*2/5, (h-mCardSize.height())/2, 0, mCardSize.height(), mCardSize.width(), Deck.DeckType.EWaste1, getResources());
		recBinX = 3*w/4;
		recBinY = h/2;
	}

	@Override
	public void onDraw(Canvas canvas){

		// Cache?
		if (mUseCache) {
			// Yes
			canvas.drawBitmap(mCacheBitmap, 0, 0, null);
		} else {
			// No
			mCanvasPaint.setStyle(Style.FILL);
			canvas.drawRect(mScreenSize, mCanvasPaint);

			menuDummyS.doDraw(canvas);
			menuDummyT.doDraw(canvas);

			canvas.drawBitmap( bin, mScreenSize.width()*3/5, mScreenSize.height()/2, mCanvasPaint);
		}


	}


}
