package com.projeto.projectPokemon.exception;

public class PokemonNotCorrespondingMatchException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PokemonNotCorrespondingMatchException() {
        super("Nenhum Pokémon correspondente");
    }

    public PokemonNotCorrespondingMatchException(String errorMessage) {
        super(errorMessage);
    }
}

