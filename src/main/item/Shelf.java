package main.item;

public abstract class Shelf {

    public abstract boolean addData(String title, String author);

    public abstract boolean deleteData(String id);

    public abstract boolean deleteAllData();

    public abstract Production[] getData(int count);

    public abstract int countData();
}
