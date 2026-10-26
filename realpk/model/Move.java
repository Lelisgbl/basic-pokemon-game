package model;

public class Move {
    private String name;
    private int power;
    private int accuracy;
    private String type;
    private int currentPP;
    private int maxPP;

    public Move(String name, int power, int accuracy, String type, int maxPP) {
        this.name = name;
        this.power = power;
        this.accuracy = accuracy;
        this.type = type;
        this.maxPP = maxPP;
        this.currentPP = maxPP;
    }

    public boolean useMove() {
        if (currentPP > 0) {
            currentPP--;
            return true;
        }
        return false;
    }

    // Getters e Setters
    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public String getType() {
        return type;
    }

    public int getCurrentPP() {
        return currentPP;
    }

    public int getMaxPP() {
        return maxPP;
    }
}