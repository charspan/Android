package com.charspan.exchangepuzzle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.widget.TextView;

import com.charspan.exchangepuzzle.view.GamePintuLayout;
import com.charspan.exchangepuzzle.view.GamePintuLayout.GamePintuListener;

public class MainActivity extends Activity {

	private GamePintuLayout mGamePintuLayout;
	private TextView mLevel;
	private TextView mTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTime = (TextView) findViewById(R.id.id_time);
		mLevel = (TextView) findViewById(R.id.id_level);

		mGamePintuLayout = (GamePintuLayout) findViewById(R.id.id_gamepintu);
		mGamePintuLayout.setTimeEnabled(true);
		mGamePintuLayout.setOnGamePintuListener(new GamePintuListener() {
			@Override
			public void timeChanged(int currentTime) {
				mTime.setText("" + currentTime);
			}

			@Override
			public void nextLevel(final int nextLevel) {
				new AlertDialog.Builder(MainActivity.this)
						.setTitle("Game Info").setMessage("LEVEL UP!!!")
						.setPositiveButton("NEXT LEVEL", new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								mGamePintuLayout.nextLevel();
								// 在内部类里面所以要改成final
								mLevel.setText("" + nextLevel);
							}
						}).show();
			}

			@Override
			public void gameOver() {
				new AlertDialog.Builder(MainActivity.this)
						.setTitle("Game Info").setMessage("Game Over!!!")
						.setPositiveButton("RESTART", new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								mGamePintuLayout.restart();
							}
						}).setNegativeButton("Quit", new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								finish();
							}
						}).show();
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		mGamePintuLayout.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mGamePintuLayout.resume();
	}

}
