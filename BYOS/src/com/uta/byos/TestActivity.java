package com.uta.byos;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * This class displays the GameTest object used during gameplay 
 * @author Matthew Waller
 *
 */

public class TestActivity extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		Bundle in = getIntent().getExtras();
		String input = in.getString("layout");
		String inputR = in.getString("rules");
		int limit = in.getInt("move limit");
		View gT = findViewById(R.id.gameTest1);
		GameTest gg = (GameTest) gT;
		gg.constructFromInput(input, inputR, limit);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add(R.string.switch_mode);
		getMenuInflater().inflate(R.menu.activity_test, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
		case 0:
			finish();
			return true;
		case R.id.menu_settings:
			return true;
		}
		return false;
	}
	

}
