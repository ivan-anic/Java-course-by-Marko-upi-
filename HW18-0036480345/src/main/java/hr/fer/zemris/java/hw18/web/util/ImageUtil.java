package hr.fer.zemris.java.hw18.web.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

/**
 * A utility class used by the servlets in this package.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class ImageUtil {

	/** A reference to the local images directory. */
	private static final String IMGS_PATH = "WEB-INF/slike/";
	/** A reference to the local thumbnails directory. */
	private static final String THMB_PATH = "WEB-INF/thumbnails/";
	/** A reference to the local description file. */
	private static final String DESC_PATH = "WEB-INF/opisnik.txt";
	/** A reference to the defined thumbnail size. */
	private static final int SMALL_SIZE = 150;
	/** Keeps information whether the description file has been parsed yet. */
	private static boolean parsed;
	/** A reference to the {@linkplain ImageWrapper} instances */
	private static List<ImageWrapper> images = new ArrayList<>();

	/**
	 * Parses the description file.
	 *
	 * @param req
	 *            the {@linkplain HttpServletRequest} instance
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void parseDesc(HttpServletRequest req) throws IOException {
		int i = 0;

		String name = "";
		String desc = "";

		String[] tags;

		List<String> lines = Files.readAllLines(Paths.get(req.getSession()
				.getServletContext().getRealPath(DESC_PATH)),
				Charset.forName("UTF-8"));

		for (String z : lines) {
			if (i == 0) {
				name = z.trim();
			} else if (i == 1) {
				desc = z.trim();
			} else {
				tags = z.trim().split(",");
				i = -1;
				images.add(new ImageWrapper(name, desc, Arrays.asList(tags)));
			}
			i++;
		}

		parsed = true;
	}

	/**
	 * Gets all of the tags occurred among the images.
	 *
	 * @param req
	 *            the {@linkplain HttpServletRequest} instance
	 * @return the desired tags set
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static Set<String> getTags(HttpServletRequest req) throws IOException {
		if (!parsed)
			parseDesc(req);

		Set<String> tags = new HashSet<>();
		images.forEach(z -> {
			z.getTags().forEach(y -> tags.add(y));
		});
		return tags;
	}

	/**
	 * Creates a thumbnail of the desired image (if not yet created) and returns
	 * its path on the disk.
	 *
	 * @param name
	 *            the name of the file
	 * @param req
	 *            the {@linkplain HttpServletRequest} instance
	 * @return the desired thumbnail
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static String getThumbnail(String name, HttpServletRequest req) throws IOException {

		String thumbName = req.getSession()
				.getServletContext().getRealPath(THMB_PATH + name);
		String imgName = req.getSession()
				.getServletContext().getRealPath(IMGS_PATH + name);

		if (!new File(thumbName).exists()) {

			BufferedImage image = ImageIO.read(new File(imgName));

			BufferedImage newImage = new BufferedImage(SMALL_SIZE, SMALL_SIZE, BufferedImage.TYPE_INT_RGB);
			Image im = (Image) image.getScaledInstance(SMALL_SIZE, SMALL_SIZE, Image.SCALE_SMOOTH);
			Graphics g = newImage.createGraphics();
			g.drawImage(im, 0, 0, SMALL_SIZE, SMALL_SIZE, null);
			g.dispose();
			ImageIO.write(newImage, "jpg", new File(thumbName));

		}
		return thumbName;
	}

	/**
	 * Gets the {@linkplain ImageWrapper} instance associated with a certain
	 * tag.
	 *
	 * @param tag
	 *            the specified tag
	 * @return the images set associated with the desired tag
	 */
	public static Set<ImageWrapper> getImagesForTag(String tag) {
		Set<ImageWrapper> imageNames = new HashSet<>();

		images.forEach(z -> {
			z.getTags().forEach(y -> {
				if (y.equals(tag)) {
					imageNames.add(z);
				}
			});
		});
		return imageNames;
	}

	/**
	 * Gets the image {@linkplain ImageWrapper} instance with the specified
	 * name.
	 * 
	 * @param name
	 *            the name
	 * @return the image associated with the desired name
	 */
	public static ImageWrapper getImageForName(String name) {
		return images.stream().filter(z -> z.getName().equals(name)).findAny().orElse(null);

	}
}
