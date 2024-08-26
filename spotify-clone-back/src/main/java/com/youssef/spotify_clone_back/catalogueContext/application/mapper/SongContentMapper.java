package com.youssef.spotify_clone_back.catalogueContext.application.mapper;

import com.youssef.spotify_clone_back.catalogueContext.application.dto.SaveSongDTO;
import com.youssef.spotify_clone_back.catalogueContext.application.dto.SongContentDto;
import com.youssef.spotify_clone_back.catalogueContext.domain.SongContent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SongContentMapper {

    @Mapping(source = "song.publicId", target = "publicId")
    SongContentDto songContentToSongContentDTO(SongContent songContent);

    SongContent saveSongDTOToSong(SaveSongDTO songDTO);
}
