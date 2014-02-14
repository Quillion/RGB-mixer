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
 * User: Edgar
 * Date: 2/13/14
 * Time: 7:49 PM
 */
public class ImageWindow
{
	private final static int WIDTH = 500;
	private final static int HEIGHT = 500;

	private final static int IMAGE_WIDTH = 300;
	private final static int IMAGE_HEIGHT = 300;

	private JFrame window;

	private JButton load;
	private JButton save;
	private JLabel loadedImage;
	private JLabel mixedImage;
	private JButton mixButton;

	private JRadioButton[] buttons;
	private ButtonGroup red;
	private ButtonGroup green;
	private ButtonGroup blue;

	private BufferedImage original;
	private BufferedImage mixed;

	public ImageWindow()
	{
		initItems();
		addItems();
		initActions();
	}

	private void initItems()
	{
		window = new JFrame("RGB Mixer");
		window.setSize(WIDTH, HEIGHT);

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

		red = new ButtonGroup();
		red.add(buttons[0]);
		red.add(buttons[3]);
		red.add(buttons[6]);

		green = new ButtonGroup();
		green.add(buttons[1]);
		green.add(buttons[4]);
		green.add(buttons[7]);

		blue = new ButtonGroup();
		blue.add(buttons[2]);
		blue.add(buttons[5]);
		blue.add(buttons[8]);
	}

	private void addItems()
	{
		window.setLayout(new MigLayout("wrap 5"));

		window.add(load);
		window.add(new JSeparator(SwingConstants.VERTICAL), "span 1 16, height 350");
		window.add(new JLabel("Red"));
		window.add(new JSeparator(SwingConstants.VERTICAL), "span 1 16, height 350");
		window.add(mixedImage, "span 1 15");

		window.add(loadedImage, "span 1 15");

		window.add(buttons[0]);
		window.add(buttons[3]);
		window.add(buttons[6]);
		window.add(new JSeparator(SwingConstants.HORIZONTAL), "width 75");

		window.add(new JLabel("Green"));
		window.add(buttons[1]);
		window.add(buttons[4]);
		window.add(buttons[7]);
		window.add(new JSeparator(SwingConstants.HORIZONTAL), "width 75");

		window.add(new JLabel("Blue"));
		window.add(buttons[2]);
		window.add(buttons[5]);
		window.add(buttons[8]);
		window.add(new JSeparator(SwingConstants.HORIZONTAL), "width 75");

		window.add(mixButton);
		window.add(save);

		window.setVisible(true);
		window.pack();
	}

	private void initActions()
	{
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		load.addActionListener(new ActionListener()
		{
			private JFileChooser imageChooser = null;//new JFileChooser();
			private File file = null;

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

						loadedImage.setIcon(new ImageIcon(original));
						mixedImage.setIcon(new ImageIcon(mixed));
					}
					catch (Exception exp)
					{
					}
				}
			}
		});
	}
}
