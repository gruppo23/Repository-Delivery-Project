package com.uni.frame;

import java.io.InputStream;

public class SingletonImage {

	// Immagini caricate
	private static String IMAGE_0;
	private static String IMAGE_1;
	
	private static SingletonImage singletonImage;
	private SingletonImage() {
		IMAGE_0 = "slide-1.png";
	}
	
	/**
	 * 
	 * @return
	 */
	public static SingletonImage getInstance() {
		if(singletonImage == null) {
			// Singleton immagini!
			singletonImage = new SingletonImage();
		}
		return singletonImage;
	}
	
	/**
	 * 
	 * @param num
	 * @return
	 */
	public String getImage(int num) {
		
		// Ritorna immagine in base al numero!
		switch(num) {
			
			case 0:
				return IMAGE_0;
				
			case 1:
				return IMAGE_1;
		
			default:
				return null;
		}
		
	}
	
}
