package com.charspan.movepuzzle;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

	/**
	 * 游戏是否开始
	 */
	private boolean isGameStart = false;
	/**
	 * 当前是否有移动动画
	 */
	private boolean isAnimRun = false;
	/**
	 * 利用二维数组创建若干个游戏小方块
	 */
	private ImageView[][] iv_game_arr = new ImageView[3][5];
	/**
	 * 游戏主界面
	 */
	private GridLayout gl_main_game;
	/**
	 * 当前空方块的实例
	 */
	private ImageView iv_null_ImageView;
	/**
	 * 当前手势
	 */
	private GestureDetector mDetector;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		mDetector.onTouchEvent(ev);
		return super.dispatchTouchEvent(ev);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mDetector = new GestureDetector(this, new OnGestureListener() {
			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				return false;
			}

			@Override
			public void onShowPress(MotionEvent e) {
			}

			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
				return false;
			}

			@Override
			public void onLongPress(MotionEvent e) {
			}

			/**
			 * 快速划过
			 */
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				int type = getDirByGes(e1.getX(), e1.getY(), e2.getX(), e2.getY());
				// Toast.makeText(MainActivity.this, type + "",
				// Toast.LENGTH_SHORT).show();
				changeByDir(type);
				return false;
			}

			@Override
			public boolean onDown(MotionEvent e) {
				return false;
			}
		});
		/**
		 * 初始化游戏的若干个小方块
		 */
		// 获取一张大图 // ((BitmapDrawable)
		// getResources().getDrawable(R.drawable.pic_1)).getBitmap()
		Bitmap bigBm = BitmapFactory.decodeResource(getResources(), R.drawable.pic_1);
		// 小方块边长
		int tuWandH = bigBm.getWidth() / 5;
		// 小方块的边长，应该是整个屏幕的宽度/5
		@SuppressWarnings("deprecation")
		int ivWandH = getWindowManager().getDefaultDisplay().getWidth() / 5;
		/**
		 * 初始化游戏主界面，并添加若干个小方块
		 */
		gl_main_game = (GridLayout) findViewById(R.id.gl_main_game);
		for (int i = 0; i < iv_game_arr.length; i++) {
			for (int j = 0; j < iv_game_arr[0].length; j++) {
				// 根据行和列来切成若干个游戏小图片(根据列来切的)
				Bitmap bm = Bitmap.createBitmap(bigBm, j * tuWandH, i * tuWandH, tuWandH, tuWandH);
				iv_game_arr[i][j] = new ImageView(this);
				// 设置每一个游戏小方块的图案
				iv_game_arr[i][j].setImageBitmap(bm);
				// 他的父布局是RL
				iv_game_arr[i][j].setLayoutParams(new RelativeLayout.LayoutParams(ivWandH, ivWandH));
				// 设置小方块之间的间距
				iv_game_arr[i][j].setPadding(2, 2, 2, 2);
				// 绑定自定义游戏数据
				iv_game_arr[i][j].setTag(new GameData(i, j, bm));
				iv_game_arr[i][j].setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						boolean flag = isNearByNullImageView((ImageView) v);
						// Toast.makeText(MainActivity.this, "当前点击方块与空方块是否相邻" +
						// flag, Toast.LENGTH_SHORT).show();
						if (flag) {
							changeDataByImageView((ImageView) v);
						}
					}
				});
				gl_main_game.addView(iv_game_arr[i][j]);
			}
		}
		/**
		 * 设置当前最后一个方块为空的数据
		 */
		setNullImageView(iv_game_arr[2][4]);
		/**
		 * 随机打乱顺序
		 */
		randomMove();
		/**
		 * 设置游戏开始状态
		 */
		isGameStart = true;
	}

	/**
	 * 根据手势的方向，获取与空方块相邻的位置，如果存在方块就进行数据交换
	 * 
	 * @param type
	 *            1:上，2:下，3:左，4:右
	 */
	public void changeByDir(int type) {
		changeByDir(type, true);
	}

	/**
	 * 根据手势的方向，获取与空方块相邻的位置，如果存在方块就进行数据交换
	 * 
	 * @param type
	 *            1:上，2:下，3:左，4:右
	 * @param isAnim
	 *            是否带动画
	 */
	public void changeByDir(int type, boolean isAnim) {
		// 获取空方块位置
		GameData mNullGameData = (GameData) iv_null_ImageView.getTag();
		// 根据手势方向，设置相邻位置二维数组的坐标
		int new_x = mNullGameData.x;
		int new_y = mNullGameData.y;
		if (type == 1) {//
			new_x++;
		} else if (type == 2) {
			new_x--;
		} else if (type == 3) {
			new_y++;
		} else if (type == 4) {
			new_y--;
		}
		// Toast.makeText(MainActivity.this, data.x+" ::
		// "+data.y,Toast.LENGTH_SHORT).show();

		// 判断新坐标是否存在
		if (new_x >= 0 && new_x < iv_game_arr.length && new_y >= 0 && new_y < iv_game_arr[0].length) {
			// Toast.makeText(MainActivity.this, new_x+" :
			// "+new_y,Toast.LENGTH_SHORT).show();
			// 存在的话开始移动
			changeDataByImageView(iv_game_arr[new_x][new_y], isAnim);
		}
	}

	/**
	 * 判断游戏是否结束
	 * 
	 * 游戏结束则Toast
	 */
	public void isGameOver() {
		boolean isGameOver = true;
		// 遍历每个游戏小方块
		for (int i = 0; i < iv_game_arr.length; i++) {
			if (!isGameOver)
				break;
			for (int j = 0; j < iv_game_arr[0].length; j++) {
				// 为空的方块数据不判断
				if (iv_game_arr[i][j] == iv_null_ImageView) {
					continue;
				}
				GameData mGameData = (GameData) iv_game_arr[i][j].getTag();
				if (!mGameData.isTrue()) {
					isGameOver = false;
					break;
				}
			}
		}
		// 游戏结束给提示
		if (isGameOver) {
			Toast.makeText(MainActivity.this, "游戏结束", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 手势的判断
	 * 
	 * @param sx
	 *            手第一个触摸点的x坐标
	 * @param sy
	 *            手第一个触摸点的y坐标
	 * @param ex
	 *            手最后一个触摸点的x坐标
	 * @param ey
	 *            手最后一个触摸点的y坐标
	 * @return int <br>
	 *         1:手势向上滑动<br>
	 *         2:手势向下滑动<br>
	 *         3:手势向左滑动 <br>
	 *         4:手势向上右滑动
	 */
	public int getDirByGes(float sx, float sy, float ex, float ey) {
		// 左右：横向距离大于竖直距离
		// 左 ：终点x小于起点x
		// 安卓y正轴方向为竖直向下
		// 上：终点y小于起点y
		return Math.abs(sx - ex) < Math.abs(sy - ey) ? (sy > ey ? 1 : 2) : (sx > ex ? 3 : 4);
	}

	/**
	 * 随机打乱顺序
	 */
	public void randomMove() {
		// 打乱次数
		for (int i = 0; i < 50; i++) {
			// 开始交换，无动画
			// 0~3 + 1
			int type = (int) (Math.random() * 4) + 1;
			changeByDir(type, false);
		}
	}

	/**
	 * 利用动画结束之后，交换两个方块
	 * 
	 * @param mImageView
	 *            点击的方块
	 */
	public void changeDataByImageView(final ImageView mImageView) {
		changeDataByImageView(mImageView, true);
	}

	/**
	 * 利用动画结束之后，交换两个方块
	 * 
	 * @param mImageView
	 *            点击的方块
	 * @param isAnim
	 *            是否有动画
	 */
	public void changeDataByImageView(final ImageView mImageView, boolean isAnim) {
		if (isAnimRun) {
			return;
		}
		// 有没有动画
		if (!isAnim) {
			GameData mGameData = (GameData) mImageView.getTag();
			GameData mNullGameData = (GameData) iv_null_ImageView.getTag();
			// 设置图片
			iv_null_ImageView.setImageBitmap(mGameData.bm);
			// 交换数据
			mNullGameData.bm = mGameData.bm;
			mNullGameData.p_x = mGameData.p_x;
			mNullGameData.p_y = mGameData.p_y;
			// 设置当前点击的为空方块
			setNullImageView(mImageView);
			// 游戏是否开始
			if (isGameStart) {
				isGameOver();
			}
			return;
		}
		// 创建动画，设置方向、移动距离
		TranslateAnimation translateAnimation = null;
		// 点击方块在白色方块上边
		if (mImageView.getY() > iv_null_ImageView.getY()) {//
			translateAnimation = new TranslateAnimation(0.1f, 0.1f, 0.1f, -mImageView.getWidth());
			// Toast.makeText(MainActivity.this, "白色下移",
			// Toast.LENGTH_SHORT).show();
		} // 点击方块在白色方块下边
		else if (mImageView.getY() < iv_null_ImageView.getY()) {//
			translateAnimation = new TranslateAnimation(0.1f, 0.1f, 0.1f, mImageView.getWidth());
			// Toast.makeText(MainActivity.this, "白色上移",
			// Toast.LENGTH_SHORT).show();
		}
		// 点击方块在白色方块左边
		else if (mImageView.getX() > iv_null_ImageView.getX()) {
			translateAnimation = new TranslateAnimation(0.1f, -mImageView.getWidth(), 0.1f, 0.1f);
			// Toast.makeText(MainActivity.this, "白色右移",
			// Toast.LENGTH_SHORT).show();
		} // 点击方块在白色方块右边
		else if (mImageView.getX() < iv_null_ImageView.getX()) {//
			translateAnimation = new TranslateAnimation(0.1f, mImageView.getWidth(), 0.1f, 0.1f);
			// Toast.makeText(MainActivity.this, "白色左移",
			// Toast.LENGTH_SHORT).show();
		}
		// 设置动画时长
		translateAnimation.setDuration(70);
		// 动画结束之后停留
		translateAnimation.setFillAfter(true);
		// 动画结束后真正的把数据交换
		translateAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				isAnimRun = true;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				isAnimRun = false;
				mImageView.clearAnimation();
				GameData mGameData = (GameData) mImageView.getTag();
				GameData mNullGameData = (GameData) iv_null_ImageView.getTag();
				// 设置图片
				iv_null_ImageView.setImageBitmap(mGameData.bm);
				// 交换数据
				mNullGameData.bm = mGameData.bm;
				mNullGameData.p_x = mGameData.p_x;
				mNullGameData.p_y = mGameData.p_y;
				// 设置当前点击的为空方块
				setNullImageView(mImageView);
				// 游戏是否开始
				if (isGameStart) {
					isGameOver();
				}
			}
		});
		// 执行动画
		mImageView.startAnimation(translateAnimation);
	}

	/**
	 * 设置某个方块位空方块
	 * 
	 * @param imageView
	 *            当前要设置为空的方块的实例
	 */
	public void setNullImageView(ImageView mImageView) {
		mImageView.setImageBitmap(null);
		iv_null_ImageView = mImageView;
	}

	/**
	 * 判断当前点击的方块，是否与空方块的位置关系是相邻关系
	 * 
	 * @param imageView
	 *            所点击的方块
	 * @return true:相邻,false:不相邻
	 */
	public boolean isNearByNullImageView(ImageView mImageView) {
		// 分别获取当前空方块的位置与点击方块的位置，通过坐标x、y两边只差1的方式判断
		// x,y是二维数组的位置
		GameData mNullGameData = (GameData) iv_null_ImageView.getTag();
		GameData mGameData = (GameData) mImageView.getTag();
		// // 当前点击的方块在空方块的上边
		// if (mNullGameData.x == mGameData.x + 1 && mNullGameData.y ==
		// mGameData.y) {
		// // Toast.makeText(MainActivity.this, "相邻，白色方块在点击方块下边",
		// // Toast.LENGTH_SHORT).show();
		// return true;
		// } // 当前点击的方块在空方块的下边
		// else if (mNullGameData.x == mGameData.x - 1 && mNullGameData.y ==
		// mGameData.y) {
		// // Toast.makeText(MainActivity.this, "相邻，白色方块在点击方块上面边",
		// // Toast.LENGTH_SHORT).show();
		// return true;
		// }
		// // 当前点击的方块在空方块的左边
		// else if (mNullGameData.x == mGameData.x && mNullGameData.y ==
		// mGameData.y + 1) {
		// // Toast.makeText(MainActivity.this, "相邻，白色方块在点击方块右边",
		// // Toast.LENGTH_SHORT).show();
		// return true;
		// }
		// // 当前点击的方块在空方块的右边
		// else if (mNullGameData.x == mGameData.x && mNullGameData.y ==
		// mGameData.y - 1) {
		// // Toast.makeText(MainActivity.this, "相邻，白色方块在点击方块左边",
		// // Toast.LENGTH_SHORT).show();
		// return true;
		// }
		// // Toast.makeText(MainActivity.this, "白色方块与点击方块不相邻",
		// // Toast.LENGTH_SHORT).show();
		if (Math.abs(mNullGameData.x - mGameData.x) + Math.abs(mNullGameData.y - mGameData.y) == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 游戏小方块上要绑定的数据
	 */
	class GameData {

		/** 小方块的原始位置 **/
		private int x = 0;
		private int y = 0;
		/** 小方块的图片 **/
		private Bitmap bm;
		/** 小方块的当前位置 **/
		private int p_x;
		private int p_y;

		public GameData(int x, int y, Bitmap bm) {
			this.x = x;
			this.y = y;
			this.bm = bm;
			this.p_x = x;
			this.p_y = y;
		}

		/**
		 * 每个小方块的位置是否正确
		 * 
		 * @return
		 */
		public boolean isTrue() {
			if (x == p_x && y == p_y)
				return true;
			return false;
		}

	}

}