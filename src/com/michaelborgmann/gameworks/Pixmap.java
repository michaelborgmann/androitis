package com.michaelborgmann.gameworks;

import android.graphics.Bitmap;

import com.michaelborgmann.gameworks.Graphics.PixmapFormat;

public class Pixmap {

	PixmapFormat format;
	Bitmap bitmap;
	
	public Pixmap(Bitmap bitmap, PixmapFormat format) {
			this.bitmap = bitmap;
			this.format = format;
	}
	public int getWidth() { return bitmap.getWidth(); }
	public int getHeight() { return bitmap.getHeight(); }
	public PixmapFormat getFormat() { return format; }
	public void remove() { bitmap.recycle(); }
	
}
