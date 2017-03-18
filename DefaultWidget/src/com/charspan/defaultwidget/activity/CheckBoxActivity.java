package com.charspan.defaultwidget.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.charspan.defaultwidget.R;

public class CheckBoxActivity extends Activity {

	private CheckBox beijing = null;
	private CheckBox shanghai = null;
	private CheckBox shenzhen = null;
	private EditText editText1 = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_box);
		beijing = (CheckBox) findViewById(R.id.beijing);
		shanghai = (CheckBox) findViewById(R.id.shanghai);
		shenzhen = (CheckBox) findViewById(R.id.shenzhen);
		editText1 = (EditText) findViewById(R.id.editText1);
		// 给CheckBox设置事件监听
		beijing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					editText1.setText(buttonView.getText() + "选中");
				} else {
					editText1.setText(buttonView.getText() + "取消选中");
				}
			}
		});
		shanghai.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					editText1.setText(buttonView.getText() + "选中");
				} else {
					editText1.setText(buttonView.getText() + "取消选中");
				}
			}
		});
		shenzhen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					editText1.setText(buttonView.getText() + "选中");
				} else {
					editText1.setText(buttonView.getText() + "取消选中");
				}
			}
		});
	}
}
