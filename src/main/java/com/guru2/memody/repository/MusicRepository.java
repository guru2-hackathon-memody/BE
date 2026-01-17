package com.guru2.memody.repository;

import com.guru2.memody.entity.Music;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MusicRepository extends CrudRepository<Music, Long> {
    Music findMusicByMusicId(Long musicId);

    Optional<Music> findMusicByAppleMusicUrl(String trackViewUrl);
}
