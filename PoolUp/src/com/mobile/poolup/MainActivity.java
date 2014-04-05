package com.mobile.poolup;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	EditText username; 
	EditText password; 
	Button login; 
	String u,p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText)findViewById(R.id.usernameText);
        password = (EditText)findViewById(R.id.passwordText); 
        login = (Button)findViewById(R.id.loginButton);
        login.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                    	 u = username.getText().toString(); 
                         p = password.getText().toString(); 
                    }
                });
       
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
