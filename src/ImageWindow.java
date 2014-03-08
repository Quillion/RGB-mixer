/**
 * @author Edgar Ghahramanyan <edgarquill@gmail.com>
 * @version Version 1
 * @since 1.6
 */

import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Contains gui interface for loading and saving images and
 * calling functions to manipulate pixels.
 */
public class ImageWindow
{
	// MAIN WINDOW HEIGHT AND WIDTH
	private final static int WIDTH = 940;
	private final static int HEIGHT = 435;

	// ETC SIZES FOR ELEMENTS
	private final static int IMAGE_WIDTH = 300;
	private final static int IMAGE_HEIGHT = 300;
	private final static int RADIO_BUTTONS_WIDTH = 300;
	private final static int DIVIDER_HEIGHT = 370;

	// WINDOW ELEMENTS
	private JFrame window;

	private JPanel loadPanel;
	private JPanel mixPanel;

	private JButton load;
	private JButton save;
	private JLabel loadedImage;
	private JLabel mixedImage;
	private JButton mixButton;

	private JRadioButton[] buttons;

	private JCheckBox inverse;

	private BufferedImage original;
	private BufferedImage mixed;

	private File file;

	public ImageWindow()
	{
		initItems();
		addItems();
		initActions();

		mixPanel.setVisible(false);

		window.setResizable(false);
		window.setVisible(true);
		window.setSize(new Dimension(WIDTH, HEIGHT));
		window.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		window.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		window.setMaximumSize(new Dimension(WIDTH, HEIGHT));
	}

