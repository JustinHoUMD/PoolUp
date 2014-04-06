package com.mobile.poolup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.simplelogin.SimpleLogin;
import com.firebase.simplelogin.SimpleLoginAuthenticatedHandler;
import com.firebase.simplelogin.User;
import com.github.sendgrid.SendGrid;

import android.app.TimePickerDialog;
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
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateFragment extends Fragment implements OnClickListener {

	private String useremail,fullName,userName;
	
	private Firebase fireBaseRef;

	private EditText etTripName, etDate, etEmailAddresses,etDestination;
	private Button bCreateTrip;
	private TimePicker timePicker;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		fireBaseRef = new Firebase("https://sizzling-fire-7279.firebaseio.com/");		
		checkUser();		
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_create, container,
				false);
		etTripName = (EditText) rootView.findViewById(R.id.etTripName);
		etDate = (EditText) rootView.findViewById(R.id.etDateOfTrip);
		etEmailAddresses = (EditText) rootView
				.findViewById(R.id.etEmailAddresses);
		etDestination = (EditText)rootView.findViewById(R.id.etDestination);
		
		bCreateTrip = (Button) rootView.findViewById(R.id.bCreateTrip);
		bCreateTrip.setOnClickListener(this);

		timePicker = (TimePicker) rootView.findViewById(R.id.tpPicker);

		return rootView;
	}

	private void checkUser() {

		SimpleLogin authClient = new SimpleLogin(fireBaseRef,
				this.getActivity());
		authClient.checkAuthStatus(new SimpleLoginAuthenticatedHandler() {
			public void authenticated(
					com.firebase.simplelogin.enums.Error error, User user) {
				if (error != null) {
					// Oh no! There was an error performing the check
				} else if (user == null) {
					// No user is logged in
				} else {
					useremail = user.getEmail();
					userName= parseEmail(useremail);
					getName(fireBaseRef.child("users").child(userName).child("Name"));
					Log.d("DEBUG LOGIN", userName);
					
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bCreateTrip:			
			String tripCode = genRandomTripCode();
			String time = "" + timePicker.getCurrentHour() + ":"
					+ timePicker.getCurrentMinute();			

			
			
			
			Map<String, Object> tripDetails = new HashMap<String, Object>();
			tripDetails.put("Driver",userName);
			tripDetails.put("Date",etDate.getText().toString());
			tripDetails.put("TripName",etTripName.getText().toString());			
			tripDetails.put("Time",time);
			tripDetails.put("Destination",etDestination.getText().toString());
			fireBaseRef.child("trips").child(tripCode).child("TripDetails").setValue(tripDetails);
			
			Map<String,Object> updates = new HashMap<String,Object>();
			updates.put(etTripName.getText().toString(), tripCode);
			Firebase tripsref =fireBaseRef.child("users").child(userName).child("MyTrips");
			tripsref.updateChildren(updates);
			
			
			
			/*
			fireBaseRef.child("trips").child(tripCode).child("Driver")
					.setValue(userName);	
			Firebase tripRef = fireBaseRef.child("trips").child(tripCode);
			tripRef.child("Date").setValue(etDate.getText().toString());
			tripRef.child("TripName").setValue(etTripName.getText().toString());			
			tripRef.child("Time").setValue(time);
			tripRef.child("Destination").setValue(etDestination.getText().toString());	
			*/
					
			sendEmails(tripCode);
		}

	}

	private String genRandomTripCode() {
		String code = String.valueOf((int)(Math.random() * 99999));		
		return code;
	}

	String parseEmail(String email) {
		return email.substring(0, email.indexOf("@"));
	}
	
	private void sendEmails(String TripCode){
		String passgrEmails = etEmailAddresses.getText().toString();
		String [] indvdlEmails = passgrEmails.split(",");
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		SendGrid sendGrid = new SendGrid("anishk25", "25ANUfeb");
		
		for(String email : indvdlEmails){
			sendGrid.addTo(email);		
			sendGrid.setFrom(useremail);
			sendGrid.setSubject(fullName +" has invited to you a Carpool!");
			sendGrid.setText("Enter the code " + TripCode + " in the following link:" + '\n'+
					"http://polar-basin-3587.herokuapp.com" + '\n' + "Trip Details" + '\n'+"Driver: " + fullName + '\n'+"Destination: " + etDestination.getText().toString());		
		}
		sendGrid.send();
	}
	
	private void getName(Firebase nameRef){			
		
		nameRef.addValueEventListener(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				fullName = snapshot.getValue().toString();
				int duration = Toast.LENGTH_LONG;
				Toast toast = Toast.makeText(getActivity(), "Logged in as "+fullName,duration);
				toast.show();
				
			}
			
			@Override
			public void onCancelled(FirebaseError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
	}
}

