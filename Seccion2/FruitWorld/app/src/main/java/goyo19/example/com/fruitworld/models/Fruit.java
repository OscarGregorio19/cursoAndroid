package goyo19.example.com.fruitworld.models;

public class Fruit {
    private String nombre;
    private int icon;
    private String origin;

    public Fruit() {}

    public Fruit(String nombre, int icon, String origin) {
        this.nombre = nombre;
        this.icon = icon;
        this.origin = origin;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}
