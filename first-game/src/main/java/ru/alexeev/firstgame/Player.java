package ru.alexeev.firstgame;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Random;

public class Player {
    private final IntegerProperty hp;
    private final StringProperty name;
    private final int damage;
    private final Random random;
    private boolean isHitting;

    public Player(String name, int healthPoints, int damage) {
        this.hp = new SimpleIntegerProperty(healthPoints);
        this.name = new SimpleStringProperty(name);
        this.damage = damage;
        this.random = new Random();
    }

    public void setIsHitting(boolean isHitting) {
        this.isHitting = isHitting;
    }
    public boolean isHitting(){
        return isHitting;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public IntegerProperty hpProperty() {
        return hp;
    }

    public void takeDamage(int damage) {
        hp.set(Math.max(0, hp.get() - damage));
    }

    public int attack() {
        return (int) (damage * (0.5 + random.nextDouble())); // Урон: от 50% до 150% от damage
    }

    public boolean isAlive() {
        return hp.get() > 0;
    }



}
