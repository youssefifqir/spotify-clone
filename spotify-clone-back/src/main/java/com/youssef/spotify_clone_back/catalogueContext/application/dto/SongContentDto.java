package com.youssef.spotify_clone_back.catalogueContext.application.dto;

import jakarta.persistence.Lob;

import java.util.UUID;

public record SongContentDto(
        UUID publicId,
        @Lob byte[] file,String fileContentType
) {
}
