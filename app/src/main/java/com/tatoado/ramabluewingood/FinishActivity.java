package com.tatoado.ramabluewingood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FinishActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_finish);

		findViewById(R.id.return_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				MainActivity.newGame = true;
				startActivity(new Intent(getBaseContext(), SelectUserActivity.class));
				System.exit(0);
			}
		});
	}
}
