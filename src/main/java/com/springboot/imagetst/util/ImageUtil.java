package com.springboot.imagetst.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import javax.imageio.ImageIO;
import org.springframework.web.multipart.MultipartFile;

public class ImageUtil {

	public static String convertImageToBase64(MultipartFile image) {
	    try {
	        BufferedImage originalImage = ImageIO.read(image.getInputStream());
	        if (originalImage == null) {
	        	return null;
	        }
	        BufferedImage compressedImage = compressImage(originalImage);
	        if (compressedImage == null) {
	            return null;
	        }
	        return encodeImageToBase64(compressedImage);
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}

    private static BufferedImage compressImage(BufferedImage image) {
        if (image == null) {
            return null; // Return null if the input image is null
        }
        int newWidth = image.getWidth() / 2; // Half the width
        int newHeight = image.getHeight() / 2; // Half the height
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.setColor(Color.WHITE); // Set background color to white
        g.fillRect(0, 0, newWidth, newHeight); // Fill the image with white background
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(image, 0, 0, newWidth, newHeight, null);
        g.dispose();
        return resizedImage;
    }


    private static String encodeImageToBase64(BufferedImage image) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            byte[] bytes = baos.toByteArray();
            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
