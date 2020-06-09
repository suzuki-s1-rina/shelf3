package main.item;

import java.util.List;

import main.mysql.ConnectMySQL;

public class CdShelf extends Shelf {

    public static final int MAX_CDS_SIZE = 5;

    public CdShelf() {
    	maxCount =  MAX_CDS_SIZE;
    	products = new Production[maxCount];

    }

    public CdShelf(int size) {
    	maxCount = size;
    	products = new Production[maxCount];

    }

    public boolean add(Production production) {
    	if(production instanceof Cd) {
        	products[count] = production;
            return true;
    	}
        return false;
    }

    public boolean addData(String title, String author) {
        return ConnectMySQL.insertData("INSERT INTO cd (song, singer) VALUES ('"+ title +"','"+ author +"');");
    }

    public boolean deleteData(String id) {
    	return ConnectMySQL.deleteData("DELETE FROM cd WHERE id=" + id +";");
    }

    public boolean deleteAllData() {
    	return ConnectMySQL.deleteAllData("TRUNCATE cd");
    }

    //まだ
    public List<Production> getData() {
    	return ConnectMySQL.getCdData("SELECT * FROM cd;");
    }

    public int countData() {
    	return ConnectMySQL.countData("SELECT COUNT(*) AS CNT FROM cd;");
    }
}