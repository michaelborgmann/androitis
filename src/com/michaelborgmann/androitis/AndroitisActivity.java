package com.michaelborgmann.androitis;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class AndroitisActivity extends Activity implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        View newGameButton = findViewById(R.id.new_game_button);
        newGameButton.setOnClickListener(this);
        View aboutButton = findViewById(R.id.about_button);
        aboutButton.setOnClickListener(this);
        View quitButton = findViewById(R.id.quit_button);
        quitButton.setOnClickListener(this);
        
    }

    public void onClick(View view) {
    	
    	Intent intent;
    	
    	switch (view.getId()) {
    	case R.id.new_game_button:
    		intent = new Intent(this, GameActivity.class);
    		startActivity(intent);
    		break;
    	case R.id.about_button:
    		intent = new Intent(this, AboutActivity.class);
    		startActivity(intent);
    		break;
    	case R.id.quit_button:
    		finish();
    		break;
    	}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.layout.menu, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.options:
    		startActivity(new Intent(this, OptionsActivity.class));
        	return true;
        default: return false;
    	}
    }
   
}