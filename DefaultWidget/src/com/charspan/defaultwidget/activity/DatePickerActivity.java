package com.charspan.defaultwidget.activity;

import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;

import com.charspan.defaultwidget.R;

public class DatePickerActivity extends Activity {

	private EditText dateEt = null;
	private DatePicker datePicker = null;
	private Calendar calendar = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_date_picker);
		dateEt = (EditText) findViewById(R.id.dateEt);
		datePicker = (DatePicker) findViewById(R.id.datePicker);
		calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int monthOfYear = calendar.get(Calendar.MONTH);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		datePicker.init(year, monthOfYear, dayOfMonth,
				new OnDateChangedListener() {
					public void onDateChanged(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						dateEt.setText("您选择的日期是：" + year + "年"
								+ (monthOfYear + 1) + "月" + dayOfMonth + "日。");
					}

				});
	}
}
