package infra;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class AsciiArtRepository {
    private final Map<String, StringBuilder> asciiArts;

    public AsciiArtRepository(String filePath) {
        this.asciiArts = new HashMap<>();
        carregarArtesDoArquivo(filePath);
    }

    private void carregarArtesDoArquivo(String filePath) {
        try (BufferedReader reader = abrirReader(filePath)) {
            String line;
            String currentPokemon = null;
            StringBuilder currentArt = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("===") && line.endsWith("===")) {
                    if (currentPokemon != null && currentArt != null) {
                        asciiArts.put(currentPokemon, currentArt);
                    }
                    currentPokemon = line.substring(3, line.length() - 3).trim();
                    currentArt = new StringBuilder();
                } else {
                    if (currentArt != null) {
                        if (currentArt.length() > 0) {
                            currentArt.append("\n");
                        }
                        currentArt.append(line);
                    }
                }
            }
            if (currentPokemon != null && currentArt != null) {
                asciiArts.put(currentPokemon, currentArt);
            }

        } catch (IOException e) {
            System.err.println("Erro ao carregar o arquivo de artes ASCII: " + e.getMessage());
        }
    }

    private BufferedReader abrirReader(String filePath) throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(filePath);
        if (is != null) {
            return new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        }
        java.io.File file = new java.io.File(filePath);
        if (file.exists()) {
            return new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));
        }
        throw new IOException("Ficheiro não encontrado: " + file.getAbsolutePath());
    }

    public String getAsciiArt(String pokemonName) {
        StringBuilder art = asciiArts.get(pokemonName.toUpperCase());
        if (art != null) {
            return art.toString();
        }
        return "       /\\_/\\\n      ( o.o )\n       > ^ <   [Desconhecido]";
    }
}