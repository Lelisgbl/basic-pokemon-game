package model;

public class FirePokemon extends Pokemon {
   

    public FirePokemon(String name, int level, int hp, int attack, int defense, int speed) {
        super(name, "FIRE", level, hp, attack, defense, speed);
       
    }

    @Override
    public void performSpecialAbility() {
        System.out.println(this.name + " ativou a sua habilidade especial de Fogo: Aumentando poder de ataque!");
        this.attack += 5;
    }

   
}