package at.technikum.mtcg.models;

public class Card {
    private String id;
    private String name;
    private double damage;
    private String type;

    public Card(String id, String name, double damage, String type) {
        this.id = id;
        this.name = name;
        this.damage = damage;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getDamage() {
        return damage;
    }

    public String getType() {
        return type;
    }
}