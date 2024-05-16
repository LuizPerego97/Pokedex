package com.projeto.projectPokemon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.projectPokemon.dto.PokemonDTO;
import com.projeto.projectPokemon.entity.Pokemon;
import com.projeto.projectPokemon.service.PokemonService;

@RestController
@RequestMapping(value = "/pokemons")
public class PokemonController {

	@Autowired
	private PokemonService pokemonService;

	@GetMapping(value = "/getAll")
	public ResponseEntity<?> getAll() {
		List<PokemonDTO> pokemons = getPokemonService().allPokemon();
		try {
			if (!pokemons.isEmpty()) {
				return ResponseEntity.status(HttpStatus.OK).body(pokemons);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lista de pokemons vazia");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno!" + e.getMessage());
		}
	}

	@CrossOrigin
	@GetMapping(value = "/getById")
	public ResponseEntity<?> getById(@RequestParam Long id) {
		try {
			Pokemon pokemonFound = getPokemonService().getPokemonByID(id);
			if (pokemonFound != null) {
				return ResponseEntity.status(HttpStatus.OK).body(PokemonDTO.converterParaDTO(pokemonFound));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pokémon com esse id não existe");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno!!" + e.getMessage());
		}
	}

	@GetMapping(value = "/searchAtributes")
	public ResponseEntity<?> findByAtributes(@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "num", required = false) String num,
			@RequestParam(value = "id", required = false) Long id) {
		try {
			Pokemon pokemonFound = getPokemonService().findByAtributos(nome, num, id);
			if (pokemonFound != null) {
				return ResponseEntity.status(HttpStatus.OK).body(PokemonDTO.converterParaDTO(pokemonFound));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum pokemon correspondente");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno!!" + e.getMessage());
		}
	}

	@GetMapping(value = "/getByObject")
	public ResponseEntity<PokemonDTO> findByObject(@RequestBody Pokemon pokemonSend) {
		try {
			PokemonDTO pokemonDTO = new PokemonDTO();
			Pokemon pokemonFound = getPokemonService().findByObject(pokemonSend);
			if (pokemonFound != null) {
				return ResponseEntity.status(HttpStatus.OK).body(PokemonDTO.converterParaDTO(pokemonFound));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pokemonDTO);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@PutMapping
	public ResponseEntity<?> updatePokemon(@RequestParam Long id, @RequestBody Pokemon pokemonUpdate) {
		try {
			getPokemonService().updatePokemon(id, pokemonUpdate);
			return getPokemonService().updatePokemon(id, pokemonUpdate);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno" + e.getMessage());
		}
	}

	@PostMapping(value = "/add")
	public ResponseEntity<String> addPokemon(@RequestBody Pokemon pokemon) {
		try {
			getPokemonService().savePokemon(pokemon);
			return ResponseEntity.status(HttpStatus.OK).body("Pokemon adicionado com sucesso.");
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: Um Pokemon com os mesmos dados já existe.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno: " + e.getMessage());
		}
	}

	@PostMapping(value = "/addList")
	public ResponseEntity<String> addList(@RequestBody List<Pokemon> pokemons) {
		try {
			if (pokemons.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lista vazia");
			}
			getPokemonService().saveList(pokemons);
			return ResponseEntity.status(HttpStatus.OK).body("Lista adicionada com sucesso");
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(" Um ou mais Pokemons com os mesmos dados já existentes.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno: " + e.getMessage());
		}
	}

	@DeleteMapping
	public ResponseEntity<?> deletePokemon(@RequestParam Long id) {
		return getPokemonService().deletePokemon(id);
	}

	public PokemonService getPokemonService() {
		return pokemonService;
	}
}
