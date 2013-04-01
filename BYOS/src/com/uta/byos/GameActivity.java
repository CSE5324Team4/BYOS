package com.uta.byos;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class GameActivity extends Activity{


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add(R.string.switch_mode);
		getMenuInflater().inflate(R.menu.activity_game, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
		case 0:
			Intent i = new Intent(this, TestActivity.class);
			View gB = findViewById(R.id.gameBuilder1);
			i.putExtra(android.content.Intent.EXTRA_TEXT, ((GameBuilder) gB).toString());
			startActivity(i);
			return true;
		case R.id.menu_settings:
			return true;
		}
		return false;
	}
	

}
