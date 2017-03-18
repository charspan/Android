package com.charspan.defaultwidget.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.charspan.defaultwidget.R;

public class MainActivity extends Activity {

	private Context context = null;
	private Button btn_textView = null;
	private Button btn_editView = null;
	private Button btn_autoCompleteView = null;
	private Button btn_button = null;
	private Button btn_imageButton = null;
	private Button btn_radioButton = null;
	private Button btn_checkBox = null;
	private Button btn_datePicker = null;
	private Button btn_timePicker = null;
	private Button btn_imageView = null;
	private Button btn_spinner = null;
	private Button btn_meue = null;
	private Button btn_register=null;
	private Button btn_sqlite=null;

	void init() {
		context = this;
		btn_textView = (Button) findViewById(R.id.btn_textView);
		btn_editView = (Button) findViewById(R.id.btn_editView);
		btn_autoCompleteView = (Button) findViewById(R.id.btn_autoCompleteView);
		btn_button = (Button) findViewById(R.id.btn_button);
		btn_imageButton = (Button) findViewById(R.id.btn_imageButton);
		btn_radioButton = (Button) findViewById(R.id.btn_radioButton);
		btn_checkBox = (Button) findViewById(R.id.btn_checkBox);
		btn_datePicker = (Button) findViewById(R.id.btn_datePicker);
		btn_timePicker = (Button) findViewById(R.id.btn_timePicker);
		btn_imageView = (Button) findViewById(R.id.btn_imageView);
		btn_spinner = (Button) findViewById(R.id.btn_spinner);
		btn_meue = (Button) findViewById(R.id.btn_meue);
		btn_register=(Button)findViewById(R.id.btn_register);
		btn_sqlite=(Button)findViewById(R.id.btn_sqlite);
		btn_textView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, TextViewActivity.class);
				startActivity(intent);
			}
		});
		btn_editView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, EditViewActivity.class);
				startActivity(intent);
			}
		});
		btn_autoCompleteView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,
						AutoCompleteViewActivity.class);
				startActivity(intent);
			}
		});
		btn_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent itent = new Intent(context, ButtonActivity.class);
				startActivity(itent);
			}
		});
		btn_imageButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent itent = new Intent(context, ImageButtonActivity.class);
				startActivity(itent);
			}
		});
		btn_radioButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent itent = new Intent(context, RadioButtonActivity.class);
				startActivity(itent);
			}
		});
		btn_checkBox.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent itent = new Intent(context, CheckBoxActivity.class);
				startActivity(itent);
			}
		});
		btn_datePicker.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent itent = new Intent(context, DatePickerActivity.class);
				startActivity(itent);
			}
		});
		btn_timePicker.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent itent = new Intent(context, TimePickerActivity.class);
				startActivity(itent);
			}
		});
		btn_imageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent itent = new Intent(context, ImageViewActivity.class);
				startActivity(itent);
			}
		});
		btn_spinner.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent itent = new Intent(context, SpinnerActivity.class);
				startActivity(itent);
			}
		});
		btn_meue.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
                Intent intent=new Intent(context,MenuActivity.class);
                startActivity(intent);
			}
		});
		btn_register.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
                Intent intent=new Intent(context,RegisterActivity.class);
                startActivity(intent);
			}
		});
		btn_sqlite.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			     Intent intent=new Intent(context,SqliteActivity.class);
			     startActivity(intent);
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}
}
