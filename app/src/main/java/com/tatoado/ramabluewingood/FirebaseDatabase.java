package com.tatoado.ramabluewingood;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by juandavid on 11/05/17.
 */

class FirebaseDatabase implements ValueEventListener {

	private static FirebaseDatabase instance = new FirebaseDatabase();
	@SuppressWarnings("FieldCanBeLocal")
	private final String _DATABASE_NAME = "GAME", _BEST_SCORE = "BEST_SCORE",
			_BEST_TIME = "BEST_TIME", _HISTORY = "HISTORY", _SCORE = "SCORE", _TIME = "TIME";
	private DatabaseReference database = com.google.firebase.database.FirebaseDatabase.getInstance().getReference(_DATABASE_NAME);
	private int bestScore = 0, bestTime = 0;

	private FirebaseDatabase() {
		database.addValueEventListener(this);
	}

	static FirebaseDatabase getInstance() {
		return instance;
	}

	public void insertGame(String date, int seconds, int score) {
		database.child(_HISTORY).child(date).child(_SCORE).setValue(score);
		database.child(_HISTORY).child(date).child(_TIME).setValue(seconds);

		if(seconds < bestTime) database.child(_BEST_TIME).setValue(seconds);
		if(score > bestScore) database.child(_BEST_SCORE).setValue(score);
	}

	public int getBestScore() {
		return bestScore;
	}

	public int getBestTime() {
		return bestTime;
	}

	@Override
	public void onDataChange(DataSnapshot dataSnapshot) {
		bestScore = (int) (long) dataSnapshot.child(_BEST_SCORE).getValue();
		bestTime = (int) (long) dataSnapshot.child(_BEST_TIME).getValue();
	}

	@Override
	public void onCancelled(DatabaseError databaseError) {

	}
}
