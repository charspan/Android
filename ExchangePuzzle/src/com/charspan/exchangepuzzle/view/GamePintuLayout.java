package com.charspan.exchangepuzzle.view;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.charspan.exchangepuzzle.R;
import com.charspan.exchangepuzzle.model.ImagePiece;
import com.charspan.exchangepuzzle.utils.ImageSplitterUtil;

@SuppressLint("HandlerLeak")
public class GamePintuLayout extends RelativeLayout implements OnClickListener {
	/**
	 * 将图片切割成mClomn*mClomn块
	 */
	private int mColumn = 2;
	/**
	 * 容器的内边距
	 */
	private int mPadding;
	/**
	 * 每张小图之间的距离（横、枞）dp
	 */
	private int mMargin = 1;
	/**
	 * 图片碎块数组集合
	 */
	private ImageView[] mGamePintuItems;
	/**
	 * 图片碎块长和宽
	 */
	private int mItemWidth;// 正方形
	/**
	 * 游戏图片
	 */
	private Bitmap mBitmap;
	/**
	 * 图片碎块列表集合
	 */
	private List<ImagePiece> mItemBitmaps;
	private boolean once;
	/**
	 * 游戏面板宽度
	 */
	private int mWidth;
	/**
	 * 游戏当前关卡是否成功
	 */
	private boolean isGameSuccess = false;
	/**
	 * 游戏是否结束
	 */
	private boolean isGameOver = false;
	/**
	 * 等级
	 */
	private int mLevel = 1;
	/**
	 * 时间修改信息
	 */
	private static final int TIME_CHANGE = 0x110;
	/**
	 * 升级信息
	 */
	private static final int NEXT_LEVEL = 0x111;
	/**
	 * 是否计时
	 */
	private boolean isTimeEnabled = false;
	/**
	 * 剩余时间
	 */
	private int mTime;
	/**
	 * 交换的动画层
	 */
	private RelativeLayout mAnimLayout;
	/**
	 * 当前是否在移动 防止 用户可能疯狂的在点击
	 */
	private boolean isAniming;

	/**
	 * 设置接口
	 */
	public interface GamePintuListener {

		void nextLevel(int nextLevel);

		void timeChanged(int currentTime);

		void gameOver();
	}

	public GamePintuListener mListener;
	/**
	 * 点击的第一块图片
	 */
	private ImageView mFirst;
	/**
	 * 点击的第图块图片
	 */
	private ImageView mSecond;

	/**
	 * 设置接口回调
	 * 
	 * @param mListener
	 */
	public void setOnGamePintuListener(GamePintuListener mListener) {
		this.mListener = mListener;
	}

	public GamePintuLayout(Context context) {
		this(context, null);
	}

