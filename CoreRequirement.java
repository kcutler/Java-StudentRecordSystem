package edu.mills.cs64.final_project;

/**
 * Core requirements for General Education at Mills. 
 * 
 * @author B0048993
 * 
 */
public enum CoreRequirement { 
	CA("Critical Analysis", 3),
	WOC("Written and Oral Communication", 7),
	QL("Quantitative Literacy", 3),
	RGP("Race, Gender, and Power", 3),
	SI("Scientific Inquiry", 3),
	LOTE("Language Other Than English", 3),
	IP("International Perspectives", 3),
	CE("Community Engagement", 2),
	CIE("Creativity, Innovation, and Experimentation", 3);

	private String title;
	private int credits;

	/**
	 * Constructs a core requirement with the given information. 
	 * 
	 * @param title
	 * @param credits
	 */
	private CoreRequirement(String title, int credits) {
		this.title = title;
		this.credits = credits;			
	}

	/**
	 * Gets the number of credits to satisfy the requirement. 
	 * 
	 * @return the number of credits to satisfy this requirement 
	 */
	protected int getCredits() {
		return credits;
	}

	/**
	 * Gets the full title to satisfy the requirement. 
	 * 
	 * @return the full title of this requirement 
	 */
	protected String getTitle() {
		return title;
	}
}
