package org.example;


import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        if (args.length != 2) {
            System.out.println(
                    "Application needs two arguments to run: " +
                            "java com.hca.jdbc.UsingDriverManager <username> " +
                            "<password>");
            System.exit(1);
        }
        // get the user name and password from the command line args
        String username = args[0];
        String password = args[1];

        // load the driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // create the connection and prepared statement in a
        // try-with-resources block
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/northwind",
                username, password);
             PreparedStatement preparedStatement =
                     connection.prepareStatement(
                             "SELECT CategoryID, CategoryName FROM categories ORDER BY CategoryID");
        ) {
            // a try-with-resources block
            try (ResultSet resultSet =
                         preparedStatement.executeQuery();
            ) {
                // loop thru the results
                while (resultSet.next()) {
                    // process the data
                    System.out.printf(
                            "CategoryID = %d, CategoryName = %s;\n",
                            resultSet.getInt(1), resultSet.getString(2));
                }
            }

            Scanner myObj = new Scanner(System.in);
            System.out.println("Enter type of product do you want to see?");
            // String input
            int CategoryID = myObj.nextInt();
            System.out.println("type of product: " + CategoryID);
            // both of them in 1 try question mark
            {
                try PreparedStatement preparedStatement2 =
                    connection.prepareStatement(
                            "SELECT * FROM products" +
                                    " WHERE CategoryID LIKE ? ORDER BY ProductName");
                ) {
                preparedStatement.setString(1, "CategoryID");

                // a try-with-resources block
                    try (ResultSet resultSet =
                                 preparedStatement.executeQuery();
                    ) {
                        // loop thru the results
                        while (resultSet.next()) {
                            // process the data
                            System.out.printf(
                                    "ProductID = %d, ProductName = %s, UnitPrice = %f, UnitsInStock = %d;\n",
                                    resultSet.getInt(1), resultSet.getString(2), resultSet.getFloat(3), resultSet.getInt(4));
                        }
                    }
            }
        } catch (SQLException e) {
            // This will catch all SQLExceptions occurring in the
            // block including those in nested try statements
            e.printStackTrace();
        }

    }
}
}



