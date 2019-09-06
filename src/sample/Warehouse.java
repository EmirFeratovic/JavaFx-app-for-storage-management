package sample;

public class Warehouse {
    private int id;
    private String location;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Warehouse(int id, String location, String name) {
        this.id = id;
        this.location = location;
        this.name = name;
    }
     public Warehouse() {
    }
}
