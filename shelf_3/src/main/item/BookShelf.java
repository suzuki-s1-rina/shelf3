package main.item;

import java.util.List;

import main.mysql.ConnectMySQL;

public class BookShelf extends Shelf {

	public static final int MAX_BOOKS_SIZE = 5;

	public BookShelf() {
		maxCount = MAX_BOOKS_SIZE;
		products = new Production[maxCount];
	}

	public BookShelf(int size) {
		maxCount = size;
		products = new Production[maxCount];
	}

	public boolean add(Production production) {
		if (production instanceof Book) {
			products[count] = production;
			return true;
		}
		return false;
	}

    public boolean addData(String title, String author) {
        return ConnectMySQL.insertData("INSERT INTO book (title, author) VALUES ('"+ title +"','"+ author +"');");
    }

    public boolean deleteData(String id) {
    	return ConnectMySQL.deleteData("DELETE FROM book WHERE id=" + id +";");
    }

    public boolean deleteAllData() {
    	return ConnectMySQL.deleteAllData("TRUNCATE book");
    }

    public List<Production> getData() {
    	return ConnectMySQL.getBookData("SELECT * FROM book;");
    }

    public int countData() {
    	return ConnectMySQL.countData("SELECT COUNT(*) AS CNT FROM book;");
    }

    public List<Production> searchData(String searchColumn, String searchText) {
    	return ConnectMySQL.getBookData("SELECT * FROM book WHERE " + searchColumn + "LIKE '%" + searchText + "%';");
    }

    public List<Production> searchAllMachData(String searchColumn, String searchText) {
    		return ConnectMySQL.getBookData("SELECT * FROM book WHERE " + searchColumn + "=" + searchText + ";");
    }

}
