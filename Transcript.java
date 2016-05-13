package edu.mills.cs64.final_project;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * A Mills College transcript. This only includes courses that
 * fulfill core requirements.
 * 
 * @author B0048993
 * @version 28 April 2016
 */
public class Transcript
{
	private String id;
	private String firstName;
	private String lastName;
	private List<GradeRecord> gradeRecords;
	private int requirementsMet[] = new int[CoreRequirement.values().length];

	/**
	 * Constructs a transcript from a file. The first three lines 
	 * describe the student:
	 * <pre>
	 * ID
	 * FIRST_NAME
	 * LAST_NAME
	 * </pre>
	 * They are followed by zero or more grade records in the 
	 * following format:
	 * <pre>
	 * DEPARTMENT NUMBER
	 * YEAR
	 * GRADE
	 * </pre>
	 * Here is a sample file:
	 * <pre>
	 * B00000123
	 * Susan
	 * Mills
	 * CS 63
	 * 2015
	 * B
	 * CS 64
	 * 2016
	 * A
	 * </pre>
	 * Grade records are only added to the transcript if the course
	 * is found by {@link Course#getClass()}.
	 * <p>
	 * Results are undefined if the file is not in the appropriate format.
	 * (In other words, this method does not do any error checking.)
	 * 
	 * @param filename the name of the transcript file
	 * @throws FileNotFoundException if the file cannot be found
	 */
	public Transcript(String filename) throws FileNotFoundException
	{
		File inputFile = new File(filename);
		Scanner scanner = new Scanner(inputFile);
		gradeRecords = new ArrayList<GradeRecord>();

		while (scanner.hasNextLine()) {
			id = scanner.nextLine(); 
			firstName = scanner.nextLine();
			lastName = scanner.nextLine();

			while (scanner.hasNextLine()) {
				Course course = Course.getCourse(scanner.nextLine());
				int year = Integer.parseInt(scanner.nextLine());
				String grade = scanner.nextLine();
				if (course != null) {
					gradeRecords.add(new GradeRecord(course, year, grade));
					for (CoreRequirement cr : course.getRequirementsMet()){
						requirementsMet[cr.ordinal()] += course.getCredits();
					}
				}
			}
		}
		scanner.close(); 
	}

	/**
	 * A second constructor that takes a file 
	 * as an argument. Its behavior is identical
	 * to the first constructor. 
	 * 
	 * @param file
	 * @throws FileNotFoundException
	 */
	public Transcript(File file) throws FileNotFoundException 
	{
		this(file.getName());
	}

	/**
	 * Recommends courses to satisfy the remaining requirements.
	 * If all requirements are met, this will provide a statement
	 * to that effect. This never recommends courses that are already
	 * on the transcript.
	 * 
	 * @return the recommendations or a statement that all are met
	 */
	public String recommendCourses() {
		String recommendedCourses = "";
		List<Course> coursesTaken = new ArrayList<Course>();

		for (GradeRecord gr : gradeRecords) {
			coursesTaken.add(gr.getCourse());
		}

		for (CoreRequirement cr : CoreRequirement.values()) {
			if (requirementsMet[cr.ordinal()] < cr.getCredits()) {
				recommendedCourses += "\n" + "To satisfy the " + cr.getTitle() 
				+ " requirement take any of: " ; 
				for (Course c : Course.getCoursesMeetingRequirements(cr)) { 
					if (!coursesTaken.contains(c)){
						recommendedCourses += "\n" + "\t" + c;
					}
				}
			}
		}
		return recommendedCourses;
	}


	/**
	 * Tests the program by loading in courses from "courses.txt"
	 * and a transcript from "transcript.txt".
	 * 
	 * @param args ignored
	 */
	public static void main(String[] args)
	{
		// You should not modify this method, except possibly
		// temporarily, when testing, after which you should
		// change it back.
		try {
			// Read in courses.
			Course.loadCourses("courses.txt");
			System.out.println("Course.getCourse(\"CS 63\"): " + 
					Course.getCourse("CS 63"));

			// Read in transcript.
			Transcript transcript = new Transcript("transcript2.txt");
			System.out.println(transcript);
			System.out.println(transcript.recommendCourses());

		} catch (FileNotFoundException e) {
			System.err.println(e.toString());
			return;
		}
	}

	/**
	 * Gets the Mills id of the student for whom the transcript was generated.
	 * 
	 * @return the Mills id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Gets the first name of the student for whom the transcript was generated.
	 * 
	 * @return the first name 
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Gets the last name of the student for whom the transcript was generated.
	 * 
	 * @return the last name 
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Gets the grade records in this transcript. 
	 * 
	 * @return the grade records
	 */
	public List<GradeRecord> getGradeRecords() {
		return gradeRecords;
	}

	/**
	 * Overrides the toString method.
	 * 
	 * @return a string representation of the transcript
	 * with the full name, id, and a list of grade records
	 */
	@Override
	public String toString()
	{
		int count = 0;
		String coreRequirementMet;
		String studentCourses = "Transcript for " + firstName 
				+ " " + lastName + "\n" + "id: " + id;

		for (GradeRecord gr : this.getGradeRecords()) {
			studentCourses += "\n" +  gr + " *";
			for (CoreRequirement cr : gr.getCourse().getRequirementsMet()) {
				studentCourses += cr + " ";
			};
		}

		coreRequirementMet =  "\n" + "\n" + "Core requirements: ";
		for (CoreRequirement cr : CoreRequirement.values()) {
			coreRequirementMet += "\n" + cr.getTitle() + " (" + cr 
					+ "): " + requirementsMet[count] + "/" + cr.getCredits();
			count++;
		}

		return studentCourses + coreRequirementMet;
	}
}
