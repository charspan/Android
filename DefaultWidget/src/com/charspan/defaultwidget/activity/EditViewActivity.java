package com.charspan.defaultwidget.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.charspan.defaultwidget.R;

public class EditViewActivity extends Activity {

	private EditText mEditText = null;
	private Button btn_getValue = null;
	private Button btn_getAll = null;
	private Button btn_getSet = null;
	private Button btn_getSelect = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_view);
		mEditText = (EditText) findViewById(R.id.edit_text);
		mEditText.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				Toast.makeText(EditViewActivity.this, String.valueOf(actionId),
						Toast.LENGTH_SHORT).show();
				return false;
			}
		});

		btn_getValue = (Button) findViewById(R.id.btn_get_value);
		btn_getValue.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(EditViewActivity.this,
						mEditText.getText().toString(), Toast.LENGTH_SHORT)
						.show();
			}
		});

		btn_getAll = (Button) findViewById(R.id.btn_all);
		btn_getAll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mEditText.selectAll();
			}
		});
		btn_getSet = (Button) findViewById(R.id.btn_select);
		btn_getSet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Editable editable = mEditText.getText();
				if (editable.length() > 1)
					Selection.setSelection(editable, 1, editable.length());
			}
		});
		btn_getSelect = (Button) findViewById(R.id.btn_get_select);
		btn_getSelect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int start = mEditText.getSelectionStart();
				int end = mEditText.getSelectionEnd();
				CharSequence selectText = mEditText.getText().subSequence(start,
						end);
				Toast.makeText(EditViewActivity.this, selectText,
						Toast.LENGTH_SHORT).show();
			}
		});
	}
}
