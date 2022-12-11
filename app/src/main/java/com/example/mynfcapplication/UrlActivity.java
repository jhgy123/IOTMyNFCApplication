package com.example.mynfcapplication;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class UrlActivity extends AppCompatActivity implements ViewGroup.OnClickListener{
    private EditText meditText;
    private Button mbutton;
    private String writeurl;
    private Toolbar mNormalToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_open_uri);
        mNormalToolbar=findViewById(R.id.toolbar);
        mNormalToolbar.setTitle("写入url链接");
        setSupportActionBar(mNormalToolbar);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        //getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        //getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_setting:
                Toast.makeText (this,"项目名称：NFC应用\n版本：1.0\n开发者：龚尹鸿杰，康彦伟\n指导教师：薛刚",Toast.LENGTH_LONG).show ();
                break;
        }
        return true;
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
