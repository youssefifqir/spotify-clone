package com.youssef.spotify_clone_back.catalogueContext.application;

import com.youssef.spotify_clone_back.catalogueContext.application.dto.FavoriteSongDto;
import com.youssef.spotify_clone_back.catalogueContext.application.dto.ReadSongInfoDTO;
import com.youssef.spotify_clone_back.catalogueContext.application.dto.SaveSongDTO;
import com.youssef.spotify_clone_back.catalogueContext.application.dto.SongContentDto;
import com.youssef.spotify_clone_back.catalogueContext.application.mapper.SongContentMapper;
import com.youssef.spotify_clone_back.catalogueContext.application.mapper.SongMapper;
import com.youssef.spotify_clone_back.catalogueContext.domain.Favorite;
import com.youssef.spotify_clone_back.catalogueContext.domain.FavoriteId;
import com.youssef.spotify_clone_back.catalogueContext.domain.Song;
import com.youssef.spotify_clone_back.catalogueContext.domain.SongContent;
import com.youssef.spotify_clone_back.catalogueContext.repository.FavoriteRepository;
import com.youssef.spotify_clone_back.catalogueContext.repository.SongContentRepository;
import com.youssef.spotify_clone_back.catalogueContext.repository.SongRepository;
import com.youssef.spotify_clone_back.infrastructure.service.dto.State;
import com.youssef.spotify_clone_back.infrastructure.service.dto.StateBuilder;
import com.youssef.spotify_clone_back.userContext.ReadUserDto;
import com.youssef.spotify_clone_back.userContext.application.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class SongService {

    private final SongMapper songMapper;
    private final SongRepository songRepository;
    private final SongContentRepository songContentRepository;
    private final SongContentMapper songContentMapper;
    private final FavoriteRepository favoriteRepository;

    private final UserService userService;

    public SongService(SongMapper songMapper, SongRepository songRepository, SongContentRepository songContentRepository, SongContentMapper songContentMapper, FavoriteRepository favoriteRepository, UserService userService) {
        this.songMapper = songMapper;
        this.songRepository = songRepository;
        this.songContentRepository = songContentRepository;
        this.songContentMapper = songContentMapper;
        this.favoriteRepository = favoriteRepository;
        this.userService = userService;
    }
    public ReadSongInfoDTO create(SaveSongDTO saveSongDTO) {
        Song song = songMapper.saveSongDTOToSong(saveSongDTO);
        Song songSaved = songRepository.save(song);

        SongContent songContent = songContentMapper.saveSongDTOToSong(saveSongDTO);
        songContent.setSong(songSaved);

        songContentRepository.save(songContent);
        return songMapper.songToReadSongInfoDTO(songSaved);
    }

    @Transactional(readOnly = true)
    public List<ReadSongInfoDTO> getAll() {

        List<ReadSongInfoDTO> allSongs = songRepository.findAll()
                .stream()
                .map(songMapper::songToReadSongInfoDTO)
                .toList();

        if(userService.isAuthenticated()) {
            return fetchFavoritesStatusForSongs(allSongs);
        }

        return allSongs;
    }

    public Optional<SongContentDto> getOneByPublicId(UUID publicId) {
        Optional<SongContent> songByPublicId = songContentRepository.findOneBySongPublicId(publicId);
        return songByPublicId.map(songContentMapper::songContentToSongContentDTO);
    }

    public List<ReadSongInfoDTO> search(String searchTerm) {
        List<ReadSongInfoDTO> searchedSongs = songRepository.findByTitleOrAuthorContaining(searchTerm)
                .stream()
                .map(songMapper::songToReadSongInfoDTO)
                .collect(Collectors.toList());

        if(userService.isAuthenticated()) {
            return fetchFavoritesStatusForSongs(searchedSongs);
        } else {
            return searchedSongs;
        }
    }
    public State<FavoriteSongDto, String> addOrRemoveFromFavorite(FavoriteSongDto favoriteSongDTO, String email) {
        StateBuilder<FavoriteSongDto, String> builder = State.builder();
        Optional<Song> songToLikeOpt = songRepository.findOneByPublicId(favoriteSongDTO.publicId());
        if (songToLikeOpt.isEmpty()) {
            return builder.forError("Song public id doesn't exist").build();
        }

        Song songToLike = songToLikeOpt.get();

        ReadUserDto userWhoLikedSong = userService.getByEmail(email).orElseThrow();

        if (favoriteSongDTO.favorite()) {
            Favorite favorite = new Favorite();
            favorite.setSongPublicId(songToLike.getPublicId());
            favorite.setUserEmail(userWhoLikedSong.email());
            favoriteRepository.save(favorite);
        } else {
            FavoriteId favoriteId = new FavoriteId(songToLike.getPublicId(), userWhoLikedSong.email());
            favoriteRepository.deleteById(favoriteId);
            favoriteSongDTO = new FavoriteSongDto(false, songToLike.getPublicId());
        }

        return builder.forSuccess(favoriteSongDTO).build();
    }

    public List<ReadSongInfoDTO> fetchFavoriteSongs(String email) {
        return songRepository.findAllFavoriteByUserEmail(email)
                .stream()
                .map(songMapper::songToReadSongInfoDTO)
                .toList();
    }

    private List<ReadSongInfoDTO> fetchFavoritesStatusForSongs(List<ReadSongInfoDTO> songs) {
        ReadUserDto authenticatedUser = userService.getAuthenticatedUserFromSecurityContext();

        List<UUID> songPublicIds = songs.stream().map(ReadSongInfoDTO::getPublicId).toList();

        List<UUID> userFavoriteSongs = favoriteRepository.findAllByUserEmailAndSongPublicIdIn(authenticatedUser.email(), songPublicIds)
                .stream().map(Favorite::getSongPublicId).toList();

        return songs.stream().peek(song -> {
            if (userFavoriteSongs.contains(song.getPublicId())) {
                song.setFavorite(true);
            }
        }).toList();
    }
}


