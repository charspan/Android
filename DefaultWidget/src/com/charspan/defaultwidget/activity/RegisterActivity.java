package com.charspan.defaultwidget.activity;


import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.charspan.defaultwidget.R;
import com.charspan.defaultwidget.model.User;

public class RegisterActivity extends Activity {

	private EditText etUser = null;
	private EditText etPwd = null;
	private EditText etRePwd = null;
	private RadioButton rbMale = null;
	private RadioButton rbFamale = null;
	private Spinner spCity = null;
	private CheckBox cbRead = null;
	private CheckBox cbTourist = null;
	private CheckBox cbPlayGame = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		etUser = (EditText) findViewById(R.id.etUser);
		etPwd = (EditText) findViewById(R.id.etPwd);
		etRePwd = (EditText) findViewById(R.id.etRePwd);
		rbMale = (RadioButton) findViewById(R.id.rbMale);
		rbFamale = (RadioButton) findViewById(R.id.rbfaMale);
		cbRead = (CheckBox) findViewById(R.id.cbRead);
		cbTourist = (CheckBox) findViewById(R.id.cbTourist);
		cbPlayGame = (CheckBox) findViewById(R.id.cbPlayGame);
		spCity = (Spinner) findViewById(R.id.spCity);
	}

	public void onclick(View view)  {
		switch (view.getId()) {
		case R.id.btRegister:
			String userName = etUser.getText().toString();
			if (TextUtils.isEmpty(userName)) {
				Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
				return ;
			}
			String passW = etPwd.getText().toString();
			if (TextUtils.isEmpty(passW)) {
				etPwd.setError("密码不能为空");
				return ;
			}
			String rePassw = etRePwd.getText().toString();
			if (TextUtils.isEmpty(rePassw)) {
				etRePwd.setError("确认密码不能为空");
				return ;
			}
			if (!passW.equals(rePassw)) {
				Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
				return ;
			}
			char sex;
			if (rbMale.isChecked()) {
				sex = rbMale.getText().charAt(0);
			} else {
				sex = rbFamale.getText().charAt(0);
			}
			StringBuffer s = new StringBuffer();
			if (cbRead.isChecked()) {
				s.append(cbRead.getText().toString() + ",");
			}
			if (cbTourist.isChecked()) {
				s.append(cbTourist.getText().toString() + ",");
			}
			if (cbPlayGame.isChecked()) {
				s.append(cbPlayGame.getText().toString() + ",");
			}
			String city = spCity.getSelectedItem().toString();
			User user = new User(userName, passW, sex,
					s.toString().equals("") ? "" : s.toString().substring(0,
							s.length()), city);
			Toast.makeText(this, "用户注册信息：" + user.toString(), Toast.LENGTH_SHORT).show();
			if(user!=null){
//				user.writeXML(user); 
				finish();}
		case R.id.btEixt:
			finish();
			break;
		}
	}
}
