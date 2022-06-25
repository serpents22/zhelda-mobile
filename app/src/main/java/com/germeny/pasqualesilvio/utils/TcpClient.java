package com.germeny.pasqualesilvio.utils;

import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class TcpClient {

    public static final String TAG = TcpClient.class.getSimpleName();
    public static final String SERVER_IP = "192.168.1.1"; //server IP address
//    public static final String SERVER_IP = "10.0.2.2"; //server IP address android emu

    public static final int SERVER_PORT = 1884;

    // message to send to the server
    private String mServerMessage;

    // sends message received notifications
    private OnMessageReceived mMessageListener = null;

    // while this is true, the server will continue running
    private boolean mRun = false;

    // used to send messages
    private PrintWriter mBufferOut;

    // used to read messages from the server
    private BufferedReader mBufferIn;

    /**
     * Constructor of the class. OnMessagedReceived listens for the messages received from server
     */
    public TcpClient(OnMessageReceived listener) {
        mMessageListener = listener;
    }

    /**
     * Sends the message entered by client to the server
     *
     * @param message text entered by client
     */
    public void sendMessage(final String message) {
        Runnable runnable = () -> {
            if (mBufferOut != null) {
                Log.d(TAG, "Sending: " + message);
                mBufferOut.print(message);
                mBufferOut.flush();
//                mBufferOut.close();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    /**
     * Close the connection and release the members
     */
    public void stopClient() {

        mRun = false;

        if (mBufferOut != null) {
            mBufferOut.flush();
            mBufferOut.close();
        }

        mMessageListener = null;
        mBufferIn = null;
        mBufferOut = null;
        mServerMessage = null;
    }

    public void run() {

        mRun = true;

        try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
            Log.e("TCP Client", "C: Connecting...");
            Socket socket = new Socket(serverAddr, SERVER_PORT);
            try {
                mBufferOut = new PrintWriter(socket.getOutputStream());
                Log.e("TCP Client", "C: Sent.");
                mBufferIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                int charsRead = 0; char[] buffer = new char[1024]; //choose your buffer size if you need other than 1024

                while (mRun) {
                    charsRead = mBufferIn.read(buffer);
                    mServerMessage = new String(buffer).substring(0, charsRead);
                    if (mServerMessage != null && mMessageListener != null) {
                        mMessageListener.messageReceived(mServerMessage);}
                    mServerMessage = null;
                }
                Log.e("RESPONSE FROM SERVER", "S: Received Message: '" + mServerMessage + "'");

            } catch (Exception e) {
                Log.e("TCP", "S: Error", e);
            } finally {
                //the socket must be closed. It is not possible to reconnect to this socket
                // after it is closed, which means a new socket instance has to be created.
                socket.close();
            }

        } catch (Exception e) {
            Log.e("TCP", "C: Error", e);
        }

    }

    //Declare the interface. The method messageReceived(String message) will must be implemented in the Activity
    //class at on AsyncTask doInBackground
    public interface OnMessageReceived {
        public void messageReceived(String message);
    }

}
