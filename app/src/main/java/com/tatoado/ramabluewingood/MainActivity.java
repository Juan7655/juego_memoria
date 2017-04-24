package com.tatoado.ramabluewingood;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    Button btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11,btn12,btn13,btn14,btn15,btn16,btn17,btn18,btn19,btn20,btn21,btn22,btn23,btn24,btn25,btn26,btn27,btn28,btn29;
    TextView txtSendorLDR;
    Handler bluetoothIn;
    private boolean fck;
    private static String num1;
    private static String num2;
    private static String num3;
    final int handlerState = 0;        				 //used to identify handler message
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();

    private ConnectedThread mConnectedThread;

    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // String for MAC address
    private static String address = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        fck=false;
        btn0 = (Button) findViewById(R.id.button0);
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);
        btn5 = (Button) findViewById(R.id.button5);
        btn6 = (Button) findViewById(R.id.button6);
        btn7 = (Button) findViewById(R.id.button7);
        btn8 = (Button) findViewById(R.id.button8);
        btn9 = (Button) findViewById(R.id.button9);
        btn10 = (Button) findViewById(R.id.button10);
        btn11 = (Button) findViewById(R.id.button11);
        btn12 = (Button) findViewById(R.id.button12);
        btn13 = (Button) findViewById(R.id.button13);
        btn14 = (Button) findViewById(R.id.button14);
        btn15 = (Button) findViewById(R.id.button15);
        btn16 = (Button) findViewById(R.id.button16);
        btn17 = (Button) findViewById(R.id.button17);
        btn18 = (Button) findViewById(R.id.button18);
        btn19 = (Button) findViewById(R.id.button19);
        btn20 = (Button) findViewById(R.id.button20);
        btn21 = (Button) findViewById(R.id.button21);
        btn22 = (Button) findViewById(R.id.button22);
        btn23 = (Button) findViewById(R.id.button23);
        btn24 = (Button) findViewById(R.id.button24);
        btn25 = (Button) findViewById(R.id.button25);
        btn26 = (Button) findViewById(R.id.button26);
        btn27 = (Button) findViewById(R.id.button27);
        btn28 = (Button) findViewById(R.id.button28);
        btn29 = (Button) findViewById(R.id.button29);


        bluetoothIn = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState) {
                    //if message is what we want
                    String readMessage = (String) msg.obj;                                                                // msg.arg1 = bytes from connect thread
                    recDataString.append(readMessage);      								//keep appending to string until ~
                    int endOfLineIndex = recDataString.indexOf("~");                    // determine the end-of-line
                    if (endOfLineIndex > 0) {                                           // make sure there data before ~
                        String dataInPrint = recDataString.substring(0, endOfLineIndex);    // extract string

                        int dataLength = dataInPrint.length();							//get length of data received

                        if (recDataString.charAt(0) == '#')								//if it starts with # we know it is what we are looking for
                        {
                            String sensor0 = recDataString.substring(1, 5);             //get sensor value from string between indices 1-5
                            String sensor1 = recDataString.substring(6, 10);            //same again...
                            String sensor2 = recDataString.substring(11, 15);
                            String sensor3 = recDataString.substring(16, 20);


                        }
                        recDataString.delete(0, recDataString.length()); 					//clear all string data
                        // strIncom =" ";
                        dataInPrint = " ";
                    }
                }
            }
        };

        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();





            btn0.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if(!fck) {
                        num3 = "1";
                        num1 = "0";
                        fck = true;
                        mConnectedThread.write(num3 + ":" + num1);
                    }else{
                        num3 = "2";
                        num2="0";
                        fck=false;
                        mConnectedThread.write(num3+":"+num2);
                    }
                }
            });
            btn1.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if(!fck) {
                        num3 = "1";
                        num1 = "1";
                        fck = true;
                        mConnectedThread.write(num3 + ":" + num1);
                    }else{
                        num3 = "2";
                        num2="1";
                        fck=false;
                        mConnectedThread.write(num3+":"+num2);
                    }
                }
            });
            btn2.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if(!fck) {
                        num3 = "1";
                        num1 = "2";
                        fck = true;
                        mConnectedThread.write(num3 + ":" + num1);
                    }else{
                        num3 = "2";
                        num2="2";
                        fck=false;
                        mConnectedThread.write(num3+":"+num2);
                    }
                }
            });
            btn3.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if(!fck) {
                        num3 = "1";
                        num1 = "3";
                        fck = true;
                        mConnectedThread.write(num3 + ":" + num1);
                    }else{
                        num3 = "2";
                        num2="3";
                        fck=false;
                        mConnectedThread.write(num3+":"+num2);
                    }
                }
            });
            btn4.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if(!fck) {
                        num3 = "1";
                        num1 = "4";
                        fck = true;
                        mConnectedThread.write(num3 + ":" + num1);
                    }else{
                        num3 = "2";
                        num2="4";
                        fck=false;
                        mConnectedThread.write(num3+":"+num2);
                    }
                }
            });
            btn5.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if(!fck) {
                        num3 = "1";
                        num1 = "5";
                        fck = true;
                        mConnectedThread.write(num3 + ":" + num1);
                    }else{
                        num3 = "2";
                        num2="5";
                        fck=false;
                        mConnectedThread.write(num3+":"+num2);
                    }
                }
            });
            btn6.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if(!fck) {
                        num3 = "1";
                        num1 = "6";
                        fck = true;
                        mConnectedThread.write(num3 + ":" + num1);
                    }else{
                        num3 = "2";
                        num2="6";
                        fck=false;
                        mConnectedThread.write(num3+":"+num2);
                    }
                }
            });
            btn7.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if(!fck) {
                        num3 = "1";
                        num1 = "7";
                        fck = true;
                        mConnectedThread.write(num3 + ":" + num1);
                    }else{
                        num3 = "2";
                        num2="7";
                        fck=false;
                        mConnectedThread.write(num3+":"+num2);
                    }
                }
            });
            btn8.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if(!fck) {
                        num3 = "1";
                        num1 = "8";
                        fck = true;
                        mConnectedThread.write(num3 + ":" + num1);
                    }else{
                        num3 = "2";
                        num2="8";
                        fck=false;
                        mConnectedThread.write(num3+":"+num2);
                    }
                }
            });
            btn9.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if(!fck) {
                        num3 = "1";
                        num1 = "9";
                        fck = true;
                        mConnectedThread.write(num3 + ":" + num1);
                    }else{
                        num3 = "2";
                        num2="9";
                        fck=false;
                        mConnectedThread.write(num3+":"+num2);
                    }
                }
            });
            btn10.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if(!fck) {
                        num3 = "1";
                        num1 = "10";
                        fck = true;
                        mConnectedThread.write(num3 + ":" + num1);
                    }else{
                        num3 = "2";
                        num2="10";
                        fck=false;
                        mConnectedThread.write(num3+":"+num2);
                    }
                }
            });
            btn11.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if(!fck) {
                        num3 = "1";
                        num1 = "11";
                        fck = true;
                        mConnectedThread.write(num3 + ":" + num1);
                    }else{
                        num3 = "2";
                        num2="11";
                        fck=false;
                        mConnectedThread.write(num3+":"+num2);
                    }
                }
            });
            btn12.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if(!fck) {
                        num3 = "1";
                        num1 = "12";
                        fck = true;
                        mConnectedThread.write(num3 + ":" + num1);
                    }else{
                        num3 = "2";
                        num2="12";
                        fck=false;
                        mConnectedThread.write(num3+":"+num2);
                    }
                }
            });
            btn13.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if(!fck) {
                        num3 = "1";
                        num1 = "13";
                        fck = true;
                        mConnectedThread.write(num3 + ":" + num1);
                    }else{
                        num3 = "2";
                        num2="13";
                        fck=false;
                        mConnectedThread.write(num3+":"+num2);
                    }
                }
            });
            btn14.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if(!fck) {
                        num3 = "1";
                        num1 = "14";
                        fck = true;
                        mConnectedThread.write(num3 + ":" + num1);
                    }else{
                        num3 = "2";
                        num2="14";
                        fck=false;
                        mConnectedThread.write(num3+":"+num2);
                    }
                }
            });
            btn15.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if(!fck) {
                        num3 = "1";
                        num1 = "15";
                        fck = true;
                        mConnectedThread.write(num3 + ":" + num1);
                    }else{
                        num3 = "2";
                        num2="15";
                        fck=false;
                        mConnectedThread.write(num3+":"+num2);
                    }
                }
            });
            btn16.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if(!fck) {
                        num3 = "1";
                        num1 = "16";
                        fck = true;
                        mConnectedThread.write(num3 + ":" + num1);
                    }else{
                        num3 = "2";
                        num2="16";
                        fck=false;
                        mConnectedThread.write(num3+":"+num2);
                    }
                }
            });
            btn17.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if(!fck) {
                        num3 = "1";
                        num1 = "17";
                        fck = true;
                        mConnectedThread.write(num3 + ":" + num1);
                    }else{
                        num3 = "2";
                        num2="17";
                        fck=false;
                        mConnectedThread.write(num3+":"+num2);
                    }
                }
            });
            btn18.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if(!fck) {
                        num3 = "1";
                        num1 = "18";
                        fck = true;
                        mConnectedThread.write(num3 + ":" + num1);
                    }else{
                        num3 = "2";
                        num2="18";
                        fck=false;
                        mConnectedThread.write(num3+":"+num2);
                    }
                }
            });
            btn19.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if(!fck) {
                        num3 = "1";
                        num1 = "19";
                        fck = true;
                        mConnectedThread.write(num3 + ":" + num1);
                    }else{
                        num3 = "2";
                        num2="19";
                        fck=false;
                        mConnectedThread.write(num3+":"+num2);
                    }
                }
            });            btn20.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if(!fck) {
                        num3 = "1";
                        num1 = "20";
                        fck = true;
                        mConnectedThread.write(num3 + ":" + num1);
                    }else{
                        num3 = "2";
                        num2="20";
                        fck=false;
                        mConnectedThread.write(num3+":"+num2);
                    }
                }
            });
            btn21.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if(!fck) {
                        num3 = "1";
                        num1 = "21";
                        fck = true;
                        mConnectedThread.write(num3 + ":" + num1);
                    }else{
                        num3 = "2";
                        num2="21";
                        fck=false;
                        mConnectedThread.write(num3+":"+num2);
                    }
                }
            });
            btn22.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if(!fck) {
                        num3 = "1";
                        num1 = "22";
                        fck = true;
                        mConnectedThread.write(num3 + ":" + num1);
                    }else{
                        num3 = "2";
                        num2="22";
                        fck=false;
                        mConnectedThread.write(num3+":"+num2);
                    }
                }
            });
            btn23.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if(!fck) {
                        num3 = "1";
                        num1 = "23";
                        fck = true;
                        mConnectedThread.write(num3 + ":" + num1);
                    }else{
                        num3 = "2";
                        num2="23";
                        fck=false;
                        mConnectedThread.write(num3+":"+num2);
                    }
                }
            });
            btn24.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if(!fck) {
                        num3 = "1";
                        num1 = "24";
                        fck = true;
                        mConnectedThread.write(num3 + ":" + num1);
                    }else{
                        num3 = "2";
                        num2="24";
                        fck=false;
                        mConnectedThread.write(num3+":"+num2);
                    }
                }
            });            btn25.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if(!fck) {
                        num3 = "1";
                        num1 = "25";
                        fck = true;
                        mConnectedThread.write(num3 + ":" + num1);
                    }else{
                        num3 = "2";
                        num2="25";
                        fck=false;
                        mConnectedThread.write(num3+":"+num2);
                    }
                }
            });
            btn26.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if(!fck) {
                        num3 = "1";
                        num1 = "26";
                        fck = true;
                        mConnectedThread.write(num3 + ":" + num1);
                    }else{
                        num3 = "2";
                        num2="26";
                        fck=false;
                        mConnectedThread.write(num3+":"+num2);
                    }
                }
            });
            btn27.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if(!fck) {
                        num3 = "1";
                        num1 = "27";
                        fck = true;
                        mConnectedThread.write(num3 + ":" + num1);
                    }else{
                        num3 = "2";
                        num2="27";
                        fck=false;
                        mConnectedThread.write(num3+":"+num2);
                    }
                }
            });
            btn28.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if(!fck) {
                        num3 = "1";
                        num1 = "28";
                        fck = true;
                        mConnectedThread.write(num3 + ":" + num1);
                    }else{
                        num3 = "2";
                        num2="28";
                        fck=false;
                        mConnectedThread.write(num3+":"+num2);
                    }
                }
            });
            btn29.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if(!fck) {
                        num3 = "1";
                        num1 = "29";
                        fck = true;
                        mConnectedThread.write(num3 + ":" + num1);
                    }else{
                        num3 = "2";
                        num2="29";
                        fck=false;
                        mConnectedThread.write(num3+":"+num2);
                    }
                }
            });


    }


    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }

    @Override
    public void onResume() {
        super.onResume();

        //Get MAC address from DeviceListActivity via intent
        Intent intent = getIntent();

        //Get the MAC address from the DeviceListActivty via EXTRA
        address = intent.getStringExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS);

        //create device and set the MAC address
        //Log.i("ramiro", "adress : " + address);
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "La creacción del Socket fallo", Toast.LENGTH_LONG).show();
        }
        // Establish the Bluetooth socket connection.
        try
        {
            btSocket.connect();
        } catch (IOException e) {
            try
            {
                btSocket.close();
            } catch (IOException e2)
            {
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
    public void onPause()
    {
        super.onPause();
        try
        {
            //Don't leave Bluetooth sockets open when leaving activity
            btSocket.close();
        } catch (IOException e2) {
            //insert code to deal with this
        }
    }

    //Checks that the Android device Bluetooth is available and prompts to be turned on if off
    private void checkBTState() {

        if(btAdapter==null) {
            Toast.makeText(getBaseContext(), "El dispositivo no soporta bluetooth", Toast.LENGTH_LONG).show();
        } else {
            if (btAdapter.isEnabled()) {
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    //create new class for connect thread
    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }


        public void run() {
            byte[] buffer = new byte[256];
            int bytes;

            // Keep looping to listen for received messages
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);        	//read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    // Send the obtained bytes to the UI Activity via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }
        //write method
        public void write(String input) {
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