	public GamePintuLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public GamePintuLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initDatas();
	}

	/**
	 * 初始化参数
	 */
	private void initDatas() {
		mMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				mMargin, getResources().getDisplayMetrics());
		mPadding = min(getPaddingLeft(), getPaddingRight(), getPaddingTop(),
				getPaddingBottom());
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case TIME_CHANGE:
				if (isGameSuccess || isGameOver || isPause)
					return;
				if (mListener != null) {
					mListener.timeChanged(mTime);
				}
				if (mTime == 0) {
					isGameOver = true;
					mListener.gameOver();
					return;
				}
				mTime--;
				// 延迟一千毫秒发送
				mHandler.sendEmptyMessageDelayed(TIME_CHANGE, 1000);
				break;
			case NEXT_LEVEL:
				mLevel++;
				if (mListener != null) {
					mListener.nextLevel(mLevel);
				} else {
					nextLevel();
				}
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// 取长宽的最小值
		mWidth = Math.min(getMeasuredHeight(), getMeasuredWidth());
		if (!once) {
			// 进行切图，以及排序
			initBitmap();
			// 设置ImageView(Item)的宽高等属性
			initItem();
			// 判断是否开启时间
			checkTimeEnable();
			once = true;
		}
		setMeasuredDimension(mWidth, mWidth);
	}

	/**
	 * 进行切图，以及排序
	 * 
	 * 完成后mItemBitmaps里面存放了已经分割好且乱序的图块
	 */
	private void initBitmap() {
		if (mBitmap == null) {
			mBitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.image);
		}
		mItemBitmaps = ImageSplitterUtil.splitImage(mBitmap, mColumn);
		//先乱序一次
		Collections.sort(mItemBitmaps, new Comparator<ImagePiece>() {
			@Override
			public int compare(ImagePiece a, ImagePiece b) {
				return Math.random() > 0.5 ? 1 : -1;
			}
		});
		while (isSame()) {
			// 使用sort完成乱序
			Collections.sort(mItemBitmaps, new Comparator<ImagePiece>() {
				@Override
				public int compare(ImagePiece a, ImagePiece b) {
					return Math.random() > 0.5 ? 1 : -1;
				}
			});
		}
	}

	/**
	 * 是否初始化的时候已经完成拼图
	 * 
	 * @return
	 */
	private boolean isSame() {
		boolean tmp = true;
		for (int i = 0; i < mItemBitmaps.size(); i++)
			if (mItemBitmaps.get(i).getIndex() != i)
				tmp = false;
//		Log.e("error", String.valueOf(tmp));
		return tmp;
	}

	/**
	 * 设置ImageView(Item)的宽高等属性
	 */
	private void initItem() {
		// 每一块小图片的长度=总宽度-容器的内边距*2-每张小图之间的距离（横、枞）dp*(多少列-1)/多少列
		mItemWidth = (mWidth - mPadding * 2 - mMargin * (mColumn - 1))
				/ mColumn;
		mGamePintuItems = new ImageView[mColumn * mColumn];
		// 生成我们的Item，设置Rule
		for (int i = 0; i < mGamePintuItems.length; i++) {
			ImageView item = new ImageView(getContext());
			item.setImageBitmap(mItemBitmaps.get(i).getBitmap());
			item.setId(i + 1);
			// 在Item的tag中存储了index真实的下标
			item.setTag(i + "_" + mItemBitmaps.get(i).getIndex());
			// 设置点击监听
			item.setOnClickListener(this);
			mGamePintuItems[i] = item;
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
					mItemWidth, mItemWidth);
			// 设置Item间横向间隙,通过rightMargin
			/*
			 * 0 1 2 3 4 5 6 7 8 9 10 11
			 */
			// 不是最后一列
			if ((i + 1) % mColumn != 0) {
				lp.rightMargin = mMargin;
			}
			// 不是第一列
			if (i % mColumn != 0) {
				lp.addRule(RelativeLayout.RIGHT_OF,
						mGamePintuItems[i - 1].getId());
			}
			// 如果不是第一行,设置topMargin和rule
			if ((i + 1) > mColumn) {
				lp.topMargin = mMargin;
				lp.addRule(RelativeLayout.BELOW,
						mGamePintuItems[i - mColumn].getId());
			}
			addView(item, lp);
		}
	}

	/**
	 * 修改时间
	 */
	private void checkTimeEnable() {
		if (isTimeEnabled) {
			// 根据当前等级设置时间
			countTimeBaseLevel();
			mHandler.sendEmptyMessage(TIME_CHANGE);
		}
	}

	private void countTimeBaseLevel() {
		mTime = (int) Math.pow(2, mLevel) * 10;
	}

	public void restart() {
		isGameOver = false;
		mColumn--;
		nextLevel();
	}

	/**
	 * 是否暂停
	 */
	boolean isPause;

	public void pause() {
		isPause = true;
		mHandler.removeMessages(TIME_CHANGE);
	}

	public void resume() {
		if (isPause) {
			isPause = false;
			mHandler.sendEmptyMessage(TIME_CHANGE);
		}
	}

	public void nextLevel() {
		this.removeAllViews();
		mAnimLayout = null;
		mColumn++;
		isGameSuccess = false;
		checkTimeEnable();
		initBitmap();
		initItem();
	}

	/**
	 * 获取多个参数的最小值 int...params 意思是不管你传递多少个
	 */
	private int min(int... params) {
		int min = params[0];
		for (int param : params) {
			if (param < min)
				min = param;
		}
		return min;
	}

	@Override
	public void onClick(View v) {
		if (isAniming)
			return;
		// 两次点击同一个Item
		if (mFirst == v) {
			mFirst.setColorFilter(null);
			mFirst = null;
			return;
		}
		if (mFirst == null) {
			mFirst = (ImageView) v;
			mFirst.setColorFilter(Color.parseColor("#55FF0000"));
		} else {
			mSecond = (ImageView) v;
			// 交换我们的Item
			exchange();
		}
	}

	/**
	 * 交换
	 */
	private void exchange() {
		mFirst.setColorFilter(null);
		// 构造我们的动画层
		setUpAnimLayout();
		ImageView first = new ImageView(getContext());
		final Bitmap fitstBitmap = mItemBitmaps.get(
				getImageIdByTag((String) mFirst.getTag())).getBitmap();
		first.setImageBitmap(fitstBitmap);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				mItemWidth, mItemWidth);
		lp.leftMargin = mFirst.getLeft() - mPadding;
		lp.topMargin = mFirst.getTop() - mPadding;
		first.setLayoutParams(lp);
		mAnimLayout.addView(first);
		ImageView second = new ImageView(getContext());
		final Bitmap secondBitmap = mItemBitmaps.get(
				getImageIdByTag((String) mSecond.getTag())).getBitmap();
		second.setImageBitmap(secondBitmap);
		RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(
				mItemWidth, mItemWidth);
		lp2.leftMargin = mSecond.getLeft() - mPadding;
		lp2.topMargin = mSecond.getTop() - mPadding;
		second.setLayoutParams(lp2);
		mAnimLayout.addView(second);
		// 设置动画
		TranslateAnimation anim = new TranslateAnimation(0, mSecond.getLeft()
				- mFirst.getLeft(), 0, mSecond.getTop() - mFirst.getTop());
		// 设置时间
		anim.setDuration(300);
		anim.setFillAfter(true);
		first.startAnimation(anim);

		TranslateAnimation animSecond = new TranslateAnimation(0,
				-mSecond.getLeft() + mFirst.getLeft(), 0, -mSecond.getTop()
						+ mFirst.getTop());
		// 设置时间
		animSecond.setDuration(300);
		animSecond.setFillAfter(true);
		second.startAnimation(animSecond);

		// 监听动画
		anim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// 先把原本的图片进行隐藏
				mFirst.setVisibility(View.INVISIBLE);
				mSecond.setVisibility(View.INVISIBLE);
				isAniming = true;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				String firstTag = (String) mFirst.getTag();
				String secondTag = (String) mSecond.getTag();
				mFirst.setImageBitmap(secondBitmap);
				mSecond.setImageBitmap(fitstBitmap);
				mFirst.setTag(secondTag);
				mSecond.setTag(firstTag);
				mFirst.setVisibility(View.VISIBLE);
				mSecond.setVisibility(View.VISIBLE);
				mFirst = mSecond = null;
				mAnimLayout.removeAllViews();
				// 判断是否拼图成功
				checkSuccess();
				isAniming = false;
			}
		});
	}

	/**
	 * 构造我们的动画层
	 */
	private void setUpAnimLayout() {
		if (mAnimLayout == null) {
			mAnimLayout = new RelativeLayout(getContext());
			addView(mAnimLayout);
		}
	}

	/**
	 * 判断是否拼图成功
	 */
	protected void checkSuccess() {
		boolean isSuccess = true;
		for (int i = 0; i < mGamePintuItems.length; i++) {
			ImageView imageView = mGamePintuItems[i];
			if (getImageIndexByTag((String) imageView.getTag()) != i) {
				isSuccess = false;
				break;
			}
		}
		if (isSuccess) {
			isGameSuccess = true;
			mHandler.removeMessages(TIME_CHANGE);
			Log.e("TAG", "SUCCESS");
			Toast.makeText(getContext(), "Succes,level up", Toast.LENGTH_LONG)
					.show();
			mHandler.sendEmptyMessage(NEXT_LEVEL);
		}
	}

	/**
	 * 根据tag获取Id
	 */
	public int getImageIdByTag(String tag) {
		String[] split = tag.split("_");
		return Integer.parseInt(split[0]);
	}

	/**
	 * 根据tag获取Index
	 */
	public int getImageIndexByTag(String tag) {
		String[] split = tag.split("_");
		return Integer.parseInt(split[1]);
	}

	/**
	 * 设置是否计时
	 */
	public void setTimeEnabled(boolean isTimeEnabled) {
		this.isTimeEnabled = isTimeEnabled;
	}

}