package edu.mills.cs64.final_project;
/**
 * A record of a course taken, such as might appear in a transcript,
 * including the department and number of the course, the year it was 
 * taken, and the grade received.
 * 
 * @author B0048993
 * @version 10 April 2016
 */
public class GradeRecord {
	private Course course;
	private int year;
	private String grade;

	/**
	 * Constructs a new GradeRecord with the given information.
	 * 
	 * @param course the course
	 * @param year the year the course was taken
	 * @param grade the grade earned
	 * @throws IllegalArgumentException if the grade is not legal,
	 *     as determined by {@link #isLegalGrade(String)}
	 */
	public GradeRecord(Course course, int year, String grade)
			throws IllegalArgumentException { 

		if (!isLegalGrade(grade)) {
			throw new IllegalArgumentException (
					"New record could not be created: " + 
							course + ", " + grade + ", " + year);
		}
		else {
			this.course = course;
			this.year = year;
			this.grade = grade; 
		}
	}

	/**
	 * Checks whether a character represents a legal grade. Legal
	 * grades are "A", "B", "C", "D", and "F".
	 * 
	 * @param grade a potential grade
	 * @return true if it is a legal grade, false otherwise
	 */
	private static boolean isLegalGrade(String grade)
	{
		if (grade.equals("A") || grade.equals("B") || grade.equals("C") || 
				grade.equals("D") || grade.equals("F")) {
			return true; }
		return false;
	}


	/**
	 * Gets the course.
	 * 
	 * @return the course 
	 */
	public Course getCourse() {
		return course;
	}

	/**
	 * Gets the year. 
	 * 
	 * @return the year the course was taken 
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Gets the grade earned in this course 
	 * 
	 * @return the grade 
	 */
	public String getGrade() {
		return grade;
	}

	/**
	 * Overrides the toString method.
	 * 
	 * @return a string representation of the grade record
	 * as course, year and grade. 
	 */
	@Override
	public String toString()
	{
		return course + " " + year + ": " + grade;
	}
}
