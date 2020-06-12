package main.item;

import main.mysql.ConnectMySQL;

public class CdShelf extends Shelf {

	public CdShelf() {

	}

	@Override
    public boolean addData(String title, String author) {
        return ConnectMySQL.insertData("INSERT INTO cd (song, singer) VALUES ('"+ title +"','"+ author +"');");
    }

	@Override
    public boolean deleteData(String id) {
    	return ConnectMySQL.deleteData("DELETE FROM cd WHERE id = ?;", id);
    }

	@Override
    public boolean deleteAllData() {
    	return ConnectMySQL.deleteAllData("TRUNCATE cd");
    }

	@Override
	public Production[] getData(int count) {
		return ConnectMySQL.getCdData("SELECT * FROM cd;", count);
	}

	@Override
    public int countData() {
    	return ConnectMySQL.countData("SELECT COUNT(*) AS CNT FROM cd;");
    }

    @Override
    public String getText() {
    	return "CD";
    }
}