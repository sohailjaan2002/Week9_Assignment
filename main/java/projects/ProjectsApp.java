package projects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectService;

public class ProjectsApp {
	private Scanner scanner = new Scanner(System.in);
	// @formatter:off
	private List<String> operations = List.of( 
			"1) Add a project"
	);
	// @formatter:on
	
	
	public static void main(String[] args) {
	
	new ProjectsApp().processUserSelections(); 
	}

	
	
	private void processUserSelections() {
		boolean done = false;
		
		while (!done) {
			
		try {
			int selection = getUserSelection();
			switch(selection) {
			case -1:
				done = exitMenu();
				break;
			case 1:
				createProject();
				break; 
				default: 
					System.out.println("\n " + selection + " is not a valid selection. Try again.");
			}
			
		}
		catch(Exception e) {
			System.out.println("\n Error: " + e + " Try again.");
		}
	  }
		
	}

	private void createProject() {
		String projectName = getStringInput("Enter the Project Name ");
		BigDecimal estimatedHours = getDecimalInput("Enter the estimated Hours ");
		BigDecimal actualHours = getDecimalInput("Enter the Actual Hours ");
		Integer difficulty = getIntInput("Enter the Project Difficulty (1-5) " );
		String notes = getStringInput("Enter the Project notes ");
		
		Project project = new Project();
		
		project.setProjectName(projectName);
		project.setEstimatedHours(estimatedHours);
		project.setActualHours(actualHours);
		project.setDifficulty(difficulty);
		project.setNotes(notes);
		
		Project dbProject = ProjectService.addProject(project);
		System.out.println("You have successfully created project: " + dbProject);
	}



	private BigDecimal getDecimalInput(String prompt) {
		String input = getStringInput(prompt);
		
		
		if (Objects.isNull(input)) {
			return null;
		}
		
		try {
			return new BigDecimal(input).setScale(2);
		}
		catch (NumberFormatException e) {
			throw new DbException(input + "is not a vaild decimal number. ");
		}
	}



	private boolean exitMenu() {
		System.out.println("\nExiting the Menu");
		return true;
	}



	private int getUserSelection() {
		printOperations();
		
		Integer input = getIntInput("Enter a menu selection");
		
		return Objects.isNull(input) ? -1 : input; 
	}

	private Integer getIntInput(String prompt) {
		String input = getStringInput(prompt);
		
		if(Objects.isNull(input)) {
			return null;
		}
		 try {
			 return Integer.parseInt(input);
		 }
		 catch (NumberFormatException e) { 
			 throw new DbException(input + " is not a valid number. ");
		 }
		
	}

	private String getStringInput(String prompt) {
		System.out.print(prompt + ": ");
		String input = scanner.nextLine();
		
		return input.isBlank() ? null : input.trim();
	}

	private void printOperations() {
		System.out.println();
		System.out.println("\n These are the available seletions. Press the Enter key to quit:");
		
		operations.forEach(input -> System.out.println("  " + input));
		}
	
}