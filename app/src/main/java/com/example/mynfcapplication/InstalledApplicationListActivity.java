package com.example.mynfcapplication;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class InstalledApplicationListActivity extends ListActivity implements OnItemClickListener {

	//应用程序所有的包名信息放在这个集合里面
	private List<String> mPackages = new ArrayList<String>();
	
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        PackageManager packageManager = getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(PackageManager.GET_ACTIVITIES);
        for(PackageInfo p : packageInfos){
        	mPackages.add( p.applicationInfo.loadLabel(packageManager) + "\n" + p.packageName );
        }
        
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1 , android.R.id.text1 , mPackages);
        setListAdapter(arrayAdapter);
        getListView().setOnItemClickListener(this);
        
    }
	
	

	@Override
	public void onItemClick(AdapterView<?> parent , View view , int position , long id) {
		Intent intent = new Intent();
		intent.putExtra("package_name" , mPackages.get(position));
		intent.putExtra("type", 1);
		setResult(1 , intent);
		finish();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
