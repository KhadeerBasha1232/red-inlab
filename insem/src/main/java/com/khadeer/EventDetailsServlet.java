package com.khadeer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EventDetailsServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String DB_URL = "jdbc:mysql://localhost:3306/khadeer";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "khadeer12";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String eventDate = request.getParameter("date");

        try {
            // Connect to the database
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Execute a parameterized query to fetch event details based on the date
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM EVENT WHERE event_date = ?");
            statement.setString(1, eventDate);
            ResultSet resultSet = statement.executeQuery();

            // Prepare the response writer
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            // Generate the event details view
            out.println("<html><head><title>Event Details</title></head><body>");

            if (resultSet.next()) {
                // Display the event details
                out.println("<h1>Event Details</h1>");
                out.println("<p><b>Event Name:</b> " + resultSet.getString("event_name") + "</p>");
                out.println("<p><b>Price:</b> " + resultSet.getDouble("price") + "</p>");
                out.println("<p><b>Place:</b> " + resultSet.getString("place") + "</p>");
                out.println("<p><b>Date:</b> " + resultSet.getString("event_date") + "</p>");
                out.println("<p><b>No. of Participants:</b> " + resultSet.getInt("num_participants") + "</p>");
            } else {
                // Event not found for the specified date
                out.println("<h1>No event found for the specified date.</h1>");
            }

            out.println("</body></html>");

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
