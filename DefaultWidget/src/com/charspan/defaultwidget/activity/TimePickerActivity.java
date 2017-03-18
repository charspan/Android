package com.charspan.defaultwidget.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import com.charspan.defaultwidget.R;

public class TimePickerActivity extends Activity {

	private EditText timeEt = null;
	private TimePicker timePicker = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_picker);
		timeEt = (EditText) findViewById(R.id.timeEt);
		timePicker = (TimePicker) findViewById(R.id.timePicker);
		timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				timeEt.setText("您选择的时间是：" + hourOfDay + "时" + minute + "分。");
			}
		});
	}
}
