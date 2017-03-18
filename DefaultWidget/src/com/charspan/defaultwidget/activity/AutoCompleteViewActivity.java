package com.charspan.defaultwidget.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.charspan.defaultwidget.R;

public class AutoCompleteViewActivity extends Activity {

	private AutoCompleteTextView mACTV1=null;

	private AutoCompleteTextView mACTV2=null;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auto_complete_view);
		mACTV1=(AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		String[] str=new String[]{"Chine","Japan","Korean","Russian","USA","Hong Kong"};
		ArrayAdapter type1=new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, str); 
		mACTV1.setAdapter(type1);
		
		mACTV2=(AutoCompleteTextView) findViewById(R.id.autoCompleteTextView2);
		ArrayAdapter type2=ArrayAdapter.createFromResource(this, R.array.autoCompleteView_str, android.R.layout.simple_spinner_item); 
		type2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
        mACTV2.setAdapter(type2);
		
	}
}
