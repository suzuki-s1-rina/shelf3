package main.item;

import java.util.List;

public abstract class Shelf {
	protected int maxCount;
	protected Production[] products;
	protected int count;




    public int getMaxCount() {
    	return maxCount;
    }

    public abstract boolean addData(String title, String author);

    public abstract boolean deleteData(String id);

    public abstract boolean deleteAllData();

    public abstract List<Production> getData();

    public abstract int countData();
}
