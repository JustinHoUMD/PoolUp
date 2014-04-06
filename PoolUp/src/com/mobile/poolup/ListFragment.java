package com.mobile.poolup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.ValueEventListener;
import com.firebase.simplelogin.SimpleLogin;
import com.firebase.simplelogin.SimpleLoginAuthenticatedHandler;
import com.firebase.simplelogin.User;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class ListFragment extends android.support.v4.app.ListFragment {
	
	
	private ArrayList<String> tripNames; 
	private HashMap<String,String> tripHash;
	
	
	private Firebase fireBaseRef;
	private String userEmail,userName;
	
	 @Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    fireBaseRef = new Firebase("https://sizzling-fire-7279.firebaseio.com/");	
	    tripNames = new ArrayList<String>();
	    tripHash = new HashMap<String,String>();
	    checkUser();	    
	  }
	 
	

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		String tripName = ((TextView)v).getText().toString();
		String tripCode = tripHash.get(tripName);
		Bundle bundle = new Bundle();
		bundle.putString("TRIP_CODE", tripCode);
		Intent intent = new Intent("android.intent.action.TRIPINFOACTIVITY");
		intent.putExtras(bundle);
		startActivity(intent);
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
					userEmail = user.getEmail();
					userName= parseEmail(userEmail);
					populateTripNames();					
				}
			}
		});
	}
	

	private String parseEmail(String email) {
		return email.substring(0, email.indexOf("@"));
	}
	
	private void populateTripNames(){
		
		Firebase currentUser = fireBaseRef.child("users").child(userName).child("MyTrips");
		
		currentUser.addValueEventListener(new ValueEventListener() {
		    @Override
		    public void onDataChange(DataSnapshot snapshot) {
		    	GenericTypeIndicator<Map<String, Object>> t = new GenericTypeIndicator<Map<String, Object>>() {};
		        Map<String, Object> tripCodes = snapshot.getValue(t);
		        
		        if(tripCodes != null){
		        	for(String k : tripCodes.keySet()){	
		        		tripHash.put(k, tripCodes.get(k).toString());
		        		tripNames.add(k);
		        	}
		        	setListAdapter(null);
		        	ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
							android.R.layout.simple_list_item_1,tripNames);
					setListAdapter(adapter);
		        }
		    }

			@Override
			public void onCancelled(FirebaseError arg0) {
				// TODO Auto-generated method stub
				
			}

		   
		});
	}
	
	

		
		
		
		
	
}
