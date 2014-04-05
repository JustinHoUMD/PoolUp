package com.mobile.poolup;

import com.firebase.client.Firebase;
import com.firebase.simplelogin.SimpleLogin;
import com.firebase.simplelogin.SimpleLoginAuthenticatedHandler;
import com.firebase.simplelogin.User;
import com.github.sendgrid.SendGrid;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class CreateFragment extends Fragment implements OnClickListener {
	
	String useremail;
	EditText toEmail,Subject,Message;
	Button sendButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		checkUser();
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_create, container, false); 
        toEmail = (EditText)rootView.findViewById(R.id.etToEmail);
        Subject = (EditText)rootView.findViewById(R.id.etSubject);
        Message = (EditText)rootView.findViewById(R.id.etMessageBody);
        
        sendButton = (Button)rootView.findViewById(R.id.bSendEmail);
        sendButton.setOnClickListener(this);
        
        return rootView;
    }
	
 private void checkUser(){
		 
		 Firebase mainref = new Firebase("https://sizzling-fire-7279.firebaseio.com/");
		 SimpleLogin authClient = new SimpleLogin(mainref,this.getActivity());
		 authClient.checkAuthStatus(new SimpleLoginAuthenticatedHandler() {
			  public void authenticated(com.firebase.simplelogin.enums.Error error, User user) {
			    if (error != null) {
			      // Oh no! There was an error performing the check
			    } else if (user == null) {
			      // No user is logged in
			    } else {
			    	useremail = user.getEmail();
			    	int duration = Toast.LENGTH_LONG;
					Toast toast = Toast.makeText(
							getActivity(), useremail,
							duration);
					toast.show();
			    }
			  }			
			});
	 }

@Override
public void onClick(View v) {
	switch(v.getId()){
	case R.id.bSendEmail:
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		SendGrid sendGrid = new SendGrid("anishk25", "25ANUfeb");
		sendGrid.addTo(toEmail.getText().toString());		
		sendGrid.setFrom(useremail);
		sendGrid.setSubject(Subject.getText().toString());
		sendGrid.setText(Message.getText().toString());
		sendGrid.send();

	}
	
}
}
