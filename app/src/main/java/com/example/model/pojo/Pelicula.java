package com.example.model.pojo;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Pelicula implements Serializable {
    public static final String TMDB_IMAGE_PATH = "http://image.tmdb.org/t/p/w154";

    @SerializedName("poster_path")
    private String poster;

    @SerializedName("title")
    private String titulo;

    private Integer id;

    public static final String TMDB_BACKDROP_IMAGE_PATH = "http://image.tmdb.org/t/p/w780";

    @SerializedName("backdrop_path")
    private String fondo;

    @SerializedName("vote_count")
    private Integer votos;
    private String original_title;
    private String overview;
    private Boolean estaFavorito;


    @SerializedName("vote_average")
    private float votosPromedio;

    @SerializedName("genre_ids")
    private List<Integer> genreIds;

    @SerializedName("genre")
    private List<Genero> genres;

    @SerializedName("release_date")
    private String releaseDate;

    private Trailer trailer;


    public Integer getIdPelicula() {
        return id;
    }

    public Pelicula(){
        estaFavorito = false;
        //trailerList = new ArrayList<>();
    }
    public String getPoster() {

        return TMDB_IMAGE_PATH + poster;
    }


    public String getFondo() {

        return TMDB_BACKDROP_IMAGE_PATH + fondo;
    }

    public void setFondo(String poster) {
        this.fondo= fondo;
    }


    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void cambiarEstadoFav (){ this.estaFavorito=!this.estaFavorito; }
    public Boolean getEstaFavorito() { return estaFavorito; }
    public void setEstaFavorito(Boolean variable){ this.estaFavorito=variable;}


    public Integer getVotos() {
        return votos;
    }

    public void setVotos(Integer votos) {
        this.votos = votos;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getVotosPromedio() {
        return votosPromedio;
    }

    public void setVotosPromedio(float votosPromedio) {
        this.votosPromedio = votosPromedio;
    }

    public List<Integer> getGenreIds(){ return genreIds;}

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Trailer getTrailer() {
        return trailer;
    }

    public void setTrailer(Trailer trailer) {
        this.trailer = trailer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pelicula pelicula = (Pelicula) o;
        return Objects.equals(id, pelicula.id);
    }



}