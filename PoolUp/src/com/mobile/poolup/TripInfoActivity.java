package com.mobile.poolup;

import java.util.HashMap;
import java.util.Map;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.ValueEventListener;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TripInfoActivity extends Activity{

	String tripCode;
	Firebase fireBaseRef;
	HashMap<String,String> tripDetails;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trip_info);
		
		fireBaseRef = new Firebase("https://sizzling-fire-7279.firebaseio.com/");	
		tripDetails = new HashMap<String, String>();
		Bundle bundle = getIntent().getExtras();
		tripCode = bundle.getString("TRIP_CODE");
		Log.d("DEBUG", "Received code: " + tripCode);		
		getTripDetails();
		getPassengers(); 
		//displayDetails();
		
		
		
	}
	
	/*private void displayDetails(){
		LinearLayout ll = new LinearLayout(this);
		TextView tv = new TextView(this);
		for(String key: tripDetails.keySet()){
			tv.setText(tripDetails.get(key));
			ll.addView(tv);
		}*/
		

	
	
	private void getTripDetails(){
		Firebase currentUser = fireBaseRef.child("trips").child(tripCode).child("TripDetails");		
		currentUser.addValueEventListener(new ValueEventListener() {
		    @Override
		    public void onDataChange(DataSnapshot snapshot) {
		    	GenericTypeIndicator<Map<String, Object>> t = new GenericTypeIndicator<Map<String, Object>>() {};
		        Map<String, Object> tripCodes = snapshot.getValue(t);       
		        
		        
		        LinearLayout lm = (LinearLayout) findViewById(R.id.lTripLayout);
		        for(String k : tripCodes.keySet()){	
		        	TextView tv = new TextView(getApplicationContext());
		        	String s = tripCodes.get(k).toString();
		        	tv.setText(k+ ":"+s);		        	
					lm.addView(tv);
		        }		        
		     
		    }
			@Override
			public void onCancelled(FirebaseError arg0) {
				// TODO Auto-generated method stub
				
			}

		   
		});
	}
	
	private void getPassengers(){
		Firebase currentUser = fireBaseRef.child("trips").child(tripCode).child("Passengers");		
		currentUser.addValueEventListener(new ValueEventListener() {
		    @Override
		    public void onDataChange(DataSnapshot snapshot) {
		    	GenericTypeIndicator<Map<String, Object>> t = new GenericTypeIndicator<Map<String, Object>>() {};
		        Map<String, Object> tripCodes = snapshot.getValue(t);       
		        
		        
		        LinearLayout lm = (LinearLayout) findViewById(R.id.lTripLayout);
		        for(String k : tripCodes.keySet()){	
		        	TextView tv = new TextView(getApplicationContext());
		        	String s = k.toString(); 
		        	tv.setText(s);		        	
					lm.addView(tv);
		        }		        
		     
		    }
			@Override
			public void onCancelled(FirebaseError arg0) {
				// TODO Auto-generated method stub
				
			}

		   
		});
	}
	
	
	

}
