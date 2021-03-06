package com.uta.byos;


import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
			SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
			String rules = parseFromSharedPref(sharedPref);
			View gB = findViewById(R.id.gameBuilder1);
			i.putExtra("layout", ((GameBuilder) gB).toString());
			i.putExtra("rules", rules);
			i.putExtra("move limit", Integer.parseInt( sharedPref.getString("move_int", "-1") ) );
			startActivity(i);
			return true;
		case R.id.menu_settings:
			Intent i2 = new Intent(this, SettingsActivity.class);
			startActivity(i2);
			return true;
		}
		return false;
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		GameBuilder gB = (GameBuilder) findViewById(R.id.gameBuilder1);
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		if(gB.buildInProgress)
			gB.setDeckSize(Integer.valueOf(sharedPref.getString("deck_size", "1")));
		else
			gB.initDeckSize(Integer.valueOf(sharedPref.getString("deck_size", "1")));
	}
	
	/**
	 * Upon selecting "Switch Mode" this method creates the rule book for GameTest to interpret during gameplay
	 * @param sharedPref	The user's selected preferences
	 * @return A seven character string that is to be used as the rule book
	 * @see	GameTest#ruleBook
	 */
	
	private String parseFromSharedPref(SharedPreferences sharedPref){
		String out = "";
		out += parseFromListPreference("rulebook", sharedPref.getString("rulebook", "Alternating-Alternating"));
		out += Integer.toHexString(Integer.valueOf(sharedPref.getString("build_int", "1")));
		if(sharedPref.getBoolean("movewrap", false))
			out += 't';
		else
			out += 'f';
		if(sharedPref.getBoolean("buildwrap", false))
			out += 't';
		else
			out += 'f';
		out += parseFromListPreference("order", sharedPref.getString("order", "Build descending"));
		out += parseFromListPreference("startfound", sharedPref.getString("startfound", "Ace"));
		out += parseFromListPreference("start_res", sharedPref.getString("start_res", "King"));
		return out;
	}
	
	/**
	 * Used by parseFromSharedPref for parsing ListPreference selections
	 * @param name Name of android:key value
	 * @param opt  The option selected 
	 * @return The index of option selected from the menu represented by name parameter
	 */
	
	private String parseFromListPreference(String name, String opt){
		if(name.equals("rulebook")){
			String[] optin = {"Suit-Suit", 
	        "Color-Color", 
	        "Color-Suit", 
	        "Alternating-Alternating", 
	        "Other-Other", 
	        "Other-Color", 
	        "Other-Alternating", 
	        "None-None", 
	        "None-Other", 
	        "None-Color", 
	        "None-Alternating", 
	        "None-Suit"};
			for(int i = 0; i < optin.length; i++)
				if (optin[i].equals(opt))
					return Integer.toHexString(i);
		} else if(name.equals("order")){
			String[] optin = {"Build ascending", 
	        "Build descending", 
	        "Move ascending", 
	        "Move descending", 
	        "None"};
			for(int i = 0; i < optin.length; i++)
				if (optin[i].equals(opt))
					return Integer.toHexString(i);
		} else if(name.equals("startfound") || name.equals("start_res")){
			String[] optin = {"None"
	        ,"Ace"
	        ,"2"
	        ,"3"
	        ,"4"
	        ,"5"
	        ,"6"
	        ,"7"
	        ,"8"
	        ,"9"
	        ,"10"
	        ,"Jack"
	        ,"Queen"
	        ,"King"};
			for(int i = 0; i < optin.length; i++)
				if(optin[i].equals(opt)){
					return Integer.toHexString(i);}
		}
		return "2";
	
	}

	
	

}
