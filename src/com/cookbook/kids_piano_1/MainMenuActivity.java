package com.cookbook.kids_piano_1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class MainMenuActivity extends Activity {
	private Button btnStart, btnHelp, btnCredits, btnExit;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
		Eula.show(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
    	btnStart = (Button) findViewById(R.id.btnStart);
    	btnHelp = (Button) findViewById(R.id.btnHelp);
    	btnCredits = (Button) findViewById(R.id.btnCredits);
    	btnExit = (Button) findViewById(R.id.btnExit);
    	
    	btnStart.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startPiano();
			}
		});
    	
    	btnHelp.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				viewHelp();
			}
		});
    	
    	btnCredits.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				viewCredits();
			}
		});
    	
    	btnExit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				exitApp();
			}
		});
    }
    
    private void startPiano() {
		startActivity(new Intent("com.cookbook.PianoActivity"));
		this.finish();
    }
    
    private void viewHelp() {
		startActivity(new Intent("com.cookbook.HelpActivity"));
		this.finish();
    }
    
    private void viewCredits() {
    	startActivity(new Intent("com.cookbook.CreditsActivity"));
    	this.finish();
    }
    
    private void exitApp() {
    	this.finish();
    }
}