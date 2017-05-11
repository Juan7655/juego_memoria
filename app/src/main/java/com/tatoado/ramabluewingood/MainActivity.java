package com.tatoado.ramabluewingood;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import android.widget.Chronometer;

public class MainActivity extends Activity {


	//Objeto de cronometro
	Chronometer chrono;
	public static String TextoTiempo;
	// SPP UUID service - this should work for most devices
	private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	static Handler bluetoothIn;
	public static boolean newGame = true;
	final int handlerState = 0;//used to identify handler message
	private final GameManager manager = GameManager.getInstance();
	Button[] btn = new Button[30];
	private TextView patoa;
	private boolean fck = true;
	private BluetoothAdapter btAdapter;
	private BluetoothSocket btSocket;
	private StringBuilder recDataString = new StringBuilder();
	private ConnectedThread mConnectedThread;
	private int btn1 = 0, btn2 = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		patoa = (TextView) findViewById(R.id.PATOA);

		chrono = (Chronometer) findViewById(R.id.chronometer2);
		Handler mHandler = new Handler();
		chrono.start();

		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				TextoTiempo=chrono.getText().toString();
				mConnectedThread.write("t");
				startActivity(new Intent(getBaseContext(), FinishActivity.class).putExtra("TIEMPO",TextoTiempo));
			}
		},301000L);



		//se establece la configuración de los datos de inicio
			manager.resetGame();

			bluetoothIn = new Handler() {
				public void handleMessage(android.os.Message msg) {
					//Se establece el Handler para los datos bluetooth recibidos
					if (msg.what == handlerState) {
						String readMessage = (String) msg.obj;
						recDataString.append(readMessage);
						int endOfLineIndex = recDataString.indexOf("/"),
								startOfLineIndex = recDataString.indexOf("@");
						if(startOfLineIndex != -1 && endOfLineIndex != -1) {
							readMessage = recDataString.substring(startOfLineIndex + 1, endOfLineIndex);
							//se utilliza el mensaje hasta el simbolo de fin de datos
							if (endOfLineIndex > 0) {
								int readValue = -1;
								try {
									readValue = Integer.parseInt(readMessage);
								} catch (NumberFormatException ignored) {
									//si no se puede convertir a int, se deja el valor inicial de -1
								}
								patoa.setText(String.valueOf(readValue));
								manager.addPlayerPoint(readValue == 1);//si el valor es 1, se agrega punto al jugador
								((TextView) findViewById(R.id.score_1)).setText(getString(R.string.score_text) + ": " + manager.getPlayerPoints());
								if (manager.getPlayerPoints() >= 15) {
									TextoTiempo=chrono.getText().toString();
									mConnectedThread.write("t");
									startActivity(new Intent(getBaseContext(), FinishActivity.class));

								}
								if (readValue == 0) {//se vuelven a habilitar los botones si no hubo punto
									btn[btn1].setEnabled(true);
									btn[btn2].setEnabled(true);
								}else if(readValue == 1){
									btn[btn1].setEnabled(false);
									btn[btn2].setEnabled(false);
								}
							}
						}
						recDataString.delete(0, recDataString.length());//se reinicia el mensaje
					}
				}
			};

			btAdapter = BluetoothAdapter.getDefaultAdapter();// get Bluetooth adapter
			checkBTState();


		for (int i = 0; i < 30; i++) {//crea los botones dinámicamente y les asigna el listener correspondiente
			final int id = i;
			btn[i] = (Button) findViewById(getButtonId(i));
			btn[i].setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					//si el jugador es 1, se envia la correspondiente informacion por BT
						mConnectedThread.write("@" + (fck ? "1" : "2") + ":" + String.valueOf(id + 1) + "/");
					if (fck) btn1 = id;//se determina si es la primera o segudna seleccion de led
					else btn2 = id;
					fck = !fck;
				}
			});
		}

	}

	/**
	 * Retorna el id del boton en el layout dependiendo del id enviado como parametro
	 *
	 * @param id el id del botón del que se requiere el identificador de layout
	 * @return el identificador del botón requerido
	 */
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

	private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
		return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
		//creates secure outgoing connection with BT device using UUID
	}

	@Override
	public void onResume() {
		super.onResume();
			//Get MAC address from DeviceListActivity via intent
			Intent intent = getIntent();
			//Get the MAC address from the DeviceListActivty via EXTRA
			String address = intent.getStringExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS);

			//create device and set the MAC address
			BluetoothDevice device = btAdapter.getRemoteDevice(address);

			try {
				btSocket = createBluetoothSocket(device);
			} catch (IOException e) {
				Toast.makeText(getBaseContext(), "La creacción del Socket fallo", Toast.LENGTH_LONG).show();
			}
			try {// Establish the Bluetooth socket connection.
				btSocket.connect();
			} catch (IOException e) {
				try {
					btSocket.close();
				} catch (IOException ignored) {
				}
			}
			mConnectedThread = new ConnectedThread(btSocket);
			mConnectedThread.start();

			//I send a character when resuming.beginning transmission to check device is connected
			//If it is not an exception will be thrown in the write method and finish() will be called
		if(newGame){
			mConnectedThread.write("t");
			newGame = !newGame;
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		try {//Don't leave Bluetooth sockets open when leaving activity
			btSocket.close();
		} catch (IOException ignored) {
		}
	}

	private void checkBTState() {//Checks that the Android device Bluetooth is available and prompts to be turned on if off
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

			try {//Create I/O streams for connection
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


			while (true)// Keep looping to listen for received messages
				try {
					bytes = mmInStream.read(buffer);            //read bytes from input buffer
					String readMessage = new String(buffer, 0, bytes);
					// Send the obtained bytes to the UI Activity via handler
					bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
				} catch (IOException e) {
					break;
				}
		}


		void write(String input) {//write method
			byte[] msgBuffer = input.getBytes();//converts entered String into bytes
			try {//write bytes over BT connection via outstream
				mmOutStream.write(msgBuffer);
			} catch (IOException e) {//if you cannot write, close the application
				Toast.makeText(getBaseContext(), "La Conexión falló", Toast.LENGTH_LONG).show();
				Log.d("WriteMethod", e.toString());
				finish();
			}
		}
	}
}