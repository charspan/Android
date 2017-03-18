package com.charspan.defaultwidget.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.charspan.defaultwidget.R;

public class RadioButtonActivity extends Activity {

	private RadioGroup radiogroup = null;
	private RadioButton radio2 = null;
	private TextView tv = null;// 根据不同选项所要变更的文本控件
	private RadioButton rb = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_radio_button);
		// 根据ID找到该文本控件
		tv = (TextView) findViewById(R.id.tvSex);
		radiogroup = (RadioGroup) findViewById(R.id.radiogroup1);
		radio2 = (RadioButton) findViewById(R.id.radiobutton2);
		radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						if (checkedId == radio2.getId()) {
							DisplayToast("正确答案：" + radio2.getText()
									+ "，恭喜你，回答正确！");
						} else {
							DisplayToast("请注意，回答错误！");
						}
					}
				});

		// 根据ID找到RadioGroup实例
		RadioGroup group = (RadioGroup) findViewById(R.id.radioGroup);
		// 绑定一个匿名监听器
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// 获取变更后的选中项的ID
				int radioButtonId = arg0.getCheckedRadioButtonId();
				// 根据ID获取RadioButton的实例
				rb = (RadioButton) findViewById(radioButtonId);
				// 更新文本内容，以符合选中项
				tv.setText("您的性别是：" + rb.getText());
			}
		});
	}

	public void DisplayToast(String str) {
		Toast toast = Toast.makeText(this, str, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.TOP, 0, 220);
		toast.show();
	}
}
