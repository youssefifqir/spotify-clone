package com.youssef.spotify_clone_back.catalogueContext.application.dto;

import com.youssef.spotify_clone_back.catalogueContext.application.vo.SongAuthorVO;
import com.youssef.spotify_clone_back.catalogueContext.application.vo.SongTitleVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record SaveSongDTO(@Valid SongTitleVO title,
                          @Valid SongAuthorVO author,
                          @NotNull byte[] cover,
                          @NotNull String coverContentType,
                          @NotNull byte[] file,
                          @NotNull String fileContentType
                          ) {
}
