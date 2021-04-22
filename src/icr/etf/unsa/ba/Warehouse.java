package icr.etf.unsa.ba;

import javafx.beans.property.SimpleStringProperty;

public class Warehouse {
    private StorageDAOdb dao = StorageDAOdb.getInstance();
    private int id;
    private SimpleStringProperty location = new SimpleStringProperty("");
    private SimpleStringProperty name = new SimpleStringProperty("");

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location.get();
    }

    public SimpleStringProperty locationProperty() {
        return location;
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public Warehouse(int id, String location, String name) {
        this.id = id;
        this.location =  new SimpleStringProperty(location);
        this.name = new SimpleStringProperty(name);
    }
     public Warehouse() {
        this.id = dao.nextWarehouseId();
        this.name = new SimpleStringProperty("");
        this.location = new SimpleStringProperty("");
    }

    @Override
    public String toString() {
        return name.get();
    }
}
