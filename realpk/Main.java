import infra.AsciiArtRepository;
import infra.JsonPokemonRepository;
import infra.PokemonRepository;
import model.Pokemon;
import view.BattleScreen;

public class Main {
    public static void main(String[] args) {
        // Carrega os dados dos Pokémons de um arquivo JSON
        PokemonRepository pokemonRepo = new JsonPokemonRepository("pokemons.json");
        
        // Carrega as artes ASCII de um arquivo JSON externo separado da view
        AsciiArtRepository artRepo = new AsciiArtRepository("ascii_arts.txt");
        
        // Injeta o repositório de artes na camada de visão
        BattleScreen battleScreen = new BattleScreen(artRepo);

        // Exemplo de uso
        Pokemon charmander = pokemonRepo.findByName("CHARMANDER").orElse(null);
        Pokemon bulbasaur = pokemonRepo.findByName("BULBASAUR").orElse(null);

        if (charmander != null && bulbasaur != null) {
            battleScreen.renderBattleUI(charmander, bulbasaur);
        }
    }
}