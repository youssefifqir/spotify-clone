package com.youssef.spotify_clone_back.catalogueContext.repository;

import com.youssef.spotify_clone_back.catalogueContext.domain.Favorite;
import com.youssef.spotify_clone_back.catalogueContext.domain.FavoriteId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FavoriteRepository extends JpaRepository<Favorite,FavoriteId> {
    void deleteById(FavoriteId favoriteId);
    List<Favorite> findAllByUserEmailAndSongPublicIdIn(String email, List<UUID> songPublicIds);

}
