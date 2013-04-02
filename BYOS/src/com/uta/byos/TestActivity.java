package com.uta.byos;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class TestActivity extends Activity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		String input = getIntent().getExtras().getString(android.content.Intent.EXTRA_TEXT);
		View gT = findViewById(R.id.gameTest1);
		GameTest gg = (GameTest) gT;
		gg.constructFromInput(input);
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
			Intent i = new Intent(this, GameActivity.class);
			startActivity(i);
			return true;
		case R.id.menu_settings:
			return true;
		}
		return false;
	}
	

}
