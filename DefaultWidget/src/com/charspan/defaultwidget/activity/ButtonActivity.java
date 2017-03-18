package com.charspan.defaultwidget.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.charspan.defaultwidget.R;

public class ButtonActivity extends Activity {

	private Button btn_Color2 = null;
	private Button btn_Color3 = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_button);
		btn_Color2 = (Button) findViewById(R.id.btn_changeColor2);
		btn_Color3 = (Button) findViewById(R.id.btn_changeColor3);
		btn_Color2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(ButtonActivity.this, "点击事件,方法二", Toast.LENGTH_SHORT).show();
			}
		});
		btn_Color3.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				Toast.makeText(ButtonActivity.this, "长时间被点击", Toast.LENGTH_SHORT).show();
				return false;
			}
		});
	}

	/**
	 * 在XML布局文件中设置Button的属性：
	 *　android:onClick="changeColor"
	 *　然后在该布局文件对应的Acitivity中实现该方法： 需要注意的是这个方法必须符合三个条件：
	 *　1.public
	 * 2.返回void
	 * 3.只有一个参数View，这个View就是被点击的这个控件。
	 * */
	public void changeColor(View v) {
		Toast.makeText(this, "点击事件，方法一", Toast.LENGTH_SHORT).show();
	}
}
