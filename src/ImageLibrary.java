/**
 * @author Edgar Ghahramanyan <edgarquill@gmail.com>
 * @version Version 1
 * @since 1.6
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

/**
 * Contains basic functions to do pixel image manipulations.
 */
public class ImageLibrary
{
	public final static int RED = 0;
	public final static int GREEN = 1;
	public final static int BLUE = 2;

	/**
	 * Takes an image and mixes rgb values, and can inverse them too.
	 * @param image The image that you want mixed.
	 * @param red What red value should be replaced with.
	 * @param green What green value should be replaced with.
	 * @param blue What blue value should be replaced with.
	 * @param inverse True if you would like to inverse the colors.
	 * @return Image with color values changed based on parameters passed.
	 */
	public static BufferedImage mix(BufferedImage image, int red, int green, int blue, boolean inverse)
	{
		int width = image.getWidth();
		int height = image.getHeight();
		int rgb[] = new int[width * height];
		image.getRGB(0, 0, width, height, rgb, 0, width);
		// LOOP HORIZONTALLY
		for (int w = 0; w < width; w++)
		{
			// LOOP VERTICALLY
			for (int h = 0; h < height; h++)
			{
				// GET THE INDEX
				int index = h * width + w;
				// SHIFT MAGIC TO OBTAIN RGB VALUES
				int a = (rgb[index] >> 24) & 0x000000ff;
				int r = (rgb[index] >> 16) & 0x000000ff;
				int g = (rgb[index] >> 8) & 0x000000ff;
				int b = rgb[index] & 0x000000ff;

				// IF WE WOULD ALSO LIKE IMAGES TO BE INVERTED
				if (inverse)
				{
					r = 255 - r;
					g = 255 - g;
					b = 255 - b;
				}

				// READY TO REAPPLY COLORS BACK
				int color = 0x00000000;
				color = color | (a << 24);

				// APPLY RED
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

				// APPLY GREEN
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

				// APPLY BLUE
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

				// SET THE PIXEL
				image.setRGB(w, h, color);
			}
		}
		return image;
	}

	/**
	 * Does a deep copy of the given image, and returns new instance of it,
	 * at a new memory location.
	 * @param image Image that you want deep copied.
	 * @return Deep copy of the given image.
	 */
	public static BufferedImage deepCopy(BufferedImage image)
	{
		ColorModel cm = image.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = image.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	/**
	 * Resizes the given image. It will resize either based on height or width,
	 * depending on which is longer.
	 * @param img The image that you want resized, resizing will be done by either width or height depending on which is longer..
	 * @param width The new width of the given image.
	 * @param height The new height of the given image.
	 * @return Newly resized image that is a copy of the given image.
	 */
	public static BufferedImage resize(BufferedImage img, int width, int height)
	{
		int imgWidth = img.getWidth();
		int imgHeight = img.getHeight();
		// DETERMINE IF WE WANT TO RESIZE BASED ON WIDTH OR HEIGHT
		if (imgWidth * height < imgHeight * width)
		{
			// RESIZE BASED ON HEIGHT
			width = imgWidth * height / imgHeight;
		}
		else
		{
			// RESIZE BASED ON WIDTH
			height = imgHeight * width / imgWidth;
		}
		// NEW RESIZED BLANK IMAGE
		BufferedImage newImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = newImage.createGraphics();
		// RESIZE
		try
		{
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g.clearRect(0, 0, width, height);
			g.drawImage(img, 0, 0, width, height, null);
		}
		finally
		{
			g.dispose();
		}
		return newImage;
	}
}
