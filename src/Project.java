import java.sql.*;
import java.util.Scanner;

public class Project {
    static  int num = 10;
    static Scanner sc = new Scanner(System.in);
    static Statement statement;

    static void createTable(Connection con) throws SQLException {
        statement = con.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS data(Name Text NOT NULL, Email Text, Age int, location Text, Designation Text)");
        System.out.println("Database created");
    }

    public static int populateTable() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_one", "wisdom", "@Emma513131");) {
            createTable(con);

            String Name, Email, Location, Designation, answer;
            int Age, count;
            String name, email, location, designation;
            int age;
            count = 0;
            PreparedStatement insertStatement = con.prepareStatement("INSERT INTO data(Name,Email,Age,Location,Designation)VALUES(?,?,?,?,?)");

            do {
                System.out.println("please enter your name ");
                Name = sc.nextLine();
                System.out.println("Please enter your Email");
                Email = sc.nextLine();
                System.out.println("Please enter your age");
                Age = sc.nextInt();
                sc.nextLine();
                System.out.println("please enter your location");
                Location = sc.nextLine();
                System.out.println("please enter your Designation");
                Designation = sc.nextLine();

                insertStatement.setString(1, Name);
                insertStatement.setString(2, Email);
                insertStatement.setInt(3, Age);
                insertStatement.setString(4, Location);
                insertStatement.setString(5, Designation);
                insertStatement.execute();

                System.out.println("Do you want to insert more data (yes/no)?");
                answer = sc.nextLine();

                count++;
            } while (count < num && answer.equalsIgnoreCase("yes"));

            String selectAll = "SELECT * FROM data";

            System.out.println("+-------------------------------------------------------------------------+");
            System.out.println("| Name                 | Email      |Age | Location     | Designation     |");
            System.out.println("+-------------------------------------------------------------------------+");

            try (ResultSet resultSet = statement.executeQuery(selectAll)) {
                if (resultSet.next()) {
                    name = resultSet.getString(1);
                    email = resultSet.getString(2);
                    age = resultSet.getInt(3);
                    location = resultSet.getString(4);
                    designation = resultSet.getString(5);

                    System.out.println("+-------------------------------------------------------------------------+");
                    System.out.printf("| %-20s | %-10s | %-1d | %-12s | %-15s |\n", name, email, age, location, designation);
                    System.out.println("+-------------------------------------------------------------------------+");
                }
            }

            System.out.println("Total records: " + count);
            return count;
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return 0;
    }

    public static void main(String[] args) {
        populateTable();
    }
    }