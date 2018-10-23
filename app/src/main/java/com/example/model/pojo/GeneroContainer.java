package com.example.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GeneroContainer {

        @SerializedName("genres")
        private List<Genero> genres;

        public List<Genero> getGenres() {
            return genres;
        }

        public void setGenres(List<Genero> genres) {
            this.genres = genres;
        }

}

