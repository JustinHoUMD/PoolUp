package com.mobile.poolup;

import java.util.HashMap;
import java.util.Map;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.ValueEventListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
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
		
	}
	
	
	
	
		

	
	
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
		        	
		        	if(!s.equals("Driver") && !s.equals("TripName")){
		        		tv.setText(k+ ":"+s);
		        		tv.setTextColor(Color.parseColor("#000000"));
		        		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
		        		lm.addView(tv);
		        	}
		        	
		        	
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
		        
		        
		        if(tripCodes != null){
		        LinearLayout lm = (LinearLayout) findViewById(R.id.lTripLayout);		        
		        for(String k : tripCodes.keySet()){ 	
		        	Map<String, Object> passgrDetails = (Map<String, Object>) tripCodes.get(k);
		        	
		        	 		        	
		        	LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		        	Button myButton = new Button(getApplicationContext());
		        	myButton.setText(k);
		        	lm.addView(myButton,lp);
		        	
		        	final String address = passgrDetails.get("street_address").toString();
		        	myButton.setOnClickListener(new OnClickListener() {						
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://maps.google.com/maps?" + "saddr="+ 
										"My Location" + "&daddr="+address));
							 intent.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity");
							 startActivity(intent);
						}
					});	 
		        }
					
		        }		        
		     
		    }
			@Override
			public void onCancelled(FirebaseError arg0) {
				// TODO Auto-generated method stub
				
			}

		   
		});
	}








	
	
	

}
