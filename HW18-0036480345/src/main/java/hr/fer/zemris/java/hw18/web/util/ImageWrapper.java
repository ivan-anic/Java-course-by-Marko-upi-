package hr.fer.zemris.java.hw18.web.util;

import java.util.List;

/**
 * A class which represents a single image on the disk. Holds the following
 * data:
 * <ul>
 * <li>the name</li>
 * <li>the description</li>
 * <li>the associated tags</li>
 * </ul>
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class ImageWrapper {

	/** The name of this image. */
	private String name;

	/** The description of this image. */
	private String description;

	/** The tags associated with this image. */
	private List<String> tags;

	/**
	 * Instantiates a new image wrapper.
	 *
	 * @param name
	 *            the name of this image
	 * @param desc
	 *            the desc of this image
	 * @param tags
	 *            the tags associated with this image
	 */
	public ImageWrapper(String name, String desc, List<String> tags) {
		super();
		this.name = name;
		this.description = desc;
		this.tags = tags;
	}

	/**
	 * Gets the name of this image
	 *
	 * @return the name of this image
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the description of this image
	 *
	 * @return the description of this image
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the tags associated with this image
	 *
	 * @return the tags associated with this image
	 */
	public List<String> getTags() {
		return tags;
	}

	@Override
	public String toString() {
		return "ImageWrapper [name=" + name + ", description=" + description + ", tags=" + tags + "]";
	}

}
