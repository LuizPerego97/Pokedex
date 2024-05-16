package com.projeto.projectPokemon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.projeto.projectPokemon.entity.Pokemon;

public interface PokemonRepository extends JpaRepository<Pokemon, Long>, JpaSpecificationExecutor<Pokemon> {

	// Metodo customizado

	// Specifiction
	default Pokemon findOneById(Long id) {
		return findOne((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id)).orElse(null);
	}

	// buscar por nome
	default Pokemon findByName(String name) {
		return findOne((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name)).orElse(null);
	}

	// buscar por número
	default Pokemon findByNum(String num) {
		return findOne((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("num"), num)).orElse(null);

	}

	// JPA NATIVO
	Pokemon getByName(String name);

	Pokemon getByNum(String num);

}
