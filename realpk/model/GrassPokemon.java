package model;

public class GrassPokemon extends Pokemon {
    private float hpAbsorbRatio;

    public GrassPokemon(String name, int level, int hp, int attack, int defense, int speed) {
        super(name, "GRASS", level, hp, attack, defense, speed);
        this.hpAbsorbRatio = 0.20f;
    }

    @Override
    public void performSpecialAbility() {
        System.out.println(this.name + " ativou a sua habilidade de Planta: Aumentando a defesa!");
        this.defense += 5;
    }

    public float getHpAbsorbRatio() {
        return hpAbsorbRatio;
    }
}