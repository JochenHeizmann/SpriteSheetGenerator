package views;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.Timer;

public class Spritesheet extends Canvas {

	private static final long serialVersionUID = 7225941142633924223L;
	private ArrayList<BufferedImage> images;
	private int currentFrame;
	private final Timer timer;

	public Spritesheet() {
		timer = new Timer(30, new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				updateCanvas();

			}
		});
		timer.start();
	}

	public void setFps(final int fps) {
		timer.setDelay(1000 / fps);
	}

	public void setImages(final ArrayList<BufferedImage> images) {
		this.images = images;
		currentFrame = 0;
		repaint();
	}

	public void updateCanvas() {
		if (images != null && images.size() > 1) {
			if (++currentFrame >= images.size()) {
				currentFrame = 0;
			}

			repaint();
		}

	}

	@Override
	public void paint(final Graphics g) {
		Rectangle r = g.getClipBounds();
		g.clearRect(0, 0, r.width, r.height);

		if (images != null && images.size() > 0) {
			final BufferedImage image = images.get(currentFrame);

			g.drawImage(image, r.width / 2 - image.getWidth() / 2, r.height / 2
					- image.getHeight() / 2, null);
		}
	}
}
