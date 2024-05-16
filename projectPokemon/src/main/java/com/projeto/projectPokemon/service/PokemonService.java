package com.projeto.projectPokemon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.projeto.projectPokemon.dto.PokemonDTO;
import com.projeto.projectPokemon.entity.Pokemon;
import com.projeto.projectPokemon.exception.PokemonNotCorrespondingMatchException;
import com.projeto.projectPokemon.repository.PokemonRepository;

@Service
public class PokemonService {

	@Autowired
	private PokemonRepository pokemonRepository;

	// Metodo get All funcionando corretamente
	public List<PokemonDTO> allPokemon() {
		List<Pokemon> listPokemon = getPokemonRepository().findAll();
		List<PokemonDTO> listPokemonDTO = listPokemon.stream().map(pokemon -> PokemonDTO.converterParaDTO(pokemon))
				.toList();

		return listPokemonDTO;
	}

	// Metodo com specification
	public Pokemon getPokemonByID(Long id) {
		return getPokemonRepository().findOneById(id);
	}

	// Metodo com specification
	public Pokemon findByAtributos(String name, String num, Long id) {
		if (name != null && !name.isEmpty()) {
			return getPokemonRepository().findByName(name);
		} else if (num != null && !num.isEmpty()) {
			return getPokemonRepository().findByNum(num);
		} else if (id != null) {
			return getPokemonRepository().findOneById(id);
		}
		return null;
	}

	// Metodo nativo JPA
	public Pokemon findByObject(Pokemon pokemon) {
		return getPokemonRepository().findOne(Example.of(pokemon)).orElse(null);
	}

	public ResponseEntity<?> updatePokemon(Long id, Pokemon pokemonUpdates) {
		Pokemon pokemonFound = getPokemonByID(id);
		if (pokemonFound == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pokemon não encontrado");
		} else {
			pokemonFound.setNum(
					pokemonUpdates.getNum() != null ? pokemonUpdates.getNum() : pokemonFound.getNum());
			pokemonFound.setName(
					pokemonUpdates.getName() != null ? pokemonUpdates.getName() : pokemonFound.getName());
			pokemonFound.setImg(
					pokemonUpdates.getImg() != null ? pokemonUpdates.getImg() : pokemonFound.getImg());
			pokemonFound.setType(
					pokemonUpdates.getType() != null ? pokemonUpdates.getType() : pokemonFound.getType());
			pokemonFound.setHeight(
					pokemonUpdates.getHeight() != null ? pokemonUpdates.getHeight() : pokemonFound.getHeight());
			pokemonFound.setWeight(
					pokemonUpdates.getWeight() != null ? pokemonUpdates.getWeight() : pokemonFound.getWeight());
			pokemonFound.setWeaknesses(pokemonUpdates.getWeaknesses() != null ? pokemonUpdates.getWeaknesses()
					: pokemonFound.getWeaknesses());

			savePokemon(pokemonFound);
			return ResponseEntity.status(HttpStatus.OK).body(pokemonFound);
		}
	}

	public void savePokemon(Pokemon pokemon) {
		getPokemonRepository().save(pokemon);
	}

	public void saveList(List<Pokemon> pokemons) {
		getPokemonRepository().saveAll(pokemons);
	}

	public ResponseEntity<String> deletePokemon(Long id) {
		try {
			Pokemon pokemon = getPokemonRepository().findOneById(id);
			if (pokemon != null) {
				getPokemonRepository().deleteById(id);
				return ResponseEntity.status(HttpStatus.OK).body("Pokémon excluído com sucesso!");
			} else {
				throw new PokemonNotCorrespondingMatchException();
			}
		} catch (PokemonNotCorrespondingMatchException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao excluir o Pokémon: " + e.getMessage());
		}
	}

	public PokemonRepository getPokemonRepository() {
		return pokemonRepository;
	}

}
