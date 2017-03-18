package com.charspan.defaultwidget.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.charspan.defaultwidget.R;
import com.charspan.defaultwidget.adapter.MyAdapter;
import com.charspan.defaultwidget.adapter.Person;

public class SpinnerActivity extends Activity {

	private Spinner mSpinner1 = null;
	private Spinner mSpinner2 = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spinner);
		mSpinner1 = (Spinner) findViewById(R.id.spinner1);
		// 建立数据源
		List<Person> persons = new ArrayList<Person>();
		persons.add(new Person("自定义", "Adapter"));
		persons.add(new Person("张三", "上海 "));
		persons.add(new Person("李四", "上海 "));
		persons.add(new Person("王五", "北京"));
		persons.add(new Person("赵六", "广州 "));
		// 建立Adapter绑定数据源
		MyAdapter _MyAdapter = new MyAdapter(this, persons);
		// 绑定Adapter
		mSpinner1.setAdapter(_MyAdapter);
		mSpinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Person user = (Person) parent.getItemAtPosition(position);
				Toast.makeText(SpinnerActivity.this,
						"你点击的是:" + user.toString(), Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				return;
			}
		});

		mSpinner2 = (Spinner) findViewById(R.id.spinner2);
		// 建立数据源
		String[] mItems = getResources().getStringArray(R.array.spinnername);
		// 建立Adapter并且绑定数据源
		ArrayAdapter<String> _Adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mItems);
		// 绑定 Adapter到控件
		mSpinner2.setAdapter(_Adapter);
		mSpinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
			@SuppressLint("ShowToast")
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				String str = parent.getItemAtPosition(position).toString();
				Toast.makeText(SpinnerActivity.this, "你点击的是:" + str, 2000)
						.show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				return;
			}
		});
	}
}
