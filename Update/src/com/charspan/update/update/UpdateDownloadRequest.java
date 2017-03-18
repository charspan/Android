package com.charspan.update.update;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * �����ĸ������ļ������غ��̼߳��ͨѶ
 */
@SuppressLint("HandlerLeak")
public class UpdateDownloadRequest implements Runnable {

	private String downloadurl;
	private String localFilePath;
	private UpdateDownloadListener downloadListener;
	private boolean isDownloading = false;
	private long currentFileLength;

	private DownloadResponseHandler downloadHandler;

	public UpdateDownloadRequest(String downloadUrl, String localFilePath, UpdateDownloadListener downloadListener) {
		this.downloadurl = downloadUrl;
		this.localFilePath = localFilePath;
		this.downloadListener = downloadListener;
		this.isDownloading = true;
		this.downloadHandler = new DownloadResponseHandler();
	}

	/**
	 * ������ȥ�������ӵķ���
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void makeRequest() throws IOException, InterruptedException {
		if (!Thread.currentThread().isInterrupted()) {
			URL url = new URL(downloadurl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setReadTimeout(5000);
			connection.setRequestProperty("Connectine", "Keep-Alive");
			// ���������ǵ�ǰ���߳�
			connection.connect();
			currentFileLength = connection.getContentLength();
			if (!Thread.currentThread().isInterrupted()) {
				// ��������ļ�����
				downloadHandler.sendResponseMessage(connection.getInputStream());
			}
		}
	}

	// ������ӽ���
	@Override
	public void run() {
		try {
			makeRequest();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ʽ������
	 * 
	 * @param value
	 * @return
	 */
	public String getTwoPointFloatStr(float value) {
		DecimalFormat fnum = new DecimalFormat("0.00");
		return fnum.format(value);
	}

	/**
	 * �������п����쳣
	 */
	public enum FailureCode {
		UnknowHost, Socket, SocketTimeout, ConnectTimeout, IO, HttpResponse, JSON, Interrupted
	}

	public class DownloadResponseHandler {

		protected static final int success_message = 0;
		protected static final int failure_message = 1;
		protected static final int start_message = 2;
		protected static final int finish_message = 3;
		protected static final int network_off = 4;
		private static final int progress_changed = 5;

		private int mCompleteSize = 0;
		private int progress = 0;

		// ����������̼߳��ͨѶ
		private Handler handler;

		public DownloadResponseHandler() {
			handler = new Handler(Looper.getMainLooper()) {

				@Override
				public void handleMessage(Message msg) {
					handleSelfMessage(msg);
				}
			};
		}

		/**
		 * �������Ͳ�ͬ����Ϣ����
		 */
		protected void sendFinishMessage() {
			sendMessage(obtainMessage(finish_message, null));
		}

		private void sendProgressChangeMessage(int progress) {
			sendMessage(obtainMessage(progress_changed, new Object[] { progress }));
		}

		private void sendFailueMessage(FailureCode failureCode) {
			sendMessage(obtainMessage(failure_message, new Object[] { failureCode }));
		}

		private void sendMessage(Message msg) {
			if (handler != null) {
				handler.sendMessage(msg);
			} else {
				handleSelfMessage(msg);
			}
		}

		/**
		 * ��ȡһ����Ϣ����
		 * 
		 * @param responseMessage
		 * @param response
		 * @return
		 */
		protected Message obtainMessage(int responseMessage, Object response) {
			Message msg = null;
			if (handler != null) {
				msg = handler.obtainMessage(responseMessage, response);
			} else {
				msg = Message.obtain();
				msg.what = responseMessage;
				msg.obj = response;
			}
			return msg;
		}

		protected void handleSelfMessage(Message msg) {
			Object[] response;
			switch (msg.what) {
			case failure_message:
				response = (Object[]) msg.obj;
				handleFailureMessage((FailureCode) response[0]);
				break;
			case progress_changed:
				response = (Object[]) msg.obj;
				handleProgressChangedMessage(((Integer) response[0]).intValue());
				break;
			case finish_message:
				onFinish();
				break;
			default:
				break;
			}
		}

		/**
		 * ������Ϣ�Ĵ����߼�
		 */
		protected void handleProgressChangedMessage(int progress) {
			// downloadListener.onProgressChanged(progress,"");
		}

		protected void handleFailureMessage(FailureCode failureCode) {
			onFailure(failureCode);
		}

		// �ⲿ�ӿڻص�
		protected void onFinish() {
			downloadListener.onFinished(mCompleteSize, "");
		}

		public void onFailure(FailureCode failureCode) {
			downloadListener.onFailure();
		}

		/**
		 * �ļ����ط���
		 * 
		 * ���͸������͕A�¼�
		 * 
		 * @param is
		 */
		void sendResponseMessage(InputStream is) {
			RandomAccessFile randomAccessFile = null;
			mCompleteSize = 0;

			byte[] buffer = new byte[1024];
			int length = -1;
			int limit = 0;
			try {
				randomAccessFile = new RandomAccessFile(localFilePath, "rwd");
				while ((length = is.read(buffer)) != -1) {
					if (isDownloading) {
						randomAccessFile.write(buffer, 0, length);
						mCompleteSize += length;
						if (mCompleteSize < currentFileLength) {
							progress = (int) Float.parseFloat(getTwoPointFloatStr(mCompleteSize / currentFileLength));
							// Ϊ������notification����Ƶ��
							if (limit / 30 == 0 && progress <= 100) {
								sendProgressChangeMessage(progress);
							}
							limit++;
						}
					}
				}
				sendFinishMessage();
			} /*catch (FileNotFoundException e) {
				e.printStackTrace();
			} */catch (IOException e) {
				sendFailueMessage(FailureCode.IO);
			} finally {
				try {
					if (is != null) {
						is.close();
					}
					if (randomAccessFile != null) {
						randomAccessFile.close();
					}
				} catch (IOException e) {
					sendFailueMessage(FailureCode.IO);
				}
			}
		}
	}

}
