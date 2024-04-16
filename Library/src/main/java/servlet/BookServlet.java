package servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dBHelpers.BookDbHelper;
import dao.BookDAO;
import models.Book;

@WebServlet(name = "BookServlet", urlPatterns = "/books/*")
public class BookServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // Get the html table from the dbHelper object
	    List<Book> books;
		
	    String pathInfo = request.getPathInfo();
	    
	    if(pathInfo.equals("/read")) {
		   try {
				books = BookDAO.getBooks();
				// pass execution control to read.jsp along with the table
			    request.setAttribute("books", books);
			    String url = "/read.jsp";
			    RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			    dispatcher.forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   
	    } else if(pathInfo.equals("/booksByAuthor")) {
	    	try {
	    		String author = request.getParameter("author");
	    		books = BookDAO.getBooksByAuthor(author);
	    		request.setAttribute("books", books);
	    		String url = "/read.jsp";
	    		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	    		dispatcher.forward(request, response);
	    	}catch (SQLException e) {
	    		e.printStackTrace();
	    	}
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Reached doPost method in BookServlet");
		// get the data
		// TODO: Add error checking!
	    String title = request.getParameter("title");
	    String author = request.getParameter("author");
	    int pages = Integer.parseInt(request.getParameter("pages"));
	
		// set up a book object
	    Book book = new Book();
	    book.setTitle(title);
	    book.setAuthor(author);
	    book.setPages(pages);
	        
		// pass the book to addQuery to add to the database
	    boolean success = false;
	    try {
	        success = BookDAO.addBook(book);
	    } catch (SQLException e) {
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        response.getWriter().println("Error adding book to the database");
	        // Log the exception or display an error message to the user
	        e.printStackTrace();
	        return;
	    }

	    if (success) {
	        String url = "read";
	        response.sendRedirect(url);
	    } else {
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        response.getWriter().println("Failed to add book to the database");
	    }
	    
	}
//	@Override
//	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	    // Get the book ID from the request parameter
//	    int bookID = Integer.parseInt(request.getParameter("bookID"));
//
//	    if (bookID != 0) {
//	        // Create a dbHelper object
//	        BookDbHelper bdb = new BookDbHelper();
//
//	        // Delete the book with the specified ID from the database
//	        boolean success = bdb.doDelete(bookID);
//
//	        if (success) {
//	            // Book deleted successfully
//	            response.setStatus(HttpServletResponse.SC_OK);
//	            response.getWriter().println("Book deleted successfully");
//	        } else {
//	            // Failed to delete the book
//	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//	            response.getWriter().println("Failed to delete the book");
//	        }
//	    } else {
//	        // Book ID is missing in the request
//	        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//	        response.getWriter().println("Book ID is missing");
//	    }
//	}
//	@Override
//	protected void doPut(HttpServletRequest request, HttpServletResponse response)
//	        throws ServletException, IOException {
//	    // Get the book details from the request parameters
//	    int bookID = Integer.parseInt(request.getParameter("bookID"));
//	    String title = request.getParameter("title");
//	    String author = request.getParameter("author");
//	    String pagesStr = request.getParameter("pages");
//
//	    // Validate the input parameters
//	    if (title == null || author == null || pagesStr == null) {
//	        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//	        response.getWriter().println("Invalid request parameters");
//	        return;
//	    }
//
//	    // Parse the pages parameter to an integer
//	    int pages;
//	    try {
//	        pages = Integer.parseInt(pagesStr);
//	    } catch (NumberFormatException e) {
//	        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//	        response.getWriter().println("Invalid pages value");
//	        return;
//	    }
//
//	    // Create a Book object with the updated details
//	    Book book = new Book(bookID, title, author, pages);
//
//	    // Create a dbHelper object
//	    BookDbHelper bdb = new BookDbHelper();
//
//	    // Update the book in the database
//	    boolean success = bdb.doUpdate(book);
//
//	    if (success) {
//	        // Book updated successfully
//	        response.setStatus(HttpServletResponse.SC_OK);
//	        response.getWriter().println("Book updated successfully");
//	    } else {
//	        // Failed to update the book
//	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//	        response.getWriter().println("Failed to update the book");
//	    }
//	}
}
