package model;

public class WaterPokemon extends Pokemon {
    private int healAmount;

    public WaterPokemon(String name, int level, int hp, int attack, int defense, int speed) {
        super(name, "WATER", level, hp, attack, defense, speed);
        this.healAmount = 15;
    }

    @Override
    public void performSpecialAbility() {
        System.out.println(this.name + " ativou a sua habilidade de Água e recuperou HP!");
        this.currentHp = Math.min(this.maxHp, this.currentHp + healAmount);
    }

    public int getHealAmount() {
        return healAmount;
    }
}