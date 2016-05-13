package edu.mills.cs64.final_project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

/**
 * Provides a GUI for getting information about transcripts and
 * core requirements. Specifically, a transcript can be loaded
 * and be displayed along with the core requirements satisfied.
 * This can also recommend courses to satisfy remaining core
 * requirements.
 * 
 * @author [replace the brackets and this text with your Mills id]
 * @version April 25, 2016
 */
public class GUI
{
  private static final String COURSES_FILE = "courses.txt";
  private Transcript currentTranscript;
  
  // GUI constants
  private static final int WIDTH = 600;
  private static final int HEIGHT = 400;
  private static final int MAX_VERTICAL_GAP = 6;
  private static final String TITLE = "Core Requirements Calculator";
  private static final String INSTRUCTIONS =
      // Beginning with the "<html>" tag enables using HTML like "<br>"
      // for line breaks.
      "<html>Find out what core requirements are satisfied by a transcript<br>" +
      "and what courses to take to satisfy the remaining requirements.<br>" +
      "Begin by loading a transcript.</html>";
  private static final String LOAD_TRANSCRIPT = "Load Transcript";
  private static final String RECOMMEND_COURSES = "Recommend Courses";
  private static final String EXIT = "Exit";

  // GUI components
  private JFrame frame;
  private JLabel instructionsLabel; // instructions to user
  private JTextArea feedbackArea;   // feedback to user
  private JTextArea transcriptArea; // where to display transcript
  private JButton loadTranscriptButton; // click on to load a transcript
  private JButton exitButton;           // click on to exit
  private JButton recommendCoursesButton;

  private GUI()
  {
    // Initialize the frame.
    frame = new JFrame(TITLE);
    frame.setSize(WIDTH, HEIGHT);
    // Make program end when this frame is closed.
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // Center the frame on the screen.
    frame.setLocationRelativeTo(null);

    // Create GUI components.
    instructionsLabel = new JLabel(INSTRUCTIONS, SwingConstants.CENTER);
    loadTranscriptButton = new JButton(LOAD_TRANSCRIPT);
    recommendCoursesButton = new JButton(RECOMMEND_COURSES);
	recommendCoursesButton.setVisible(false);
    exitButton = new JButton(EXIT);
    feedbackArea = new JTextArea();
    transcriptArea = new JTextArea();
    JScrollPane feedbackPane = new JScrollPane(feedbackArea);
    JScrollPane transcriptPane = new JScrollPane(transcriptArea);

    // Add listeners.
    loadTranscriptButton.addActionListener(new LoadTranscriptButtonHandler());
    exitButton.addActionListener(new ExitButtonHandler());
    recommendCoursesButton.addActionListener(new RecommendCoursesButtonHandler());

    // Lay out components.
    GroupLayout layout = new GroupLayout(frame.getContentPane());
    layout.setHorizontalGroup(
        layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
            .addComponent(instructionsLabel)
            .addComponent(loadTranscriptButton)
            .addComponent(recommendCoursesButton)
            .addComponent(exitButton)
            .addComponent(feedbackPane))
        .addComponent(transcriptPane));
    frame.getContentPane().setLayout(layout);
    layout.setVerticalGroup(layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup()
            .addGroup(layout.createSequentialGroup()
                .addComponent(instructionsLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                    GroupLayout.DEFAULT_SIZE, MAX_VERTICAL_GAP)
                .addComponent(loadTranscriptButton)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                    GroupLayout.DEFAULT_SIZE, MAX_VERTICAL_GAP)
                .addComponent(recommendCoursesButton)
                .addComponent(exitButton)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED,
                    GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(feedbackPane))
            .addComponent(transcriptPane)));         
  }

  public void setVisibility(boolean visibility)
  {
    frame.setVisible(visibility);
  }
  
  public class ExitButtonHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ae) {
			System.exit(0);
		}
  }
  
  public class LoadTranscriptButtonHandler implements ActionListener
  {
    // Create a file chooser that starts in the directory of the project.
    private JFileChooser fileChooser = 
        new JFileChooser(System.getProperty("user.dir"));

    @Override
    public void actionPerformed(ActionEvent ae)
    {
    	int retval = fileChooser.showOpenDialog(frame);
    	switch (retval)
    	{
    	case JFileChooser.CANCEL_OPTION:
    		feedbackArea.append("Load transcript operation cancelled.\n");
    		break;
    	case JFileChooser.APPROVE_OPTION:
    		try {
    			File file = fileChooser.getSelectedFile();
    			currentTranscript = new Transcript(file);
    			transcriptArea.append(currentTranscript.toString());
    			feedbackArea.append("You have successfully loaded a transcript.\n" 
    					+ "Filepath: " + fileChooser.getSelectedFile().getAbsolutePath());
    			recommendCoursesButton.setVisible(true);
    			break;
    		} catch (FileNotFoundException e) {
    			feedbackArea.append("File not found. " + e.toString());
    			return;
    		}
    	case JFileChooser.ERROR_OPTION:
    		feedbackArea.append("Error with load transcript dialog.");
    		break;
    	default:
    		feedbackArea.append("Error loading transcript.");
    		break;
    	}
    }  
  }
  
	public class RecommendCoursesButtonHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ae) {
			transcriptArea.append("\n\n" + currentTranscript.recommendCourses());
		}
	}

  public static void main(String[] args)
  {
    try {
      // Read in courses.
      Course.loadCourses(COURSES_FILE);
    } catch (FileNotFoundException e) {
      System.err.println("Unable to load required file " + COURSES_FILE);
      return;
    }
    GUI gui = new GUI();
    gui.setVisibility(true);
  }

}
