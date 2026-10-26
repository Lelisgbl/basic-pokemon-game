package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Pokemon {
    protected String name;
    protected String type;
    protected int level;
    protected int maxHp;
    protected int currentHp;
    protected int attack;
    protected int defense;
    protected int speed;
    protected List<Move> moves;

    public Pokemon(String name, String type, int level, int hp, int attack, int defense, int speed) {
        this.name = name;
        this.type = type;
        this.level = level;
        this.maxHp = hp;
        this.currentHp = hp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.moves = new ArrayList<>();
    }

    // Método abstrato a ser implementado pelas subclasses
    public abstract void performSpecialAbility();

    public void attack(int moveIndex, Pokemon target) {
        if (moveIndex < 0 || moveIndex >= moves.size()) {
            System.out.println("Movimento inválido!");
            return;
        }

        Move move = moves.get(moveIndex);
        if (move.useMove()) {
            // Cálculo simples de dano baseado no ataque e defesa
            int rawDamage = (this.attack + move.getPower()) - (target.getDefense() / 2);
            int damage = Math.max(1, rawDamage); // Garante no mínimo 1 de dano

            System.out.println(this.name + " usou " + move.getName() + "!");
            target.takeDamage(damage);
        } else {
            System.out.println(move.getName() + " não tem mais PP!");
        }
    }

    public void takeDamage(int damage) {
        this.currentHp -= damage;
        if (this.currentHp < 0) {
            this.currentHp = 0;
        }
        System.out.println(this.name + " recebeu " + damage + " de dano. HP restante: " + this.currentHp + "/" + this.maxHp);
    }

    public boolean isFainted() {
        return this.currentHp <= 0;
    }

    public boolean addMove(Move move) {
        if (moves.size() < 4) {
            moves.add(move);
            return true;
        }
        return false; // Máximo de 4 golpes atingido
    }

    public float getHpPercentage() {
        return ((float) currentHp / maxHp) * 100;
    }

    // Getters e Setters
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpeed() {
        return speed;
    }

    public List<Move> getMoves() {
        return new ArrayList<>(moves);
    }
}