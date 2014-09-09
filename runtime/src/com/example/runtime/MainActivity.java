
package com.example.runtime;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends Activity {
    TextView text;  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        text = (TextView) findViewById(R.id.textView1);  
        
        Button btn_ls = (Button) findViewById(R.id.button1);  
        btn_ls.setOnClickListener(new OnClickListener() {  
            public void onClick(View v) {  
                //String keyCommand = "input keyevent " + KeyEvent.KEYCODE_MENU;  
                //Runtime runtime = Runtime.getRuntime();  
                //Process proc = runtime.exec(keyCommand);  
                //do_exec("input keyevent "+KeyEvent.KEYCODE_MENU);  
                RootContext.getInstance().runCommand("input keyevent " + KeyEvent.KEYCODE_BACK);
            }             
        });  
        Button btn_cat = (Button) findViewById(R.id.button2);  
        btn_cat.setOnClickListener(new OnClickListener() {  
            public void onClick(View v) {                 
                //do_exec("cat /proc/version");  
                do_exec("pwd ");
            }             
        });          
        Button btn_rm = (Button) findViewById(R.id.button3);  
        btn_rm.setOnClickListener(new OnClickListener() {  
            public void onClick(View v) {                 
                do_exec("rm /mnt/sdcard/1.jpg");  
            }             
        });      
        Button btn_sh = (Button) findViewById(R.id.button4);  
        btn_sh.setOnClickListener(new OnClickListener() {  
            public void onClick(View v) {                 
                do_exec("/system/bin/sh /mnt/sdcard/test.sh 123");  
            }             
        });       
        
    }

    
    String do_exec(String cmd) {  
        String s = "/n";  
        try {  
            Process p = Runtime.getRuntime().exec(cmd);  
            BufferedReader in = new BufferedReader(  
                                new InputStreamReader(p.getInputStream()));  
            String line = null;  
            while ((line = in.readLine()) != null) {  
                s += line + "/n";                 
            }  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        text.setText(s);  
        return cmd;       
    }  

    void do_root_exec(String cmd) {
        try {
            Process localProcess = Runtime.getRuntime().exec("su");
            DataOutputStream localDataOutputStream = new DataOutputStream(
                    localProcess.getOutputStream());
            localDataOutputStream.writeBytes("input keyevent " + KeyEvent.KEYCODE_BACK+"\n");
            localDataOutputStream.flush();
            localDataOutputStream.close();
            localProcess.waitFor();
            // / String str = new
            // DataInputStream(localProcess.getErrorStream()).readLine();
            localProcess.destroy();
        } catch (Exception localException) {
            Log.i("AAAAAAAAAAAA", localException.getMessage().toString());
            Toast.makeText(MainActivity.this, localException.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
        }
    }

   
   
}
