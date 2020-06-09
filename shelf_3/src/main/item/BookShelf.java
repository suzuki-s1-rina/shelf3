package main.item;

import main.mysql.ConnectMySQL;

public class BookShelf extends Shelf {

	public BookShelf() {

	}

	public boolean addData(String title, String author) {
		return ConnectMySQL.insertData("INSERT INTO book (title, author) VALUES ('" + title + "','" + author + "');");
	}

	public boolean deleteData(String id) {
		return ConnectMySQL.deleteData("DELETE FROM book WHERE id=" + id + ";");
	}

	public boolean deleteAllData() {
		return ConnectMySQL.deleteAllData("TRUNCATE book");
	}

	public Production[] getData(int count) {
		return ConnectMySQL.getBookData("SELECT * FROM book;", count);
	}

	public int countData() {
		return ConnectMySQL.countData("SELECT COUNT(*) AS CNT FROM book;");
	}

}
