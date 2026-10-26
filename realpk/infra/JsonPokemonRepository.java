package infra;

import model.GrassPokemon;
import model.FirePokemon;
import model.WaterPokemon;
import model.Move;
import model.Pokemon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JsonPokemonRepository implements PokemonRepository {

    private final List<Pokemon> cachePokemons;

    public JsonPokemonRepository(String filePath) {
        this.cachePokemons = new ArrayList<>();
        carregarDadosDoArquivo(filePath);
    }

    /**
     * Lê o arquivo estruturado usando BufferedReader e realiza o parsing.
     */
    private void carregarDadosDoArquivo(String filePath) {
        StringBuilder jsonContent = new StringBuilder();

        try (BufferedReader reader = abrirReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line.trim());
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de Pokémons: " + e.getMessage());
            return;
        }

        parseJsonToObjects(jsonContent.toString());
    }

    private BufferedReader abrirReader(String filePath) throws IOException {
        // Tenta carregar como recurso do classpath primeiro
        InputStream is = getClass().getClassLoader().getResourceAsStream(filePath);
        if (is != null) {
            return new BufferedReader(new InputStreamReader(is));
        }
        // Se não encontrar no classpath, tenta como caminho do sistema de arquivos
        return new BufferedReader(new FileReader(filePath));
    }

    /**
     * Parser manual básico para JSON sem bibliotecas externas
     */
    private void parseJsonToObjects(String json) {
        if (json.startsWith("[")) {
            json = json.substring(1, json.length() - 1);
        }

        // Divide o array JSON pelos objetos de Pokémon
        String[] pokemonBlocks = json.split("(?<=\\}),\\s*(?=\\{)");

        for (String block : pokemonBlocks) {
            try {
                int id = Integer.parseInt(extractValue(block, "id"));
                String name = extractValue(block, "name");
                String type = extractValue(block, "type");
                int hp = Integer.parseInt(extractValue(block, "hp"));
                int attack = Integer.parseInt(extractValue(block, "attack"));
                int defense = Integer.parseInt(extractValue(block, "defense"));
                int speed = Integer.parseInt(extractValue(block, "speed"));

                // Instanciação polimórfica com base no Tipo
                Pokemon pokemon = criarInstanciaPokemon(type, name, hp, attack, defense, speed);

                // Parsing e inclusão dos golpes (Moves)
                if (block.contains("\"moves\"")) {
                    String movesBlock = block.substring(block.indexOf("\"moves\""));
                    parseMoves(movesBlock, pokemon);
                }

                cachePokemons.add(pokemon);
            } catch (Exception e) {
                System.err.println("Erro ao realizar parsing do Pokémon: " + e.getMessage());
            }
        }
    }

    private Pokemon criarInstanciaPokemon(String type, String name, int hp, int attack, int defense, int speed) {
        return switch (type.toUpperCase()) {
            case "FIRE" -> new FirePokemon(name, 5, hp, attack, defense, speed);
            case "WATER" -> new WaterPokemon(name, 5, hp, attack, defense, speed);
            case "GRASS" -> new GrassPokemon(name, 5, hp, attack, defense, speed);
            default -> throw new IllegalArgumentException("Tipo desconhecido: " + type);
        };
    }

    private void parseMoves(String movesJson, Pokemon pokemon) {
        String[] moveBlocks = movesJson.split("(?<=\\}),\\s*(?=\\{)");
        for (String moveBlock : moveBlocks) {
            try {
                String moveName = extractValue(moveBlock, "name");
                int power = Integer.parseInt(extractValue(moveBlock, "power"));
                int accuracy = Integer.parseInt(extractValue(moveBlock, "accuracy"));
                String type = extractValue(moveBlock, "type");
                int maxPP = Integer.parseInt(extractValue(moveBlock, "maxPP"));

                pokemon.addMove(new Move(moveName, power, accuracy, type, maxPP));
            } catch (Exception ignored) {
                // Avança para o próximo golpe em caso de falha pontual de parsing
            }
        }
    }

    private String extractValue(String json, String key) {
        String pattern = "\"" + key + "\":\\s*\"?([^\",\\}\\]]+)\"?";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher m = p.matcher(json);
        if (m.find()) {
            return m.group(1).trim();
        }
        throw new IllegalArgumentException("Chave não encontrada: " + key);
    }

    @Override
    public List<Pokemon> findAll() {
        return new ArrayList<>(cachePokemons);
    }

    @Override
    public Optional<Pokemon> findByName(String name) {
        return cachePokemons.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public Optional<Pokemon> findById(int id) {
        // Exemplo simplificado de busca no cache
        return cachePokemons.stream()
                .filter(p -> p.getName().toLowerCase().contains(String.valueOf(id)))
                .findFirst();
    }
}