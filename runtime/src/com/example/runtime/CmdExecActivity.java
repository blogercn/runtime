
package com.example.runtime;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class CmdExecActivity extends Activity {
    TextView text;  
    EditText et;
    String cmd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cmd_exec_activity);
        text = (TextView) findViewById(R.id.textView1);  
        et = (EditText)findViewById(R.id.editText1);  
                
        Button btn_ls = (Button) findViewById(R.id.button1);  
        btn_ls.setOnClickListener(new OnClickListener() {  
            public void onClick(View v) {  
                String keyCommand = "input keyevent " + KeyEvent.KEYCODE_MENU;  
                //Runtime runtime = Runtime.getRuntime();  
                //Process proc = runtime.exec(keyCommand);  
                //do_exec("input keyevent "+KeyEvent.KEYCODE_MENU); 
                cmd = et.getText().toString();
                Log.i("789", "cmd="+cmd);               
                if (cmd.length() == 0)
                {
                    do_exec("ls /");
                    Log.i("789", "111cmd="+cmd);
                }
                else
                {
                    new Thread(new Runnable(){

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            do_exec(cmd + " ");
                        }
                        
                    }).start();
                  
                    Log.i("789", "22222cmd="+cmd);
                }
            }             
        });  
        
        
    }

    
    String do_exec(String cmd) {  
        String s = "\n";  
        try {  
            Process p = Runtime.getRuntime().exec(cmd);  
            BufferedReader in = new BufferedReader(  
                                new InputStreamReader(p.getInputStream()));  
            String line = null;  
            while ((line = in.readLine()) != null) {  
                s += line + "\n";                 
            }  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        Message m = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("m_content", s);
        m.setData(bundle);
        m.what = NEW_MSG;
        myHandler.sendMessage(m);
        //text.setText(s);  
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
            Toast.makeText(CmdExecActivity.this, localException.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    private final static int NEW_MSG = 0x0010;

    private Handler myHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            Bundle bundle = msg.getData();
            String content = bundle.getString("m_content");
            
            Log.i("AAA myHandler", "content=" + content);
            switch (msg.what) {
                case NEW_MSG:
                    text.setText(content);  
                    break;
            }
            super.handleMessage(msg);
        }

        @Override
        public void dispatchMessage(Message msg) {
            // TODO Auto-generated method stub
            super.dispatchMessage(msg);
        }

    };
   
}
