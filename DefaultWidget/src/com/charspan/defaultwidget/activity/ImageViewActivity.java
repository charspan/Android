package com.charspan.defaultwidget.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.charspan.defaultwidget.R;

@SuppressWarnings("deprecation")
public class ImageViewActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 加载布局文件
		setContentView(R.layout.activity_image_view);
		// 通过findViewById()方法得到Gallery对象
		Gallery gallery = (Gallery) findViewById(R.id.gallery);
		// 添加一个ImageAdapter并设置给Gallery
		gallery.setAdapter(new ImageAdapter(this));
	}

	public class ImageAdapter extends BaseAdapter {
		private Context context;
		// 使用系统的图标图片作为图库源
		private int[] imageids = { android.R.drawable.btn_minus,
				android.R.drawable.btn_radio,
				android.R.drawable.ic_lock_idle_low_battery,
				android.R.drawable.btn_radio, android.R.drawable.btn_dialog,R.drawable.head,R.drawable.icon,R.drawable.icon1};

		// 构造函数 此构造函数只有一个参数就是要存数的Context
		public ImageAdapter(Context c) {
			this.context = c;
		}

		// 得到已定义的图片的总数量
		public int getCount() {
			return imageids.length;
		}

		// 得到目前容器中图片的数组
		public Object getItem(int position) {
			return position;
		}

		// 得到目前容器中图片的数组ID
		public long getItemId(int position) {
			return position;
		}

		// 取得目前欲显示的图片view，传入数组ID使之读取成图像
		public View getView(int position, View convertView, ViewGroup parent) {
			// 创建一个ImageView对象
			ImageView imageview = new ImageView(context);
			// 设置图片给ImageView对象
			imageview.setImageResource(imageids[position]);
			// 重新设置图片的宽高
			imageview.setScaleType(ImageView.ScaleType.FIT_XY);
			// 重新设置Layout的宽高
			imageview.setLayoutParams(new Gallery.LayoutParams(220, 220));
			return imageview;
		}

		// 根据距离中央的位移量，利用getScale()返回view的大小
		public float getScale(boolean focused, int offset) {
			return Math.max(0, 1.0f / (float) Math.pow(2, Math.abs(offset)));
		}
	}
}