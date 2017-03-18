package com.charspan.update.update;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import android.util.Log;

/**
 * ���ص��ȹ���������������UpdateDownloadRequest
 */
public class UpdateManager {

	private static UpdateManager manager;
	private ThreadPoolExecutor threadPoolExecutor;
	private UpdateDownloadRequest request;

	private UpdateManager() {
		threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
	}

	static {
		manager = new UpdateManager();
	}

	public static UpdateManager getInstance() {
		return manager;
	}

	public void startDownloads(String downloadUrl, String localFilePath, UpdateDownloadListener downloadListener) {
		if (request != null) {
			return;
		}
		Log.e("startDownloads",localFilePath);
		checkLocalFilePath(localFilePath);
		//��ʼ����
		request = new UpdateDownloadRequest(downloadUrl, localFilePath, downloadListener);
		@SuppressWarnings("unused")
		Future<?> future = threadPoolExecutor.submit(request);
	}

	/**
	 * ����ļ�·��
	 */
	private void checkLocalFilePath(String path) {
		File dir = new File(path.substring(0, path.lastIndexOf("/") + 1));
		if (!dir.exists()) {
			dir.mkdir();
		}
		File file = new File(path);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
