import java.io.Serializable;

public class Beer implements Serializable {
    private String name;
    private String style;
    private double strength;

    public Beer(String c_name, String c_style, double c_strength) {
        name = c_name;
        style = c_style;
        strength = c_strength;
    }

    public String getName() { return name; }
    public String getStyle() { return style; }
    public double getStrength() { return strength; }

    public String toString() { return "name: " + getName() + ", stlye: " + getStyle() + ", strength: " + getStrength(); }
}
