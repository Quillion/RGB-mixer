import java.awt.image.BufferedImage;

/**
 * User: Edgar
 * Date: 2/14/14
 * Time: 6:59 PM
 */
public class ImageLibrary
{
	public final static int RED = 0;
	public final static int GREEN = 1;
	public final static int BLUE = 2;

	public static BufferedImage mix(BufferedImage image, int red, int green, int blue)
	{
		int width = image.getWidth();
		int height = image.getHeight();
		int rgb[] = new int[width * height];
		image.getRGB(0, 0, width, height, rgb, 0, width);
		for (int w = 0; w < width; w++)
		{
			for (int h = 0; h < height; h++)
			{
				int index = h * width + w;
				int a = (rgb[index] >> 24) & 0x000000ff;
				int r = (rgb[index] >> 16) & 0x000000ff;
				int g = (rgb[index] >> 8) & 0x000000ff;
				int b = rgb[index] & 0x000000ff;

				int color = 0x00000000;
				color = color | (a << 24);

				if (red == RED)
				{
					color = color | (r << 16);
				}
				else if (red == GREEN)
				{
					color = color | (g << 16);
				}
				else if (red == BLUE)
				{
					color = color | (b << 16);
				}

				if (green == RED)
				{
					color = color | (r << 8);
				}
				else if (green == GREEN)
				{
					color = color | (g << 8);
				}
				else if (green == BLUE)
				{
					color = color | (b << 8);
				}

				if (blue == RED)
				{
					color = color | r;
				}
				else if (blue == GREEN)
				{
					color = color | g;
				}
				else if (blue == BLUE)
				{
					color = color | b;
				}

				image.setRGB(w, h, color);
			}
		}
		return image;
	}
}
