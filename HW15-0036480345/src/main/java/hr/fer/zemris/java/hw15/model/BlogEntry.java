package hr.fer.zemris.java.hw15.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * A class which represents an object implementation of a blog entry instance
 * from the database.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
@NamedQueries({
		@NamedQuery(name = "BlogEntry.upit1", query = "select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when")
})
@Entity
@Table(name = "blog_entries")
@Cacheable(true)
public class BlogEntry {

	/** The id. */
	@Id
	@GeneratedValue
	private Long id;

	/** The comments. */
	@OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("postedOn")
	private List<BlogComment> comments = new ArrayList<>();

	/** The time this entry was created. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date createdAt;

	/** The time this entry was last modified. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	private Date lastModifiedAt;

	/** The title. */
	@Column(length = 40, nullable = false)
	private String title;

	/** The text. */
	@Column(length = 4096, nullable = false)
	private String text;

	/** The author. */
	@ManyToOne
	@JoinColumn(nullable = false)
	private BlogUser author;

	/**
	 * Gets the author.
	 *
	 * @return the author
	 */
	public BlogUser getAuthor() {
		return author;
	}

	/**
	 * Sets the author.
	 *
	 * @param author
	 *            the new author
	 */
	public void setAuthor(BlogUser author) {
		this.author = author;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the comments.
	 *
	 * @return the comments
	 */
	public List<BlogComment> getComments() {
		return comments;
	}

	/**
	 * Sets the comments.
	 *
	 * @param comments
	 *            the new comments
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * Gets the time this entry was created.
	 *
	 * @return the time this entry was created
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Sets the time this entry was created.
	 *
	 * @param createdAt
	 *            the time this entry was created
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Gets the time this entry was created
	 *
	 * @return the time this entry was created
	 */
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Sets the time this entry was last modified
	 * 
	 * @param lastModifiedAt
	 *            the time this entry was last modified
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title
	 *            the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text.
	 *
	 * @param text
	 *            the new text
	 */
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}