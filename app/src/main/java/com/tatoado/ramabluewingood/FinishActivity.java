package com.tatoado.ramabluewingood;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;

import java.util.Date;


public class FinishActivity extends AppCompatActivity {

private FirebaseDatabase database = FirebaseDatabase.getInstance();
	private GameManager manager = GameManager.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_finish);

		String textoTiempo = getIntent().getStringExtra("TIEMPO");

		CharSequence s  = DateFormat.format("d-MM-yy hh:mm", new Date().getTime() + 1);
		if(textoTiempo == null) textoTiempo = "0:0";
		database.insertGame(s.toString(), formatTime(getIntent().getStringExtra("TIEMPO")), manager.getPlayerPoints());

		((TextView) findViewById(R.id.time_text)).setText(textoTiempo);
		((TextView) findViewById(R.id.score_text)).setText(String.valueOf(manager.getPlayerPoints()));

		findViewById(R.id.return_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				MainActivity.newGame = true;
				startActivity(new Intent(getBaseContext(), SelectUserActivity.class));
				System.exit(0);
			}
		});
	}

	public int formatTime(@NonNull String entry){
		int minutes = Integer.parseInt(entry.substring(0, entry.indexOf(":"))),
				seconds = Integer.parseInt(entry.substring(entry.indexOf(":") + 1));

		return minutes * 60 + seconds;
	}
}