	private void initItems()
	{
		window = new JFrame("RGB Mixer");

		loadPanel = new JPanel(new MigLayout("wrap 1"));
		mixPanel = new JPanel(new MigLayout("wrap 4"));

		load = new JButton("Choose Any Image");
		loadedImage = new JLabel();
		loadedImage.setSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
		loadedImage.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
		loadedImage.setMinimumSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
		loadedImage.setMaximumSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));

		save = new JButton("Saved Mixed Image");
		mixedImage = new JLabel();
		mixedImage.setSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
		mixedImage.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
		mixedImage.setMinimumSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
		mixedImage.setMaximumSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));

		buttons = new JRadioButton[9];
		buttons[0] = new JRadioButton("Red");
		buttons[1] = new JRadioButton("Red");
		buttons[2] = new JRadioButton("Red");
		buttons[3] = new JRadioButton("Green");
		buttons[4] = new JRadioButton("Green");
		buttons[5] = new JRadioButton("Green");
		buttons[6] = new JRadioButton("Blue");
		buttons[7] = new JRadioButton("Blue");
		buttons[8] = new JRadioButton("Blue");
		buttons[0].setSelected(true);
		buttons[4].setSelected(true);
		buttons[8].setSelected(true);

		mixButton = new JButton("Mix...");

		ButtonGroup red = new ButtonGroup();
		red.add(buttons[0]);
		red.add(buttons[3]);
		red.add(buttons[6]);

		ButtonGroup green = new ButtonGroup();
		green.add(buttons[1]);
		green.add(buttons[4]);
		green.add(buttons[7]);

		ButtonGroup blue = new ButtonGroup();
		blue.add(buttons[2]);
		blue.add(buttons[5]);
		blue.add(buttons[8]);

		inverse = new JCheckBox("inverse");
	}

	private void addItems()
	{
		window.setLayout(new MigLayout("wrap 2"));

		loadPanel.add(load);
		loadPanel.add(loadedImage);

		mixPanel.add(new JSeparator(SwingConstants.VERTICAL), "span 1 18, height " + DIVIDER_HEIGHT);
		mixPanel.add(new JLabel("Red"));
		mixPanel.add(new JSeparator(SwingConstants.VERTICAL), "span 1 18, height " + DIVIDER_HEIGHT);
		mixPanel.add(mixedImage, "span 1 17");

		mixPanel.add(buttons[0]);
		mixPanel.add(buttons[3]);
		mixPanel.add(buttons[6]);
		mixPanel.add(new JSeparator(SwingConstants.HORIZONTAL), "width " + RADIO_BUTTONS_WIDTH);

		mixPanel.add(new JLabel("Green"));
		mixPanel.add(buttons[1]);
		mixPanel.add(buttons[4]);
		mixPanel.add(buttons[7]);
		mixPanel.add(new JSeparator(SwingConstants.HORIZONTAL), "width " + RADIO_BUTTONS_WIDTH);

		mixPanel.add(new JLabel("Blue"));
		mixPanel.add(buttons[2]);
		mixPanel.add(buttons[5]);
		mixPanel.add(buttons[8]);
		mixPanel.add(new JSeparator(SwingConstants.HORIZONTAL), "width " + RADIO_BUTTONS_WIDTH);

		mixPanel.add(inverse);
		mixPanel.add(new JSeparator(SwingConstants.HORIZONTAL), "width " + RADIO_BUTTONS_WIDTH);

		mixPanel.add(mixButton);
		mixPanel.add(save);

		window.add(loadPanel);
		window.add(mixPanel);
	}

	private void initActions()
	{
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		load.addActionListener(new ActionListener()
		{
			private JFileChooser imageChooser = null;

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (imageChooser == null)
				{
					imageChooser = new JFileChooser();
					imageChooser.setMultiSelectionEnabled(false);
					imageChooser.setAcceptAllFileFilterUsed(false);
					// Get array of available formats
					String[] suffices = ImageIO.getReaderFileSuffixes();

					// Add a file filter for each one
					for (String suffice : suffices)
					{
						FileFilter filter = new FileNameExtensionFilter(suffice + " files", suffice);
						imageChooser.addChoosableFileFilter(filter);
					}
				}
				if (file != null)
				{
					imageChooser.setSelectedFile(file);
				}
				if (imageChooser.showOpenDialog(window) == JFileChooser.APPROVE_OPTION)
				{
					file = imageChooser.getSelectedFile();
					load.setText(file.getName());
					try
					{
						original = ImageIO.read(file);
						mixed = ImageIO.read(file);

						loadedImage.setIcon(new ImageIcon(ImageLibrary.resize(original, IMAGE_WIDTH, IMAGE_HEIGHT)));
						mixedImage.setIcon(new ImageIcon(ImageLibrary.resize(mixed, IMAGE_WIDTH, IMAGE_HEIGHT)));
					}
					catch (Exception exp)
					{
						new JOptionPane(exp.toString(), JOptionPane.ERROR_MESSAGE).createDialog(window, "Error");
					}

					mixPanel.setVisible(true);
				}
			}
		});

		mixButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// FIND OUT WHICH VALUE FOR RED IS SELECTED
				int r = ImageLibrary.RED;
				if (buttons[3].isSelected())
					r = ImageLibrary.GREEN;
				else if (buttons[6].isSelected())
					r = ImageLibrary.BLUE;

				// FIND OUT WHICH VALUE FOR GREEN IS SELECTED
				int g = ImageLibrary.GREEN;
				if (buttons[1].isSelected())
					g = ImageLibrary.RED;
				else if (buttons[7].isSelected())
					g = ImageLibrary.BLUE;

				// FIND OUT WHICH VALUE FOR BLUE IS SELECTED
				int b = ImageLibrary.BLUE;
				if (buttons[2].isSelected())
					b = ImageLibrary.RED;
				else if (buttons[5].isSelected())
					b = ImageLibrary.GREEN;

				mixed = ImageLibrary.mix(ImageLibrary.deepCopy(original), r, g, b, inverse.isSelected());
				mixedImage.setIcon(new ImageIcon(ImageLibrary.resize(mixed, IMAGE_WIDTH, IMAGE_HEIGHT)));
			}
		});

		save.addActionListener(new ActionListener()
		{
			private JFileChooser saveFile;

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (saveFile == null)
				{
					// CREATE FILE CHOOSER
					saveFile = new JFileChooser();
					saveFile.setMultiSelectionEnabled(false);
					saveFile.setAcceptAllFileFilterUsed(false);
					// GET PATH OF ORIGINAL IMAGE
					String saveName = file.getAbsolutePath();
					// REMOVE IMAGE TYPE
					int end = saveName.lastIndexOf('.');

					// ADD SUFFIX BASED ON COLOR IMAGES CHOSEN
					String r = "r";
					if (buttons[3].isSelected())
						r = "g";
					else if (buttons[6].isSelected())
						r = "b";

					String g = "g";
					if (buttons[1].isSelected())
						g = "r";
					else if (buttons[7].isSelected())
						g = "b";

					String b = "b";
					if (buttons[2].isSelected())
						b = "r";
					else if (buttons[5].isSelected())
						b = "g";

					// CREATE THE NEW FILENAME
					saveName = saveName.substring(0, end) +
							r + g + b + (inverse.isSelected() ? "i" : "") +
							"." + saveName.substring(end + 1);
					// PROMPT USER IF HE WANTS TO SAVE
					saveFile.setSelectedFile(new File(saveName));
				}
				// USER CHOSE TO SAVE
				if (saveFile.showSaveDialog(window) == JFileChooser.APPROVE_OPTION)
				{
					File saveLocation = saveFile.getSelectedFile();

					int i = saveLocation.getName().lastIndexOf('.');
					if (i > 0)
					{
						String extension = saveLocation.getName().substring(i + 1);
						try
						{
							ImageIO.write(mixed, extension, saveLocation);
						}
						catch (Exception exp)
						{
							new JOptionPane(exp.toString(), JOptionPane.ERROR_MESSAGE).createDialog(window, "Error");
						}
					}
				}
			}
		});
	}
}
