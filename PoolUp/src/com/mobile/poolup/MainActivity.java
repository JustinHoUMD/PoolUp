package com.mobile.poolup;

import com.firebase.client.Firebase;
import com.firebase.simplelogin.SimpleLogin;
import com.firebase.simplelogin.SimpleLoginAuthenticatedHandler;
import com.firebase.simplelogin.User;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private EditText etUser;
	private EditText etPass;
	private Button login, createAccount;

	private Firebase mainref, users;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mainref = new Firebase("https://sizzling-fire-7279.firebaseio.com/");
		users = mainref.child("users");
		//checkIfLoggedIn();
		
		
		setContentView(R.layout.activity_main);		
		initViews();
	}

	private void checkIfLoggedIn() {
		 SimpleLogin authClient = new SimpleLogin(mainref,getApplicationContext());
		 authClient.checkAuthStatus(new SimpleLoginAuthenticatedHandler() {
			  public void authenticated(com.firebase.simplelogin.enums.Error error, User user) {
			    if (error != null) {
			      // Oh no! There was an error performing the check
			    } else if (user == null) {
			      // No user is logged in
			    } else {
			    	Log.d("DEBUG","Loggin In");
					Intent intent = new Intent("android.intent.action.TRIPACTIVITY");
					startActivity(intent);
			    }
			  }			
			});
		
	}

	private void initViews() {		

		etUser = (EditText) findViewById(R.id.usernameText);
		etPass = (EditText) findViewById(R.id.passwordText);

		login = (Button) findViewById(R.id.bLoginButton);
		createAccount = (Button) findViewById(R.id.bCreatAccount);

		login.setOnClickListener(this);
		createAccount.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bLoginButton:
			authenticateLogin();
		case R.id.bCreatAccount:
			Intent intent = new Intent("android.intent.action.CREATEACCOUNT");
			startActivityForResult(intent, 1);
		
		}

	}

	private void authenticateLogin() {
		String user = etUser.getText().toString();
		String pass = etPass.getText().toString();

		SimpleLogin authClient = new SimpleLogin(mainref,
				getApplicationContext());

		authClient.loginWithEmail(user, pass,
				new SimpleLoginAuthenticatedHandler() {

					@Override
					public void authenticated(
							com.firebase.simplelogin.enums.Error error,
							User user) {

						if (error != null) {
							int duration = Toast.LENGTH_LONG;
							Toast toast = Toast.makeText(
									getApplicationContext(), error.toString(),
									duration);
							toast.show();
						}
						else{
							Log.d("DEBUG","Loggin In");
							Intent intent = new Intent(getApplicationContext(),TripActivity.class);
							startActivity(intent);
						}

					}
				});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {

			if (resultCode == RESULT_OK) {
				int duration = Toast.LENGTH_LONG;
				Toast toast = Toast.makeText(this,
						"Account Created Successfully!", duration);
				toast.show();
			}
			if (resultCode == RESULT_CANCELED) {
				// Write your code if there's no result
			}
		}
	}

}
