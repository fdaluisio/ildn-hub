package net.ildn;

public class NewsItemRow {

	private String creatore = "";
	private String datapub = "";
	private String title = "";
	private String description = "";

	public void setCreatore(String a) {
		this.creatore = a.trim();
	}

	/**
	 * @return the datapub
	 */
	public String getDatapub() {
		return datapub;
	}

	/**
	 * @param datapub
	 *            the datapub to set
	 */
	public void setDatapub(String datapub) {
		this.datapub = datapub;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the creatore
	 */
	public String getCreatore() {
		return creatore;
	}
}
