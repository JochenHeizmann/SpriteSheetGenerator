package util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Converter {

	public enum Format {
		HORIZONTAL, VERTICAL, QUADRATIC
	}

	public static BufferedImage convert(ArrayList<BufferedImage> images,
			Format format) {
		BufferedImage image = images.get(0);
		int width = image.getWidth();
		int height = image.getHeight();

		BufferedImage bi;

		if (format == Format.HORIZONTAL) {
			bi = tryToAllocateHeapSpace(width * images.size(), height);
			Graphics2D result = bi.createGraphics();

			for (int i = 0; i < images.size(); i++) {
				result.drawImage(images.get(i), null, i * width, 0);
			}
		} else if (format == Format.VERTICAL) {
			bi = tryToAllocateHeapSpace(width, height * images.size());
			Graphics2D result = bi.createGraphics();

			for (int i = 0; i < images.size(); i++) {
				result.drawImage(images.get(i), null, 0, i * height);
			}
		} else {

			double sqrt = (double) Math.sqrt(images.size());
			int cols = (int) Math.ceil(sqrt);
			float foo = (float) images.size() / cols;
			int rows = (int) Math.ceil(foo);

			bi = tryToAllocateHeapSpace(width * cols, height * rows);
			Graphics2D result = bi.createGraphics();

			for (int i = 0; i < images.size(); i++) {
				int col = i % cols;
				int row = i / cols;
				result.drawImage(images.get(i), null, col * width, row * height);
			}
		}

		return bi;
	}

	private static BufferedImage tryToAllocateHeapSpace(int width, int height) {
		System.out.println(width + "/" + height);
		if (width * height * 3 > 64000000) {
			// Heap space
		}

		return new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
	}
}
