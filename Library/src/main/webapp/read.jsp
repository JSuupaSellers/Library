<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Book" %>
<html>
<head>
    <title>Book List</title>
</head>
<body>
<h2>Sci Fi Library</h2>
<table border="1">
    <tr>
        <th>Title</th>
        <th>Author</th>
        <th>Pages</th>
    </tr>
    <% 
        List<Book> books = (List<Book>) request.getAttribute("books");
        if (books != null) {
            for (Book book : books) {
                out.println("<tr>");
                out.println("<td>" + book.getTitle() + "</td>");
                out.println("<td>" + book.getAuthor() + "</td>");
                out.println("<td>" + book.getPages() + "</td>");
                out.println("</tr>");
            }
        }
    %>
    
</table>
<a href="${pageContext.request.contextPath}/addForm.html">Add a new book</a>
</body>
</html>