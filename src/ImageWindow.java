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
	private final static int WIDTH = 940;
	private final static int HEIGHT = 410;

	private final static int IMAGE_WIDTH = 300;
	private final static int IMAGE_HEIGHT = 300;
	private final static int RADIO_BUTTONS_WIDTH = 300;
	private final static int DIVIDER_HEIGHT = 350;

	private JFrame window;

	private JPanel loadPanel;
	private JPanel mixPanel;

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
		window.setLayout(new MigLayout("wrap 2"));

		loadPanel.add(load);
		loadPanel.add(loadedImage);

		mixPanel.add(new JSeparator(SwingConstants.VERTICAL), "span 1 16, height " + DIVIDER_HEIGHT);
		mixPanel.add(new JLabel("Red"));
		mixPanel.add(new JSeparator(SwingConstants.VERTICAL), "span 1 16, height " + DIVIDER_HEIGHT);
		mixPanel.add(mixedImage, "span 1 15");

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

					mixPanel.setVisible(true);
				}
			}
		});
	}
}
