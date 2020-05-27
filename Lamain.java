/**
 * 
 */
package main;

import javax.security.auth.login.CredentialException;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

/**
 * Main
 * <p>
 * ....................../��/) <br>
 * ....................,/�../ <br>
 * .................../..../ <br>
 * ............./��/'...'/���`�� <br>
 * ........../'/.../..../......./��\ <br>
 * ........('(...�...�.... �~/'...') <br>
 * .........\.................'...../ <br>
 * ..........''...\.......... _.�� <br>
 * ............\..............( <br>
 * ..............\.............\...<br>
 * </p>
 * @author Naryan Aggarwal
 * @author Chris Slojkowski
 * @since 04/25/2020
 * @version 1.0
 */
public class Lamain {
	
	public static Connection myConn;
	public static int projectID = 1;
	public static List<Notification> notifs = new ArrayList<Notification>();
	public static int notifID = 1;
	
	
	public static void SQLGrabber(User user) throws SQLException {
		//SQLWriter.select(conn, happy, ProjectId)
		
	}

	
	
	/* AVAILABLE TABLES IN LOCAL DATABASE
	 * tblComments
	 * tblIssues
	 * tblNotification
	 * tblProjects
	 * tblRoles
	 * tblSprint
	 * tblTaskBoards
	 * tblUserData
	 * tbl.UserPasswords
	 */
	
	public static int getID() {
		return projectID++;
	}

	private static HashSet<User> userSet = new HashSet<User>(); //Another fucking HashSet

	public static void addUser(User user) {userSet.add(user);}

	private static User currentUser;

