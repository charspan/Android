package com.charspan.defaultwidget.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.Toast;

import com.charspan.defaultwidget.R;

@SuppressLint("ShowToast")
public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		 SubMenu subMenu = menu.addSubMenu(1, 100, 100, "桃子");
	        subMenu.add(2, 101, 101, "大桃子");
	        subMenu.add(2, 102, 102, "小桃子");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.menu_about) {
			Toast.makeText(getApplicationContext(), "about", Toast.LENGTH_SHORT).show();;
			return true;
		}else if(id==R.id.menu_quit){
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
}
