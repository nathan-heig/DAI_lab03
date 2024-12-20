package ch.bdr.ImdbLike.Film;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import ch.bdr.ImdbLike.Utils.Genre;

import java.util.Optional;

public interface FilmRepository extends JpaRepository<Film, Integer> {

    @Query("SELECT f FROM Film f WHERE f.genre = :genre")
    Optional<List<Film>> findByGenre(@Param("genre") Genre genre);


    @Query("SELECT f FROM Film f WHERE f.id = :id")
    Optional<Film> findById(@Param("id") int id);

    @Query("SELECT f FROM Film f")
    List<Film> findAll();

    


}