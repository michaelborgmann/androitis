package com.michaelborgmann.gameworks;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;


import android.content.res.AssetManager;
import android.os.Environment;

public class FileIO {

	AssetManager assets;
	String storagePath;
	
	public FileIO(AssetManager assets) {
		this.assets = assets;
		this.storagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator; 
	}
	
	public InputStream readAsset(String filename) throws IOException {
		return assets.open(filename);
	}
	
	public InputStream readFile(String filename) throws IOException {
		return new FileInputStream(storagePath + filename);
	}
	
	public OutputStream writeFile(String filename) throws IOException {
		return new FileOutputStream(storagePath + filename);
	}
	
}