package infra;

import model.Pokemon;
import java.util.List;
import java.util.Optional;

public interface PokemonRepository {
    List<Pokemon> findAll();
    Optional<Pokemon> findByName(String name);
    Optional<Pokemon> findById(int id);
}