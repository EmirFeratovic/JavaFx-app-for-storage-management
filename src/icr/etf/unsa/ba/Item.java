package icr.etf.unsa.ba;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Item {
    private StorageDAOdb dao = StorageDAOdb.getInstance();
    private int id;
    private SimpleStringProperty name, description;
    private SimpleDoubleProperty price, weight;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public double getPrice() {
        return price.get();
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public double getWeight() {
        return weight.get();
    }

    public SimpleDoubleProperty weightProperty() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight.set(weight);
    }

    public Item(int id, String name, String description, double price, double weight) {
        this.id =id;
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.price = new SimpleDoubleProperty(price);
        this.weight = new SimpleDoubleProperty(weight);
    }

    public Item() {
        this.id =dao.nextItemId();
        this.name = new SimpleStringProperty("");
        this.description = new SimpleStringProperty("");
        this.price = new SimpleDoubleProperty(0);
        this.weight = new SimpleDoubleProperty(0);
    }

    @Override
    public String toString() {
        return getName();
    }

}
