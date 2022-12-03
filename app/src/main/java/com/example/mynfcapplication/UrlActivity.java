package com.example.mynfcapplication;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UrlActivity extends Activity implements ViewGroup.OnClickListener{
    private EditText meditText;
    private Button mbutton;
    private String writeurl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_open_uri);
        meditText = (EditText) findViewById(R.id.edit);
        mbutton = (Button) findViewById(R.id.write);
        mbutton.setOnClickListener(this);
//        mbutton.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            writeurl=meditText.getText().toString();
//                                            Toast.makeText(UrlActivity.this, writeurl , Toast.LENGTH_SHORT).show();
//                                            Intent intent = new Intent();
//                                            intent.putExtra("writeurl" , writeurl);
//                                            intent.putExtra("type", 2);
//                                            setResult(2 , intent);
//                                            finish();
//                                        }
//                                    });


//        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

//        mPendingIntent = PendingIntent.getActivity(this , 0 , new Intent(this , getClass()) , 0);
    }

    @Override
    public void onClick(View v) {
        writeurl=meditText.getText().toString();
        Toast.makeText(UrlActivity.this, writeurl , Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra("writeurl" , writeurl);
        intent.putExtra("type", 2);
        setResult(2 , intent);
        finish();
    }




}
