package com.michaelborgmann.gameworks;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;

public class Graphics {
	
	public static enum PixmapFormat { ARGB8888, ARGB4444, RGB565 }

	AssetManager assets;
	Bitmap framebuffer;
	Canvas surface;
	Paint paint;
	Rect srcRect = new Rect();
	Rect dstRect = new Rect();


	public Graphics(AssetManager assets, Bitmap framebuffer) {
		this.assets = assets;
		this.framebuffer = framebuffer;
		this.surface = new Canvas(framebuffer);
		this.paint = new Paint();
	}

	public Pixmap loadPixmap(String filename, PixmapFormat format) {
		
		Config config = null;
		if (format == PixmapFormat.RGB565) config = Config.RGB_565;
		else if (format == PixmapFormat.ARGB4444) config = Config.ARGB_4444;
		else config = Config.ARGB_8888;

		Options options = new Options();
		options.inPreferredConfig = config;
		
		InputStream input = null;
		Bitmap bitmap = null;
		
		try {
			input = assets.open(filename);
			bitmap = BitmapFactory.decodeStream(input);
			if (bitmap == null)
				throw new RuntimeException("Couldn't load bitmap from asset '" + filename + "'");
		}
		catch (IOException e) {
			throw new RuntimeException("Couldn't load bitmap from asset '" + filename  + "'");
		}
		finally {
			if (input != null) {
				try { input.close(); }
				catch (IOException e) { }
			}
		}

		if (bitmap.getConfig() == Config.RGB_565) format = PixmapFormat.RGB565;
		else if (bitmap.getConfig() == Config.ARGB_4444) format = PixmapFormat.ARGB4444;
		else format = PixmapFormat.ARGB8888;
		
		return new Pixmap(bitmap, format);
	}

	public void clear(int color) {
		surface.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8, (color & 0xff));
	}
	
	public void drawPixel(int x, int y, int color) {
		paint.setColor(color);
		surface.drawPoint(x, y, paint);
	}

	public void drawLine(int x1, int y1, int x2, int y2, int color) {
		paint.setColor(color);
		surface.drawLine(x1, y1, x2, y2, paint);
	}
	
	public void drawRect(int x, int y, int width, int height, int color) {
		paint.setColor(color);
		paint.setStyle(Style.FILL);
		surface.drawRect(x, y, x + width - 1, y + height - 1, paint);
	}

	public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight) {
		
		srcRect.left = srcX;
		srcRect.top = srcY;
		srcRect.right = srcX + srcWidth - 1;
		srcRect.bottom = srcY + srcHeight -1;
		dstRect.left = x;
		dstRect.top = y;
		dstRect.right = x + srcWidth - 1;
		dstRect.bottom = y + srcHeight - 1;
		
		surface.drawBitmap(((Pixmap) pixmap).bitmap, srcRect, dstRect, null);
	}
	
	public void drawPixmap(Pixmap pixmap, int x, int y) {
		surface.drawBitmap(((Pixmap) pixmap).bitmap, x, y, null);
	}

	public int getWidth() { return framebuffer.getWidth(); }
	public int getHeight() { return framebuffer.getHeight(); }

}
