package dBHelpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.Book;

/**
 * This refactor version moves the queries from their own objects
 * and organizes them as methods by model. 
 */

public class BookDbHelper {
	private Connection connection;

	public BookDbHelper() {
			try {
				connection = MyDbConnection.getConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	
	/**
	 * doReadAll() is a refactor of the ReadQuery object's doRead() method
	 * in the previous version.
	 * 
	 * In this version, doReadAll() returns a result set rather than 
	 * storing it as a field of this helper object. The {@link #getHTMLTable()}
	 * helper is modified to accept the result set instead.
	 *  
	 * @return ResultSet
	 */
	public ResultSet doReadAll(){
		// Other methods will expect specific fields from this result set.
		// This is one reason why it's always good practice to SELECT specific fields
		// rather than using the wild card, SELECT *

		// String query = "SELECT * FROM books"; // <-- Not as good
		String query = "SELECT bookID, title, author, pages FROM books"; // <-- Better

		ResultSet results = null;
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			results = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return results;
	}

	/**
	 * This version was refactored to accept a result set, rather than rely
	 * on a result set existing as a field of the instance.
	 * 
	 * This object could be further refactored to run without a result set. 
	 * One path  might be to create an overloaded version that takes no parameters.
	 * 
	 * @param results
	 * @return String
	 */
//	public String getHTMLTable(ResultSet results){
//		String table ="";
//		table += "<table border=1>\n";
//
//		try {
//			while(results.next()) {
//
//				// Consider: Why are we creating Book objects with these results, rather 
//				// than just printing the results of the query directly?
//				// (It helps us validate our data.)
//
//
//				// Consider: Could this table row code be refactored to be part of Book?
//				// Would that be a good idea or not?
//
//				table +="<tr>";
//				table +="\t<td>";
//				table += book.getTitle();
//				table +="</td>";
//				table +="<td>";
//				table += book.getAuthor();
//				table +="</td>";
//				table +="<td>";
//				table += book.getPages();
//				table +="</td>";
//				table +="\n\t<td>";
//				
//				// We made changes to the Delete servlet, so that it can't be accessed via 'GET'
//				// Thus, this HTML needs to change as well. 
//				// We'll create a small form that POSTs instead.
//				
//				table += "<form action=\"update\" method=\"post\">";
//				table += "<input type=\"hidden\" name=\"bookID\" value=\"" + book.getBookID() + "\">";
//				table += "<input type=\"submit\" value=\"Update\">";
//				table += "</form>";
//				
//				table += "<form action=\"delete\" method=\"post\">";
//				table += "<input type=\"hidden\" name=\"bookID\" value=\"" + book.getBookID() + "\">";
//				table += "<input type=\"submit\" value=\"Delete\">";
//				table += "</form>";
//				
//				// Consider adding behavior that might make this more user friendly:
//				// a) adding an "Are you sure?" Javascript popup.
//				// b) adding a success message to the reloaded page.
//				
//				table +="</td>\n";
//				
//				table +="</tr>\n";
//
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		table += "</table>";
//		return table;
//	}

	/**
	 * doReadOne() is a refactor of the ReadRecord object's doRead() method
	 * in the previous version.
	 * 
	 * In this version, doReadOne() accepts an integer rather than 
	 * storing it the id as a field of this helper object. It also returns a Book
	 * object, rather than storing it as a field.
	 * 
	 * One consequence of this change is that the calling code is now responsible 
	 * for keeping track of the resulting Book reference, rather than 
	 * the helper object.
	 * 
	 * @param int
	 * @return Book
	 **/
	public Book doReadOne(int bookId) {
		String query = "SELECT * FROM books WHERE bookID = ?";

		Book book = null;

		try {
			PreparedStatement ps = connection.prepareStatement(query);

			ps.setInt(1, bookId);
			ResultSet results = ps.executeQuery();

			results.next();

			// What if book isn't found? Is an exception thrown?
			// Is it okay to return null from this refactored method?

			book = new Book(
					results.getInt("bookID"),
					results.getString("title"),
					results.getString("author"),
					results.getInt("pages")
					);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return book;
	}
}
