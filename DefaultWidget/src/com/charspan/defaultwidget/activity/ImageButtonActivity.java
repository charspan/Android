package com.charspan.defaultwidget.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.charspan.defaultwidget.R;

public class ImageButtonActivity extends Activity {

	private ImageButton imgbtn1 = null;
	private ImageButton imgbtn2 = null;
	private ImageButton imgbtn3 = null;
	private ImageButton imgbtn4 = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_button);

		// 分别取得4个ImageButton对象
		imgbtn1 = (ImageButton) findViewById(R.id.imagebutton1);
		imgbtn2 = (ImageButton) findViewById(R.id.imagebutton2);
		imgbtn3 = (ImageButton) findViewById(R.id.imagebutton3);
		imgbtn4 = (ImageButton) findViewById(R.id.imagebutton4);

		// 分别为ImageButton设置图标
		// imgbtn1已经在main.xml布局中设置了图标，所以就不在这里设置了（设置图标即可在程序中设置，也可在布局文件中设置）
		imgbtn2.setImageDrawable(getResources().getDrawable(R.drawable.icon));// 在程序中设置图标
		imgbtn3.setImageDrawable(getResources().getDrawable(R.drawable.icon1));
		imgbtn4.setImageDrawable(getResources().getDrawable(
				android.R.drawable.sym_call_incoming));// 设置系统图标
		// 下面为各个按钮设置事件监听
		imgbtn1.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Dialog dialog = new AlertDialog.Builder(
						ImageButtonActivity.this)
						.setTitle("提示")
						.setMessage("我是ImageButton1")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// 相应的处理操作
									}
								}).create();
				dialog.show();
			}

		});

		imgbtn2.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Dialog dialog = new AlertDialog.Builder(
						ImageButtonActivity.this)
						.setTitle("提示")
						.setMessage("我是ImageButton2，我要使用ImageButton3的图标")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										imgbtn2.setImageDrawable(getResources()
												.getDrawable(R.drawable.icon1));
									}
								}).create();
				dialog.show();
			}

		});

		imgbtn3.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Dialog dialog = new AlertDialog.Builder(
						ImageButtonActivity.this)
						.setTitle("提示")
						.setMessage("我是ImageButton3，我想使用系统打电话的图标")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										imgbtn3.setImageDrawable(getResources()
												.getDrawable(
														android.R.drawable.sym_action_call));
									}
								}).create();
				dialog.show();
			}

		});

		imgbtn4.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Dialog dialog = new AlertDialog.Builder(
						ImageButtonActivity.this)
						.setTitle("提示")
						.setMessage("我是使用的系统图标")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// 相应的处理操作
									}
								}).create();
				dialog.show();
			}

		});
	}
}