package com.blueocean.jnsinputtest;
  
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import android.net.LocalServerSocket;
import android.net.LocalSocket;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.util.Log;
import android.view.IWindowManager;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.MotionEvent.PointerCoords;  
import android.view.MotionEvent.PointerProperties;  


class JNSInputTest {
	private static final String TAG = "JNSInputTest";
	private ServerSocket mServerSocket = null;
	private Socket socket = null;
	private BufferedReader is = null;
	private PrintWriter pw;
	private static final boolean debug = true;
	
	public static void main(String[] args) {
		System.err.println("this is JNSInput");
		JNSInputTest mInput = new JNSInputTest();
		mInput.sendString();
	}
	
	public void sendString() {
		try {
			Socket socket = new Socket("127.0.0.1", 44444);
			PrintWriter mPrintWriter = new PrintWriter(socket.getOutputStream());
			is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			int x = 0;
			for (x = 0; x < 500; x +=10) {
				String cmd = "injectTouch:2:" + ((x == 0) ? MotionEvent.ACTION_DOWN : MotionEvent.ACTION_MOVE) + ":" + x +":0" +
							":" + x + ":300";
				mPrintWriter.println(cmd);
				mPrintWriter.flush();
				System.err.println("x = " + x);
				String line = is.readLine();
				Log.e(TAG, "line = " + line);
			}
			String cmd = "injectTouch:2:" + MotionEvent.ACTION_UP + ":490:0:490:300";
			mPrintWriter.println(cmd);
			mPrintWriter.flush();
			
//			mPrintWriter.println("injectTouch:2:0:400:500:300:600");
//			mPrintWriter.flush();
//			mPrintWriter.println("injectTouch:2:1:400:500:300:600");
//			mPrintWriter.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
