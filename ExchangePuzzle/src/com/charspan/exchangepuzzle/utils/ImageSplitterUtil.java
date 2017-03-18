package com.charspan.exchangepuzzle.utils;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;

import com.charspan.exchangepuzzle.model.ImagePiece;

/**
 * 图片切割类
 * */
public class ImageSplitterUtil {

	/**
	 * 传入bitmap,piece 切成piece*piece块，返回List<ImagePiece>
	 * 
	 * @param bitmap
	 * @param piece
	 * @return List<ImagePiece>
	 */
	public static List<ImagePiece> splitImage(Bitmap bitmap, int piece) {

		List<ImagePiece> imagePieces = new ArrayList<ImagePiece>();
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int pieceWidth = Math.min(width, height) / piece;
		for (int i = 0; i < piece; i++) {
			for (int j = 0; j < piece; j++) {
				ImagePiece imagePiece = new ImagePiece();
				imagePiece.setIndex(i * piece + j);
				int x = j * pieceWidth;
				int y = i * pieceWidth;
				imagePiece.setBitmap(Bitmap.createBitmap(bitmap, x, y,
						pieceWidth, pieceWidth));
				imagePieces.add(imagePiece);
			}
		}
		return imagePieces;
	}
}
