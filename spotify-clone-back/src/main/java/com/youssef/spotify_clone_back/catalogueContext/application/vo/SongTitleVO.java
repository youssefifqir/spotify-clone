package com.youssef.spotify_clone_back.catalogueContext.application.vo;

import jakarta.validation.constraints.NotBlank;

public record SongTitleVO(@NotBlank String value) {
}
