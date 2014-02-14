import net.miginfocom.swing.MigLayout;

import javax.swing.*;

/**
 * User: Edgar
 * Date: 2/13/14
 * Time: 7:49 PM
 */
public class ImageWindow
{
	private final static int WIDTH = 500;
	private final static int HEIGHT = 500;

	private JFrame window;

	private JButton fromButton;
	private JFileChooser fromChooser;
	private JButton toButton;
	private JFileChooser toChooser;
	private JLabel fromImage;
	private JLabel mixedImage;
	private JButton mixButton;

	private JRadioButton[] buttons;
	private ButtonGroup red;
	private ButtonGroup green;
	private ButtonGroup blue;

	public ImageWindow()
	{
		initItems();
		addItems();

		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	private void initItems()
	{
		window = new JFrame("RGB Mixer");
		window.setSize(WIDTH, HEIGHT);

		fromButton = new JButton("Choose Any Image");
		fromChooser = new JFileChooser();
		fromImage = new JLabel();

		toButton = new JButton("Saved Mixed Image");
		toChooser = new JFileChooser();
		mixedImage = new JLabel();

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

		window.add(fromButton);
		window.add(new JSeparator(SwingConstants.VERTICAL), "span 1 16, height 350");
		window.add(new JLabel("Red"));
		window.add(new JSeparator(SwingConstants.VERTICAL), "span 1 16, height 350");
		window.add(mixedImage, "span 1 15");

		window.add(fromImage, "span 1 15");

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
		window.add(toButton);

		window.setVisible(true);
		window.pack();
	}
}
