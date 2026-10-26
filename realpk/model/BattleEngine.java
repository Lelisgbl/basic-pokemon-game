package model;

public class BattleEngine {
    private Pokemon playerPokemon;
    private Pokemon opponentPokemon;
    private int currentTurn;

    public BattleEngine(Pokemon playerPokemon, Pokemon opponentPokemon) {
        this.playerPokemon = playerPokemon;
        this.opponentPokemon = opponentPokemon;
        this.currentTurn = 1;
    }

    public void executeTurn(int playerMoveIndex, int opponentMoveIndex) {
        System.out.println("\n--- Turno " + currentTurn + " ---");

        // Quem tiver a maior velocidade ataca primeiro
        if (playerPokemon.getSpeed() >= opponentPokemon.getSpeed()) {
            playerPokemon.attack(playerMoveIndex, opponentPokemon);
            if (!opponentPokemon.isFainted()) {
                opponentPokemon.attack(opponentMoveIndex, playerPokemon);
            }
        } else {
            opponentPokemon.attack(opponentMoveIndex, playerPokemon);
            if (!playerPokemon.isFainted()) {
                playerPokemon.attack(playerMoveIndex, opponentPokemon);
            }
        }

        currentTurn++;
    }

    public boolean isBattleOver() {
        return playerPokemon.isFainted() || opponentPokemon.isFainted();
    }

    public Pokemon checkWinner() {
        if (playerPokemon.isFainted() && !opponentPokemon.isFainted()) {
            return opponentPokemon;
        } else if (opponentPokemon.isFainted() && !playerPokemon.isFainted()) {
            return playerPokemon;
        }
        return null; // A batalha ainda decorre ou deu empate
    }

    public Pokemon getPlayerPokemon() {
        return playerPokemon;
    }

    public Pokemon getOpponentPokemon() {
        return opponentPokemon;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }
}