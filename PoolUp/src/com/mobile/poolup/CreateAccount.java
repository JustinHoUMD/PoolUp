package com.mobile.poolup;

import com.firebase.client.Firebase;
import com.firebase.simplelogin.SimpleLogin;
import com.firebase.simplelogin.SimpleLoginAuthenticatedHandler;
import com.firebase.simplelogin.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccount extends Activity implements OnClickListener {

	private EditText etName, etUserName, etPassword;
	private Button doneButton;
	private Firebase mainref, users;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_account);
		initViews();
	}

	private void initViews() {

		mainref = new Firebase("https://sizzling-fire-7279.firebaseio.com/");
		users = mainref.child("users");

		etName = (EditText) findViewById(R.id.etSetName);
		etUserName = (EditText) findViewById(R.id.etSetUserNameTxt);
		etPassword = (EditText) findViewById(R.id.etSetPasswordTxt);
		doneButton = (Button) findViewById(R.id.bCreateDone);
		doneButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bCreateDone:
			createAccount();

		}
	}

	private void createAccount() {

		String name = etName.getText().toString();
		String password = etPassword.getText().toString();
		String email = etUserName.getText().toString();
		users.child(parseEmail(email)).child("Name").setValue(name);
		

		SimpleLogin authClient = new SimpleLogin(mainref,
				getApplicationContext());

		authClient.createUser(email, password,
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
						} else {
							Intent returnIntent = new Intent();
							setResult(RESULT_OK, returnIntent);
							finish();
						}

					}
				});

	}
	
	String parseEmail(String email){		
		return email.substring(0, email.indexOf("@"));		
	}
	

}
