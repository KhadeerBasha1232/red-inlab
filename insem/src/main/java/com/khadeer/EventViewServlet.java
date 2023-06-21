package com.khadeer;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EventViewServlet extends GenericServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String DB_URL = "jdbc:mysql://localhost:3306/khadeer";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "khadeer12";

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        try {
            // Connect to the database
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Execute a query to fetch event records
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM EVENT");

            // Prepare the response writer
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            // Generate the table view
            out.println("<html><head><title>Event Records</title></head><body>");
            out.println("<table><tr><th>ID</th><th>Event Name</th><th>Price</th><th>Place</th><th>Date</th><th>No. of Participants</th></tr>");
            
            // Iterate through the result set and display each record in a table row
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String eventName = resultSet.getString("event_name");
                double price = resultSet.getDouble("price");
                String place = resultSet.getString("place");
                String eventDate = resultSet.getString("event_date");
                int numParticipants = resultSet.getInt("num_participants");
                
                out.println("<tr><td>" + id + "</td><td>" + eventName + "</td><td>" + price + "</td><td>" + place + "</td><td>" + eventDate + "</td><td>" + numParticipants + "</td></tr>");
            }
            
            out.println("</table></body></html>");

            // Close the database connection
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Unable to access the database.", e);
        }
    }
}
