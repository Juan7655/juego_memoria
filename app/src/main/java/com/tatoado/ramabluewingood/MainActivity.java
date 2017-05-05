package com.tatoado.ramabluewingood;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends Activity {

	// SPP UUID service - this should work for most devices
	private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	static Handler bluetoothIn;
	final int handlerState = 0;                         //used to identify handler message
	private final String DATABASE_NAME = "GAME", PLAYER_TURN = "PLAYER_TURN",
			SELECTED_LED1 = "SELECTED_LED1", SELECTED_LED2 = "SELECTED_LED2",
			PLAYER1_POINTS = "PLAYER1_POINTS ", PLAYER2_POINTS = "PLAYER2_POINTS";
	//private final int player = 1;
	private final GameManager manager = GameManager.getInstance();
	DatabaseReference database = FirebaseDatabase.getInstance().getReference(DATABASE_NAME);
	Button[] btn = new Button[30];
	TextView patoa;
	private boolean fck;
	private BluetoothAdapter btAdapter = null;
	private BluetoothSocket btSocket = null;
	private StringBuilder recDataString = new StringBuilder();
	private ConnectedThread mConnectedThread;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		fck = true;
		patoa = (TextView) findViewById(R.id.PATOA);
		attachFirebaseDatabase();
		manager.resetGame();
		//if (player == 1) {
		database.child(PLAYER_TURN).setValue(true);
		database.child(SELECTED_LED1).setValue(-1);
		database.child(SELECTED_LED2).setValue(-1);
		database.child(PLAYER1_POINTS).setValue(manager.getPlayerPoints(1));
		database.child(PLAYER2_POINTS).setValue(manager.getPlayerPoints(2));
		//}
		bluetoothIn = new Handler() {
			public void handleMessage(android.os.Message msg) {

				if (msg.what == handlerState) {
					String readMessage = (String) msg.obj;
					recDataString.append(readMessage);
					int endOfLineIndex = recDataString.indexOf("~");
					if (endOfLineIndex > 0) {
						readMessage = recDataString.substring(endOfLineIndex - 1, endOfLineIndex);
						int lel = Integer.parseInt(readMessage);
						patoa.setText(String.valueOf(lel));
						int val = Integer.parseInt(readMessage);
						manager.addPlayerPoint(val == 1);
						database.child(PLAYER_TURN).setValue(manager.firstPlayerTurn);
						database.child(PLAYER1_POINTS).setValue(manager.getPlayerPoints(1));
						database.child(PLAYER2_POINTS).setValue(manager.getPlayerPoints(2));

						recDataString.delete(0, recDataString.length());
					} else recDataString.delete(0, recDataString.length());
				}
			}
		};

		btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
		checkBTState();

		for (int i = 0; i < 30; i++) {
			final int id = i;
			btn[i] = (Button) findViewById(getButtonId(i));
			btn[i].setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					mConnectedThread.write(fck ? "1" : "2" + ":" + String.valueOf(id));
					database.child(PLAYER_TURN).setValue(manager.firstPlayerTurn);
					database.child(fck ? SELECTED_LED1 : SELECTED_LED2).setValue(id);
					fck = !fck;
				}
			});
		}

	}

	private int getButtonId(int id) {
		switch (id) {
			case 0:
				return R.id.button0;
			case 1:
				return R.id.button1;
			case 2:
				return R.id.button2;
			case 3:
				return R.id.button3;
			case 4:
				return R.id.button4;
			case 5:
				return R.id.button5;
			case 6:
				return R.id.button6;
			case 7:
				return R.id.button7;
			case 8:
				return R.id.button8;
			case 9:
				return R.id.button9;
			case 10:
				return R.id.button10;
			case 11:
				return R.id.button11;
			case 12:
				return R.id.button12;
			case 13:
				return R.id.button13;
			case 14:
				return R.id.button14;
			case 15:
				return R.id.button15;
			case 16:
				return R.id.button16;
			case 17:
				return R.id.button17;
			case 18:
				return R.id.button18;
			case 19:
				return R.id.button19;
			case 20:
				return R.id.button20;
			case 21:
				return R.id.button21;
			case 22:
				return R.id.button22;
			case 23:
				return R.id.button23;
			case 24:
				return R.id.button24;
			case 25:
				return R.id.button25;
			case 26:
				return R.id.button26;
			case 27:
				return R.id.button27;
			case 28:
				return R.id.button28;
			case 29:
				return R.id.button29;
			default:
				return -1;
		}
	}

	private void attachFirebaseDatabase() {
		database.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {

			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});
	}

	private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

		return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
		//creates secure outgoing connecetion with BT device using UUID
	}

	@Override
	public void onResume() {
		super.onResume();

		//Get MAC address from DeviceListActivity via intent
		Intent intent = getIntent();

		//Get the MAC address from the DeviceListActivty via EXTRA
		String address = intent.getStringExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS);

		//create device and set the MAC address
		//Log.i("ramiro", "adress : " + address);
		BluetoothDevice device = btAdapter.getRemoteDevice(address);

		try {
			btSocket = createBluetoothSocket(device);
		} catch (IOException e) {
			Toast.makeText(getBaseContext(), "La creacción del Socket fallo", Toast.LENGTH_LONG).show();
		}
		// Establish the Bluetooth socket connection.
		try {
			btSocket.connect();
		} catch (IOException e) {
			try {
				btSocket.close();
			} catch (IOException e2) {
				//insert code to deal with this
			}
		}
		mConnectedThread = new ConnectedThread(btSocket);
		mConnectedThread.start();

		//I send a character when resuming.beginning transmission to check device is connected
		//If it is not an exception will be thrown in the write method and finish() will be called
		mConnectedThread.write("x");
	}

	@Override
	public void onPause() {
		super.onPause();
		try {
			//Don't leave Bluetooth sockets open when leaving activity
			btSocket.close();
		} catch (IOException e2) {
			//insert code to deal with this
		}
	}

	//Checks that the Android device Bluetooth is available and prompts to be turned on if off
	private void checkBTState() {

		if (btAdapter == null) {
			Toast.makeText(getBaseContext(), "El dispositivo no soporta bluetooth", Toast.LENGTH_LONG).show();
		} else if (!btAdapter.isEnabled()) {
			Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, 1);
		}
	}


	//create new class for connect thread
	private class ConnectedThread extends Thread {
		private final InputStream mmInStream;
		private final OutputStream mmOutStream;

		//creation of the connect thread
		ConnectedThread(BluetoothSocket socket) {
			InputStream tmpIn = null;
			OutputStream tmpOut = null;

			try {
				//Create I/O streams for connection
				tmpIn = socket.getInputStream();
				tmpOut = socket.getOutputStream();
			} catch (IOException ignored) {
			}

			mmInStream = tmpIn;
			mmOutStream = tmpOut;
		}


		public void run() {
			byte[] buffer = new byte[256];
			int bytes;

			// Keep looping to listen for received messages
			while (true) {
				try {
					bytes = mmInStream.read(buffer);            //read bytes from input buffer
					String readMessage = new String(buffer, 0, bytes);
					// Send the obtained bytes to the UI Activity via handler
					bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
				} catch (IOException e) {
					break;
				}
			}
		}

		//write method
		void write(String input) {
			byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
			try {
				mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
			} catch (IOException e) {
				//if you cannot write, close the application
				Toast.makeText(getBaseContext(), "La Conexión fallo", Toast.LENGTH_LONG).show();
				finish();

			}
		}
	}
}