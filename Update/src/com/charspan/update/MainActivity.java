package com.charspan.update;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.charspan.update.update.UpdateService;

public class MainActivity extends Activity {

	private Button btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn = (Button) findViewById(R.id.btn_main);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
//				checkVersion();
				Intent intent = new Intent(MainActivity.this, UpdateService.class);
				intent.putExtra("apkUrl", "http://192.168.199.129:8080/UpdateWeb/AndroidUpdate.apk");
				Log.e("spy", "update.....");
				startService(intent);
			}
		});
//		checkVersion();
	}

	/**
	 * 检查是否有新版本
	 */
	protected void checkVersion() {
		new AlertDialog.Builder(MainActivity.this).setTitle("有更新").setMessage("最新版本4.2.2")
				.setPositiveButton("更新", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(MainActivity.this, UpdateService.class);
						intent.putExtra("apkUrl", "http://192.168.199.129:8080/UpdateWeb/AndroidUpdate.apk");
						Log.d("spy", "update.....");
						startService(intent);
					}
				}).setNegativeButton("取消", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				}).show();
		// CommonDialog dialog=new CommonDialog(this,
		// getString(R.string.update_new_version),
		// getString(R.string.update_title),
		// getString(R.string.update_install),
		// getString(R.string.update_cancel)){
		//
		// public void onDialogClick(){
		// Intent intent=new Intent(MainActivity.this,UpdateService.class);
		// intent.putExtra("lastVersion", "4.2.2");
		// startService(intent);
		// }
		// };
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
