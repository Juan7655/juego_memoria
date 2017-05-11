package com.tatoado.ramabluewingood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SelectUserActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_user);

		findViewById(R.id.button_player1).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//MainActivity.player = 1;
				startActivity(new Intent(getApplicationContext(), DeviceListActivity.class));
				finish();
			}
		});

		findViewById(R.id.button_player2).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//MainActivity.player = 2;
				Intent intent = new Intent(getBaseContext(), MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		});
	}
}
