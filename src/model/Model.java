package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;

public class Model {

	private DefaultListModel listModel;
	private ArrayList<BufferedImage> images;

	public Model() {
		listModel = new DefaultListModel();
		images = new ArrayList<BufferedImage>();
	}

	public DefaultListModel getListModel() {
		return listModel;
	}

	public ArrayList<BufferedImage> getImages() {
		return images;
	}

	public void addFiles(File[] files) throws IOException {
		for (int i = 0; i < files.length; i++) {
			if (!listModel.contains(files[i])) {
				listModel.addElement(files[i]);
				images.add(ImageIO.read(files[i]));
			}
		}
	}

	public void removeFiles(int[] indices) {
		for (int i = indices.length - 1; i > -1; --i) {
			listModel.remove(indices[i]);
			images.remove(indices[i]);
		}
	}

	public void removeAllFiles() {
		listModel.clear();
		images.clear();
	}

	public File[] getFiles() {
		File[] result = new File[listModel.getSize()];
		for (int i = 0; i < listModel.getSize(); i++) {
			result[i] = (File) listModel.getElementAt(i);
		}
		return result;
	}

}
