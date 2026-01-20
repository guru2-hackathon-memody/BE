package com.guru2.memody.repository;

import com.guru2.memody.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    @Query(
            value = """
        SELECT *
        FROM artist
        WHERE itunes_artist_id = 0
          AND BINARY artist_name = :name
        LIMIT 1
    """,
            nativeQuery = true
    )
    Optional<Artist> findOnboardingArtistByExactName(
            @Param("name") String name
    );

    @Query(
            value = """
           SELECT * FROM artist
           WHERE itunes_artist_id = :itunesArtistId
            LIMIT 1
        """,
            nativeQuery = true
    )
    Optional<Artist> findByItunesArtistId(Long itunesArtistId);


}
