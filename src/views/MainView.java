package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

import util.Converter;

import model.Model;

public class MainView {

	private JFrame frame;
	private Model model;
	private final JPanel panelSpritesheet = new JPanel();
	private final JFileChooser chooser = new JFileChooser();
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private FileFilter filter;

	/**
	 * Create the application.
	 */
	public MainView(Model model) {
		this.model = model;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 400, 626);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setMultiSelectionEnabled(true);
		chooser.setFileFilter(new FileNameExtensionFilter("Image files", "png",
				"gif", "jpg", "jpeg", "tiff", "tga"));

		panelSpritesheet.setBorder(new TitledBorder(null, "Preview",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelSpritesheet.setBounds(0, 286, 400, 205);
		frame.getContentPane().add(panelSpritesheet);
		panelSpritesheet.setLayout(null);

		final Spritesheet canvas = new Spritesheet();

		final JSpinner spinnerFps = new JSpinner();
		spinnerFps.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				canvas.setFps((Integer) spinnerFps.getValue());
			}
		});
		spinnerFps.setModel(new SpinnerNumberModel(30, 1, 120, 1));
		spinnerFps.setBounds(338, 171, 52, 28);
		panelSpritesheet.add(spinnerFps);

		canvas.setBounds(10, 20, 380, 151);
		canvas.setImages(model.getImages());
		panelSpritesheet.add(canvas);

		JLabel lblFramerate = new JLabel("Framerate");
		lblFramerate.setBounds(264, 177, 62, 16);
		panelSpritesheet.add(lblFramerate);

		JPanel panelFiles = new JPanel();
		panelFiles.setBorder(new TitledBorder(null, "Images",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelFiles.setBounds(0, 6, 400, 268);
		frame.getContentPane().add(panelFiles);
		panelFiles.setLayout(null);

		final JList listFiles = new JList(model.getListModel());
		listFiles.setFont(new Font("Lucida Grande", Font.PLAIN, 8));
		listFiles.setBounds(6, 18, 388, 203);
		panelFiles.add(listFiles);
		listFiles.setBorder(new LineBorder(new Color(0, 0, 0)));

		JButton buttonAdd = new JButton("Add");
		buttonAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				chooser.setVisible(true);
				int returnVal = chooser.showOpenDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						model.addFiles(chooser.getSelectedFiles());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		buttonAdd.setBounds(6, 233, 117, 29);
		panelFiles.add(buttonAdd);

		JButton btnRemoveAll = new JButton("Remove all");
		btnRemoveAll.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				model.removeAllFiles();
				canvas.repaint();
			}
		});
		btnRemoveAll.setBounds(126, 233, 117, 29);
		panelFiles.add(btnRemoveAll);

		JButton buttonRemove = new JButton("Remove selected");
		buttonRemove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				model.removeFiles(listFiles.getSelectedIndices());
				if (model.getListModel().getSize() == 0) {
					canvas.repaint();
				} else {
					canvas.updateCanvas();
				}
			}
		});
		buttonRemove.setBounds(245, 233, 149, 29);
		panelFiles.add(buttonRemove);

		JScrollPane scrollPane = new JScrollPane(listFiles);
		scrollPane.setBounds(5, 20, 389, 201);
		panelFiles.add(scrollPane);

		JPanel panelSave = new JPanel();
		panelSave.setBorder(new TitledBorder(null, "Save Spritesheet",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelSave.setBounds(0, 503, 400, 101);
		frame.getContentPane().add(panelSave);
		panelSave.setLayout(null);

		final JRadioButton rdbtnHorizontal = new JRadioButton("Horizontal");
		rdbtnHorizontal.setSelected(true);
		buttonGroup.add(rdbtnHorizontal);
		rdbtnHorizontal.setBounds(78, 26, 98, 23);
		panelSave.add(rdbtnHorizontal);

		final JRadioButton rdbtnVertical = new JRadioButton("Vertical");
		buttonGroup.add(rdbtnVertical);
		rdbtnVertical.setBounds(188, 26, 79, 23);
		panelSave.add(rdbtnVertical);

		JRadioButton rdbtnQuadratic = new JRadioButton("Quadratic");
		buttonGroup.add(rdbtnQuadratic);
		rdbtnQuadratic.setBounds(271, 26, 93, 23);
		panelSave.add(rdbtnQuadratic);

		JButton buttonSave = new JButton("Save Spritesheet");
		buttonSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int returnVal = chooser.showSaveDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					System.out.println(chooser.getSelectedFile()
							.getAbsoluteFile());
					Converter.Format format;

					if (rdbtnHorizontal.isSelected()) {
						format = Converter.Format.HORIZONTAL;
					} else if (rdbtnVertical.isSelected()) {
						format = Converter.Format.VERTICAL;
					} else {
						format = Converter.Format.QUADRATIC;
					}

					try {
						ImageIO.write(
								Converter.convert(model.getImages(), format),
								"png", new File(chooser.getSelectedFile()
										.getAbsoluteFile().toString()));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		buttonSave.setBounds(225, 61, 146, 29);
		panelSave.add(buttonSave);

		JCheckBox chckbxSaveXmlFoo = new JCheckBox("Export Template");
		chckbxSaveXmlFoo.setEnabled(false);
		chckbxSaveXmlFoo.setBounds(78, 62, 135, 23);
		panelSave.add(chckbxSaveXmlFoo);

		JLabel lblFormat = new JLabel("Format");
		lblFormat.setBounds(16, 30, 61, 16);
		panelSave.add(lblFormat);

		JLabel lblTemplate = new JLabel("Template");
		lblTemplate.setBounds(16, 66, 61, 16);
		panelSave.add(lblTemplate);

		new DropTarget(frame, new DropTargetListener() {

			@Override
			public void dropActionChanged(DropTargetDragEvent dtde) {
				// TODO Auto-generated method stub

			}

			@Override
			public void drop(DropTargetDropEvent dtde) {
				Transferable t = dtde.getTransferable();

				if (t.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
					dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
					try {
						List list = (List) t.getTransferData(DataFlavor.javaFileListFlavor);
						ArrayList<File> files = new ArrayList<File>();
						for (int i = 0; i < list.size(); i++) {
							File file = (File) list.get(i);
							String filename = file.getName();
							if(filename.endsWith("png") || filename.endsWith("gif") || filename.endsWith("jpg") || filename.endsWith("jpeg") || filename.endsWith("tiff") || filename.endsWith("tga")) {
								files.add(file);
							}
						}
						File[] f = new File[files.size()];
						model.addFiles(files.toArray(f));

					} catch (UnsupportedFlavorException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			public void dragOver(DropTargetDragEvent dtde) {

			}

			@Override
			public void dragExit(DropTargetEvent dte) {

			}

			@Override
			public void dragEnter(DropTargetDragEvent dtde) {

			}
		});

		frame.setVisible(true);
	}
}