	/**
	 *
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		//	Testing
		//	Stuff
		//----------------------------------------------------
		User pilot = new User("PILOT", "1234");

		Project pizza = new Project("Pizza Time", "Peter parker delivers pizza on time", getID());
		pilot.addUserDataProject(pizza);
		//pizza.addUser(pilot);

		//pizza.getBoards()[0].addIssue(problem);
		//pizza.getBoards()[2].addIssue(problem3);
		//pizza.getBoards()[1].addIssue(problem2);

		Issue problem4 = new Issue(pilot.getUserData(pizza), 5, "Happ yhappy shdj", pizza);
		Issue problem5 = new Issue(pilot.getUserData(pizza), 5, "yhappy shdj Happ", pizza);
		Issue problem6 = new Issue(pilot.getUserData(pizza), 5, "yhappy Happ shdj", pizza);
		Issue problem7 = new Issue(pilot.getUserData(pizza), 5, "shdj Happ yhappy", pizza);
		Issue problem8 = new Issue(pilot.getUserData(pizza), 5, "Happ happy shdjo asdjklas sajdkiajld skjdlaskdjlsk dkjsldkja djlsakjdlsk dkjlaks kdjljjslaj", pizza);

		pizza.getBoards()[2].addIssue(problem4);
		pizza.getBoards()[2].addIssue(problem5);
		pizza.getBoards()[2].addIssue(problem6);

		//pizza.getBoards()[1].addSprint(new Sprint(pizza.getBoards()[1], new Date()));

		pizza.getBoards()[1].addIssue(problem7);
		pizza.getBoards()[2].addIssue(problem8);

		
		String dbUrl = "jdbc:ucanaccess://c:/Users/talin/Documents/GitHub/c212-Spring-2020/local.accdb";
		String user = "student";
		String pass = "student";
		
		try {
			myConn = DriverManager.getConnection(dbUrl, user, pass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		Welcome();
	}

	public static void Welcome() throws SQLException
	{
		System.out.println("Welcome to the Issue Tracker 5000, where all your problems come to life.");
		System.out.println("By Naryan Aggarwal and Chris Slojkowski.");
		System.out.println();

		System.out.println("1. Login");
		System.out.println("2. Sign up");

		switch (G.nextInt())
		{
			case 1:
			{
				LogIn();
				break;
			}
			case 2:
			{
				CreateAccount();
				break;
			}
			default:
			{
				System.out.println("Swedish Geese be like: Hj�nk");
				Welcome();
				break;
			}
		}
	}

	public static void CreateAccount() throws SQLException
	{
		System.out.println("Create a username (max 20 characters):");
		String username = G.nextLine();
		User you = new User(username, MakeAPassword());
		userSet.add(you);
		currentUser = you;
		SQLWriter.insertInto("tblUserPasswords", "Username, password", myConn, currentUser.getUsername(), currentUser.getPassword());
		MeatNPotatoes();
	}

	public static String MakeAPassword() //This literally only exists so I can have a recursive boi
	{
		System.out.println("Create a password (min 6 characters):");
		String password1 = G.nextLine();
		System.out.println("Repeat password");
		String password2 = G.nextLine();
		if (!password1.equals(password2))
		{
			//Try again, fucko
			System.out.println("Wrong passwords");
			return MakeAPassword();
		}
		else
		{
			return password1;
		}
	}

	public static void LogIn() throws SQLException
	{
		System.out.print("Enter username: ");
		String username = G.nextLine();
		System.out.print("Enter password: ");
		String password = G.nextLine();

		User you = GetUserByName(username);
		if (you == null)
		{
			System.out.println("If you're reading this, you've been in a coma for almost 20 years now. We're trying a new technique. \n"
					+ "We don't know where this message will end up in your dream, but we hope it works. \n"
					+ "Please wake up, we miss you.");
			System.out.println();
			LogIn();
		}
		else if (G.getCRC32(password).equals(you.getPassword()))
		{
			System.out.println("Logged in!");
			currentUser = you;
			MeatNPotatoes();
		}
		else
		{
			System.out.println("Wrong password");
			LogIn();
		}
	}

	public static void MeatNPotatoes() throws SQLException
	{
		System.out.println("Issue tracker 5000");

		System.out.println("1. Notifications");
		System.out.println("2. My Projects");
		System.out.println("3. Credits");
		System.out.println("4. Logout");

		switch(G.nextInt())
		{
			case 1:
			{
				Notifications();
				break;
			}
			case 2:
			{
				MyProjects();
				break;
			}
			case 3:
			{
				Credits();
				MeatNPotatoes();
			}
			case 4:
			{
				System.out.println("Goodbye");
				Welcome();
			}
			default:
			{
				
			}
		}
	}

	public static void Notifications()
	{
		//System.out.println("I'll finish the notifications later, MOM.");
		if(notifs.size() < 1) {
			System.out.println("You have no notifications");
			try {
				MeatNPotatoes();
			} catch (SQLException e) {
				// Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.printf("%3s|%04s|%40s", "Project ID", "Issue ID", "Description");
		for(Notification n : notifs) {
			System.out.printf("%3o|%04o|%40s", n.getProjectID(), n.getIssueID(), n.getAction());
		}
		
		try {
			SQLWriter.exterminate("tblNotification", myConn, "");
		} catch (SQLException e1) {
			System.out.println("Notifications could not be cleared. Please check database.");
			System.out.println("It is not recommended to use this copy of Issue tracker 5000.");
		}
		
		System.out.println("Enter \"back\" to back.");
		if (G.next().equalsIgnoreCase("back")) {
			try {
				MeatNPotatoes();
			} catch (SQLException e) {
				System.out.println("God is Dead");
				e.printStackTrace();
			}
		}
	}

	public static void MyProjects() throws SQLException
	{
		ArrayList<Project> projects = new ArrayList<Project>();
//		ResultSet availableProjects = SQLWriter.select(myConn, "tblUserData", "Username=" + currentUser.getUsername(),"ProjectID", "Role");
//		Set<Integer> projectIDs = new HashSet<Integer>();
//		while(availableProjects.next()) {
//			projectIDs.add(Integer.parseInt(availableProjects.getString(1)));
//		}
//		ResultSet projectRS = SQLWriter.select(myConn, "tblProjects","ProjectID", "WorkingIssueID", "ProjectName", "ProjectDescription");
//		
//		while(projectRS.next()) {
//			if(projectIDs.contains(Integer.parseInt(projectRS.getString(1)))) {
//				PreparedStatement stmt = myConn.prepareStatement("SELECT tblProjects.ProjectID, tblUserData.UserData\r\n" + 
//						"FROM tblProjects INNER JOIN tblUserData ON tblProjects.ProjectID = tblUserData.ProjectID\r\n" + 
//						"GROUP BY tblProjects.ProjectID, tblUserData.UserData\r\n" + 
//						"HAVING (((tblProjects.ProjectID)="
//						+ "\""
//						+ projectRS.getString("ProjectID")
//						+ "\"));");
//				ResultSet associatedUsers = stmt.executeQuery(); // Grabs the UserData associated with the ProjectIDs
//				Set<UserData> users = new HashSet<UserData>();
//				while(associatedUsers.next()) {
//					users.add(associatedUsers)
//				}
//				projects.add(new Project(projectRS.getString(3), projectRS.getString(4), users, projectRS.getString(1)));
//			}
//		}
//		
		if (currentUser.getProjects().isEmpty())
		{
			CreateAProject();
		}
		else {
			projects.addAll(currentUser.getProjects());
			for (int i = 0; i < projects.size(); i++) {
				Project project = projects.get(i);
				System.out.println(i + 1 + ". " + project.getName() + " | " + currentUser.getUserData(project).getRole() + " | " + project.getDescription());
			}
			int next = G.nextInt();

			if (next == 0)
			{
				MeatNPotatoes();
				return;
			}
			Project current = projects.get(next - 1);
			ViewCurrentProject(current);
		}
	}

	public static void Credits()
	{
		System.out.println("Application developed by Naryan Aggarwal and Chris Slojkowski.");
		System.out.println("Graphical interface developed by Chris Slojkowski");
		System.out.println("Database and SQLWriter Framework by Naryan Aggarwal");
		System.out.println("Licensing by Naryan Aggarwal using MIT license.");
	}

	public static void CreateAProject() throws SQLException
	{
		System.out.println("Oopsy Doopsy, " + currentUser.getUsername() + ". It appears there are no Projects. Create one or perish");
		System.out.print("Project Name: ");
		String name = G.nextLine();
		System.out.println("Project Description: ");
		String descripion = G.nextLine();

		int newId = getID();
		Project pj = new Project(name, descripion, newId);
		currentUser.addUserDataProject(pj);
		SQLWriter.insertInto("tblProjects", "ProjectID, WorkingIssueID, ProjectName, ProjectDescription", myConn, ""+newId, ""+pj.workingIssueID, pj.getName(), pj.getDescription());
		notifs.add(addNotif(newId, 0, "Project Created: " + name));
		MeatNPotatoes();
	}

	public static void ViewCurrentProject(Project p)
	{
		System.out.println(p.toString());
		
		System.out.println("1. Issues");

		switch (G.nextInt())
		{
			case 1: {
				Issues(p);
				break;
			}
		}
	}

	public static void Issues(Project p)
	{
		System.out.println("1. Create an issue");

		switch (G.nextInt())
		{
			case 1:
			{
				CreateIssue(p);
			}
		}
	}

	public static void CreateIssue(Project p)
	{
		System.out.println("Issue name: ");
		String name = G.nextLine();
		System.out.println("Issue description: ");
		String description = G.nextLine();
		System.out.println("Which Taskboard?");
		System.out.println("1. To Do");
		System.out.println("2. In Progress");
		System.out.println("3. Work Done");
		int board = G.nextInt();
		System.out.println("What type? (6 characters)");
		String type = G.next();


//		new Issue(type, null, );
	}

	public static User GetUserByName(String username) throws SQLException
	{
		ResultSet rs = SQLWriter.select(myConn, "tblUserPasswords", "Username", "password");
		while(rs.next()) {
			userSet.add(new User(rs.getString(1), rs.getString(2), false));
		}
		
		for (User user: userSet)
		{
			if (user.getUsername().equalsIgnoreCase(username))
			{
				return user;
			}
		}
		return null;
	}

	public static Notification addNotif(int ProjectId, int IssueID, String action) {
		Notification newn = new Notification(ProjectId, IssueID, action);
		try {
			SQLWriter.insertInto("tblNotification", "NotificationID, ProjectID, IssueID, Action", myConn, ""+notifID++, ""+ProjectId, ""+IssueID, action);
		} catch (SQLException e) {
			System.out.println("Notification added.");
		}
		return newn;
	}
}
