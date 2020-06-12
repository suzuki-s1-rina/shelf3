package main.item;

import main.mysql.ConnectMySQL;

public class BookShelf extends Shelf {

	public BookShelf() {

	}

	@Override
	public boolean addData(String title, String author) {
		return ConnectMySQL.insertData("INSERT INTO book (title, author) VALUES ('" + title + "','" + author + "');");
	}

	@Override
	public boolean deleteData(String id) {
		return ConnectMySQL.deleteData("DELETE FROM book WHERE id = ?;", id);
	}

	@Override
	public boolean deleteAllData() {
		return ConnectMySQL.deleteAllData("TRUNCATE book");
	}

	@Override
	public Production[] getData(int count) {
		return ConnectMySQL.getBookData("SELECT * FROM book;", count);
	}

	@Override
	public int countData() {
		return ConnectMySQL.countData("SELECT COUNT(*) AS CNT FROM book;");
	}

    @Override
    public String getText() {
    	return "本";
    }
}
