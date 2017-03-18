package com.takeoffandroid.shimmercontactsview.utils;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Logger {
	
	private static final String TAG = "Addo";
	
	static String className;
	static String methodName;
	static int lineNumber;
    private static boolean LOG_ENABLED = true;

    private static String createLog( String log ) {

        return "[" + methodName + ":" + lineNumber + "]" + log;
	}
	
	private static void getMethodNames(StackTraceElement[] sElements){
		className = sElements[1].getFileName();
		methodName = sElements[1].getMethodName();
		lineNumber = sElements[1].getLineNumber();
	}
	
	public static void d(String data){
		if(!LOG_ENABLED)
			return;
		getMethodNames(new Throwable ().getStackTrace());
		Log.d (className, createLog (data));
	}
	
	public static void v(String data){
		if(!LOG_ENABLED)
			return;
		getMethodNames(new Throwable ().getStackTrace());
		Log.v (className, createLog (data));
	}
	
	public static void i(String data){
		if(!LOG_ENABLED)
			return;
		getMethodNames(new Throwable ().getStackTrace());
		Log.i (className, createLog (data));
	}
	
	public static void e(String data){
		if(!LOG_ENABLED)
			return;
		getMethodNames(new Throwable ().getStackTrace());
		Log.e (className, createLog (data));
	}
	
	public static void ToastMessage(Context context,String message){
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}


}
