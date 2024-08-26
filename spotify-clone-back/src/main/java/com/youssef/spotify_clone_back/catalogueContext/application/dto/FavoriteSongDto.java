package com.youssef.spotify_clone_back.catalogueContext.application.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record FavoriteSongDto(@NotNull boolean favorite, @NotNull UUID publicId) {
}
