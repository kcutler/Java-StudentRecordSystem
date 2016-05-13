package edu.mills.cs64.final_project;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;


/**
 * A representation of an academic course. Course information
 * can be loaded from a file through the method {@link #loadCourses(String)}.
 * After this method has been called, courses can be retrieved
 * through the method {@link #getCourse(String)}.
 * 
 * @author B0048993
 * @version 28 April 2016
 */
public class Course
{
	private String department;
	private int number;
	private String name;
	private int credits;
	private CoreRequirement[] requirementsMet;
	private static Hashtable<String, Course> courses;
	private static final String COURSES_FILE = "courses.txt";
	private static Hashtable<CoreRequirement, List<Course>> coursesMeetingRequirements;

	/**
	 * Constructs a new Course with the given information.
	 * 
	 * 
	 * @param department the department 
	 * @param number the course number
	 * @param name the course name
	 * @param credits the number of credits
	 * @param requirementsMet the list of requirements met 
	 */
	private Course(String department, int number, String name, int credits,
			CoreRequirement[] requirementsMet) {
		this.department = department;
		this.number = number;
		this.name = name;
		this.credits = credits;
		this.requirementsMet = requirementsMet;
	}


	/**
	 * Loads course information from the specified file, after
	 * which the information can be retrieved by calling 
	 * {@link #getCourse(String)}.
	 * <p>
	 * The file should consist of records in the following
	 * format:
	 * <pre>
	 *     DEPARTMENT NUMBER
	 *     NAME
	 *     CREDITS
	 *     REQUIREMENTS MET (comma-separated list)
	 * </pre>
	 * Here is a sample file:
	 * <pre>
	 *     CS 64
	 *     Computer Concepts and Intermediate Programming
	 *     4
	 *     QL
	 *     ARTH 18
	 *     Introduction to Western Art
	 *     3
	 *     CA, CIE, IP
	 * </pre>
	 * All courses in the file must meet at least one requirement.
	 * <p>
	 * Results are undefined if the file is not in the proper format.
	 * (In other words, there is no error checking and recovery.)
	 * 
	 * @param filename the name of the file with course information
	 * @throws FileNotFoundException if the file cannot be found
	 */
	public static void loadCourses(String filename) throws FileNotFoundException
	{
		courses = new Hashtable<String, Course>();
		coursesMeetingRequirements = new Hashtable<CoreRequirement, List<Course>>();
		List<Course> courseList;			
		File inputFile = new File(COURSES_FILE);
		Scanner scanner = new Scanner(inputFile);
		while (scanner.hasNextLine()) {
			String[] splitLine1 = scanner.nextLine().split(" ");
			String department = splitLine1[0];
			int number = Integer.parseInt(splitLine1[1]);
			String name = scanner.nextLine();
			int credits = Integer.parseInt(scanner.nextLine());
			String[] splitLine4 = scanner.nextLine().split(", ");
			CoreRequirement[] requirementsMet = new CoreRequirement[splitLine4.length];
			for (int i = 0; i < requirementsMet.length; i++)
			{
				requirementsMet[i] = CoreRequirement.valueOf(splitLine4[i]);
			}
			Course course = new Course(department, number, name, credits, requirementsMet);
			courses.put(course.getShortName(), course);

			for (CoreRequirement cr : requirementsMet) {
				if (coursesMeetingRequirements.containsKey(cr)) {
					courseList = coursesMeetingRequirements.get(cr);
					courseList.add(course);
				}
				else {
					courseList = new ArrayList<Course>();
					courseList.add(course);
				}
				coursesMeetingRequirements.put(cr, courseList);
			}
		}
		scanner.close();
	} 



	/**
	 * Gets a course that was previously loaded through a call
	 * to {@link #loadCourses(String)}.
	 * 
	 * @param shortName the short name of the course, as would be
	 *     returned by {@link #getShortName()}
	 * @return the course, or null if it cannot be found
	 * @throws IllegalStateException if courses have not yet been
	 *     loaded
	 */
	public static Course getCourse(String shortName) 
			throws IllegalStateException
	{
		try {
			Course course = courses.get(shortName);
			if (course != null) {
				return course;
			} else {
				return null;
			}
		} catch (IllegalStateException e) {
			System.err.println(e.toString());
			return null;
		}
	}

	/**
	 * Gets a list of courses that meets the provided 
	 * Core Requirement.
	 * 
	 * @param the core requirement
	 * @return list of courses that meet CoreRequirement
	 */
	public static List<Course> getCoursesMeetingRequirements(CoreRequirement cr) { 
		if (cr != null) {
			return coursesMeetingRequirements.get(cr);
		}
		return null;
	}
	
	
	/**
	 * Gets a short version of the name of this course.
	 * This consists of the department and the number, e.g.,
	 * "CS 64".
	 * 
	 * @return a short version of the name of this course
	 */
	public String getShortName()
	{
		return department + " " + number;
	}

	/**
	 * Gets the department of this course.
	 * 
	 * @return the department 
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * Gets the number of this course. 
	 * 
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Gets the name of this course.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the number of credits for this course. 
	 * 
	 * @return the number of credits 
	 */	
	public int getCredits() {
		return credits;
	}

	/**
	 * Gets the core requirements met by this course. 
	 * 
	 * @return the requirements met by this course
	 */
	public CoreRequirement[] getRequirementsMet() {
		return requirementsMet;
	}

	/**
	 * Gets the hashtable with a core requirement as the key
	 * and a list of courses as values. 
	 * 
	 * @return the courses that meet each core requirement 
	 */
	public Hashtable<CoreRequirement, List<Course>> getcoursesMeetingRequirements() {
		return coursesMeetingRequirements;
	}

	/**
	 * Overrides the toString method.
	 * 
	 * @return a string representation of the course 
	 * department, number, name and credits. 
	 */
	@Override
	public String toString()
	{
		return department + " " + number + ": " + name + "(" + credits + ")";
	}

}
