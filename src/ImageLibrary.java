import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

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

	public static BufferedImage deepCopy(BufferedImage image)
	{
		ColorModel cm = image.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = image.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	public static BufferedImage resize(BufferedImage img, int width, int height)
	{
		int imgWidth = img.getWidth();
		int imgHeight = img.getHeight();
		if (imgWidth * height < imgHeight * width)
		{
			width = imgWidth * height / imgHeight;
		}
		else
		{
			height = imgHeight * width / imgWidth;
		}
		BufferedImage newImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = newImage.createGraphics();
		try
		{
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g.clearRect(0, 0, width, height);
			g.drawImage(img, 0, 0, width, height, null);
		} finally
		{
			g.dispose();
		}
		return newImage;
	}
}
