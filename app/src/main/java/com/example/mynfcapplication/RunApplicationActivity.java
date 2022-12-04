package com.example.mynfcapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
/*
 * 程序的主界面逻辑，相当于MainActivity
 */
public class RunApplicationActivity extends Activity{
	
	private Button mSelectAutoRunApplication;
	private Button murlApplication;
	private Button mitinerarycardApplication;
	private Button mhealthcodeApplication;
	//当前选中的包名
	private String mPackageName;
	//写入的url内容
	private String mwriteurl;
	private NfcAdapter mNfcAdapter;
	private PendingIntent mPendingIntent;


	private int tpye=0;//写入类型：1写入打开程序，2写入打开url

	@SuppressLint("MissingInflatedId")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auto_run_application);
		
		mSelectAutoRunApplication = (Button) findViewById(R.id.button_select_auto_run_application);
		murlApplication = (Button) findViewById(R.id.button_url);
		mitinerarycardApplication = (Button) findViewById(R.id.button_itinerarycard);
		mhealthcodeApplication = (Button) findViewById(R.id.button_healthcode);
		
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		//一单截获NFC的消息，就调用PendingIntent来激活窗口
		mPendingIntent = PendingIntent.getActivity(this , 0 , new Intent(this , getClass()) , 0);
		
	}

	/*
	 * 由于launchMode设置为了singleTop或者singleTask，因此多次打开程序的时候onCreate只会执行一遍，这样
	 * 处理NFC消息的窗口就在这个onNewIntent()方法中完成
	 */
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		//如果我们没有选中某个程序，mPackageName就是空的，那么就返回，什么也不干
		if(mPackageName == null && mwriteurl==null){
			return;
		}

		//1.获得Tag对象
		Tag detectedTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		writeNFCTag(detectedTag);
	}


	/*
	 * 往NFC标签中写数据主要在该方法中体现
	 */
	private void writeNFCTag(Tag tag) {
		if(tag == null){
			return;
		}
		NdefMessage ndefMessage=null;
		if(tpye==1){
			ndefMessage= new NdefMessage( new NdefRecord[]{NdefRecord.createApplicationRecord(mPackageName)} );
		}

		if(tpye==2){
			ndefMessage = new NdefMessage( new NdefRecord[]{NdefRecord.createUri(Uri.parse(mwriteurl))} );
		}

		int size = ndefMessage.toByteArray().length;
		try {
			Ndef ndef = Ndef.get(tag);
			//先判断一下这个标签是不是NDEF的
			if(ndef != null){
				ndef.connect();
				//再来判断这个标签是否是可写的
				if( ! ndef.isWritable()){//如果是不可写的，直接就可以结束了
					Toast.makeText(this , "该NFC标签不可写!" , Toast.LENGTH_SHORT).show();
					return;
				}
				//再来判断当前标签的最大容量是否能装下我们要写入的信息
				if(ndef.getMaxSize() < size){
					Toast.makeText(this , "该NFC标签的最大可写容量太小!!" , Toast.LENGTH_SHORT).show();
					return;
				}
				//到此为止，就可以放心的把东西写入NFC标签中了
				ndef.writeNdefMessage(ndefMessage);
				Toast.makeText(this , "NFC标签写入内容成功" , Toast.LENGTH_SHORT).show();
			}
			else{//如果不是NDEF格式的
				//尝试将这个非NDEF标签格式化成NDEF格式的
				NdefFormatable format = NdefFormatable.get(tag);
				//因为有些标签是只读的，所以这里需要判断一下
				//如果format不为null，表示这个标签是可以接受格式化的
				if(format != null){
					format.connect();
					format.format(ndefMessage); //
					Toast.makeText(this , "NFC标签格式化写入成功" , Toast.LENGTH_SHORT).show();
				}
				else{
					Toast.makeText(this , "该NFC标签无法被格式化" , Toast.LENGTH_SHORT).show();
				}
			}
		} 
		catch (Exception e) {
			Toast.makeText(this , "无法读取该NFC标签" , Toast.LENGTH_SHORT).show();
		}
		
	}
	
	
	public void onClick_SelectAutoRunApplication(View view){
		Intent intent = new Intent(this , InstalledApplicationListActivity.class);
		startActivityForResult(intent , 0);
	}

	public void onClick_urlApplication(View view){
		Intent intent = new Intent(this , UrlActivity.class);
		startActivityForResult(intent , 0);
	}

	public void onClick_itineraryCardApplication(View view){
		//打开行程卡
//		Uri uri = Uri.parse("https://render.alipay.com/p/s/i/?scheme=alipays%3A%2F%2Fplatformapi%2Fstartapp%3FappId%3D2021002170600786%26page%3Dpages%252Findex%252Findex%26enbsv%3D0.2.2209271605.51%26chInfo%3Dch_share__chsub_CopyLink%26apshareid%3Dee4d5c15-67a7-4ebb-81f9-cfd3777c79c5");
//		Intent intent = new Intent();
//		intent.setAction("android.intent.action.VIEW");
//		intent.setData(uri);
//		startActivity(intent);
		murlApplication.setText("写入要打开的url链接");
		mSelectAutoRunApplication.setText("选择已安装的应用程序");
		mhealthcodeApplication.setText("写入云南健康码");
		mitinerarycardApplication.setText("写入通信行程卡【已选择】");
		mwriteurl="https://render.alipay.com/p/s/i/?scheme=alipays%3A%2F%2Fplatformapi%2Fstartapp%3FappId%3D2021002170600786%26page%3Dpages%252Findex%252Findex%26enbsv%3D0.2.2209271605.51%26chInfo%3Dch_share__chsub_CopyLink%26apshareid%3Dee4d5c15-67a7-4ebb-81f9-cfd3777c79c5";
		tpye=2;
		Toast.makeText(this , "【已选择】写入通信行程卡" , Toast.LENGTH_SHORT).show();
	}

	public void onClick_healthCodeApplication(View view){
		//打开健康码
		murlApplication.setText("写入要打开的url链接");
		mSelectAutoRunApplication.setText("选择已安装的应用程序");
		mitinerarycardApplication.setText("写入通信行程卡");
		mhealthcodeApplication.setText("写入云南健康码【已选择】");
//		Uri uri = Uri.parse("https://render.alipay.com/p/s/i/?scheme=alipays%3A%2F%2Fplatformapi%2Fstartapp%3FappId%3D2021002139686716%26page%3Dpages%252Findex%252Findex%26enbsv%3D0.2.2212011042.13%26chInfo%3Dch_share__chsub_CopyLink%26apshareid%3D4b7300b8-fd22-4061-8069-374005ac56e7");
		mwriteurl="https://render.alipay.com/p/s/i/?scheme=alipays%3A%2F%2Fplatformapi%2Fstartapp%3FappId%3D2021002139686716%26page%3Dpages%252Findex%252Findex%26enbsv%3D0.2.2212011042.13%26chInfo%3Dch_share__chsub_CopyLink%26apshareid%3D4b7300b8-fd22-4061-8069-374005ac56e7";
		tpye=2;
		Toast.makeText(this , "【已选择】写入云南健康码" , Toast.LENGTH_SHORT).show();
		//		Uri uri = Uri.parse("#小程序://行程卡/ypbMd6pmSIBQDaA");
//		Intent intent = new Intent();
//		intent.setAction("android.intent.action.VIEW");
//		intent.setData(uri);
//		startActivity(intent);
//
	}




	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		murlApplication.setText("写入要打开的url链接");
		mSelectAutoRunApplication.setText("选择已安装的应用程序");
		mitinerarycardApplication.setText("写入通信行程卡");
		mhealthcodeApplication.setText("写入云南健康码");
		if(resultCode == 1){
			mSelectAutoRunApplication.setText(data.getExtras().getString("package_name"));
			String temp = mSelectAutoRunApplication.getText().toString();
			tpye = data.getExtras().getInt("type");
			String test="1:写入打开程序";
			Toast.makeText(this , test , Toast.LENGTH_SHORT).show();
			mPackageName = temp.substring(temp.indexOf("\n") + 1);
		}
		if(resultCode == 2){
			murlApplication.setText(data.getExtras().getString("writeurl"));
			mwriteurl=murlApplication.getText().toString();
			tpye = data.getExtras().getInt("type");
			String test="2:写入url链接";
			Toast.makeText(this , test , Toast.LENGTH_SHORT).show();
		}
	}

	/*
	 * 这里所要实现的机制要高于NFC的三重过滤机制
	 * 把这个RunApplicationActivity窗口设置为最高接受NFC消息的优先级
	 */
	@Override
	protected void onResume() {
		super.onResume();
		
		if(mNfcAdapter != null){
			//把这个RunApplicationActivity窗口设置为优先级高于所有能处理NFC标签的窗口，也就是将RunApplicationActivity窗口置为栈顶
			mNfcAdapter.enableForegroundDispatch(this , mPendingIntent , null , null);
		}
	}

	/*
	 * 当不想用这个程序的时候，就不把这个窗口置顶了
	 */
	@Override
	protected void onPause() {
		super.onPause();
		
		if(mNfcAdapter != null){
			mNfcAdapter.disableForegroundDispatch(this);
		}
	}

}
