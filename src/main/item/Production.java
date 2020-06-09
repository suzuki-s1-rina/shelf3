package main.item;

public abstract class Production {

    protected String title;
    protected String person;
    protected String id;

    public abstract boolean setTitle(String title);

    public String getTitle() {
        return this.title;
    }

    public abstract boolean setPerson(String person);

    public String getPerson() {
        return this.person;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
