package com.projeto.projectPokemon.dto;

import java.util.List;

import org.modelmapper.ModelMapper;

import com.projeto.projectPokemon.entity.Pokemon;

public class PokemonDTO {

    private String num;
    private String name;
    private List<String> type;
    private byte[] img;
    private String height;
    private String weight;
    private List<String> weaknesses;
    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public List<String> getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(List<String> weaknesses) {
        this.weaknesses = weaknesses;
    }
    
	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}
     
	public static PokemonDTO converterParaDTO(Pokemon pokemon) {
		return new ModelMapper().map(pokemon, PokemonDTO.class);
	}
     
}
