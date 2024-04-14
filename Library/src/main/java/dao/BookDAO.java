package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dBHelpers.MyDbConnection;
import models.Book;

public class BookDAO {
	public static Boolean addBook(Book book) throws SQLException {
		System.out.println("Reached addBook method in BookDAO");
		try(Connection connection = MyDbConnection.getConnection()){
			String query = "INSERT INTO books (title, author, pages) values (?, ?, ?)";

			try {
				PreparedStatement ps = connection.prepareStatement(query);

				ps.setString(1, book.getTitle());
				ps.setString(2, book.getAuthor());
				ps.setInt(3, book.getPages());

				ps.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
	}
	public static List<Book> getBooks() throws SQLException {
		try(Connection connection = MyDbConnection.getConnection()){
			String query = "SELECT bookID, title, author, pages FROM books";
			ResultSet booksResult;
			List<Book> books = new ArrayList<>();
			try {
				PreparedStatement ps = connection.prepareStatement(query);
				booksResult = ps.executeQuery();

			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			
			while(booksResult.next()) {
				int id = booksResult.getInt("bookID");
				String title = booksResult.getString("title");
				String author = booksResult.getString("author");
				int page = booksResult.getInt("pages");
				books.add(new Book(id, title, author, page));
			}
			return books;
		}
	}
}
