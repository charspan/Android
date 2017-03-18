package com.charspan.update.update;

import java.io.File;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.charspan.update.R;

/**
 * app更新下载后台服务
 */
public class UpdateService extends Service {

	private String apkURL;
	private String localFilePath;
	private NotificationManager notificationManager;
	private Notification notification;

	@Override
	public void onCreate() {
		Log.e("spy", "onCreate");
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		localFilePath = Environment.getExternalStorageDirectory() + "/charspan/AndroidUpdate.apk";
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent == null) {
			notifyUser(getString(R.string.update_download_failed), getString(R.string.update_download_failed_img), 0);
			stopSelf();
		}
		apkURL = intent.getStringExtra("apkUrl");
		Log.e("spy", apkURL);
		notifyUser(getString(R.string.update_download_start), getString(R.string.update_download_start), 0);
		startDownload();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private void startDownload() {
		UpdateManager.getInstance().startDownloads(apkURL, localFilePath, new UpdateDownloadListener() {

			@Override
			public void onStarted() {
				Log.e("spy", "onStarted");
			}

			@Override
			public void onProgressChanged(int progress, String downloadUrl) {
				Log.e("spy", "onProgressChanged");
				notifyUser(getString(R.string.update_download_progressing),
						getString(R.string.update_download_progressing), progress);
			}

			@Override
			public void onFinished(int completeSize, String downloadUrl) {
				Log.e("spy", "onFinished");
				notifyUser(getString(R.string.update_download_complete), getString(R.string.update_download_complete),
						100);
				stopSelf();
			}

			@Override
			public void onFailure() {
				Log.e("spy", "onFailure");
				notifyUser(getString(R.string.update_download_failed), getString(R.string.update_download_failed_img),
						0);
				stopSelf();
			}
		});

	}

	/**
	 * 更新notification告知用户下载进度
	 * 
	 * @param retult
	 * @param reason
	 * @param prpgress
	 */
	private void notifyUser(String retult, String reason, int progress) {
		Log.e("spy", "progress:" + progress);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		builder.setSmallIcon(R.drawable.ic_launcher)
				.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
				.setContentTitle(getString(R.string.app_name));
		if (progress > 0 && progress < 100) {
			builder.setProgress(100, progress, false);
		} else {
			builder.setProgress(0, 0, false);
		}
		builder.setAutoCancel(true);
		builder.setWhen(System.currentTimeMillis());
		builder.setTicker(retult);
		builder.setContentIntent(progress >= 100 ? getContentIntent()
				: PendingIntent.getActivity(this, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT));
		notification = builder.build();
		notificationManager.notify(0, notification);
	}

	private PendingIntent getContentIntent() {
		File apkFile = new File(localFilePath);
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Log.e("getContentIntent", apkFile.getAbsolutePath());
		intent.setDataAndType(Uri.parse("file://" + apkFile.getAbsolutePath()),
				"application/vnd.android.package-archive");
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		getApplication().startActivity(intent);
		return pendingIntent;
	}

}
