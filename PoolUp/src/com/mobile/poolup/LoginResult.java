package com.mobile.poolup;

import com.firebase.client.Firebase;
import com.firebase.simplelogin.SimpleLogin;
import com.firebase.simplelogin.SimpleLoginAuthenticatedHandler;
import com.firebase.simplelogin.User;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class LoginResult extends Activity {
	
	private TextView tvUserName;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.login_activity);  	        
	        tvUserName =(TextView)findViewById(R.id.tvUserName);
	        checkUser();
	       
	    }
	 
	 
	 private void checkUser(){
		 
		 Firebase mainref = new Firebase("https://sizzling-fire-7279.firebaseio.com/");
		 SimpleLogin authClient = new SimpleLogin(mainref,getApplicationContext());
		 authClient.checkAuthStatus(new SimpleLoginAuthenticatedHandler() {
			  public void authenticated(com.firebase.simplelogin.enums.Error error, User user) {
			    if (error != null) {
			      // Oh no! There was an error performing the check
			    } else if (user == null) {
			      // No user is logged in
			    } else {
			     tvUserName.setText(user.getEmail());
			    }
			  }			
			});
	 }

}
