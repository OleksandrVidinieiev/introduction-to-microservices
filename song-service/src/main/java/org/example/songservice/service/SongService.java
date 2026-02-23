package org.example.songservice.service;

import static org.example.songservice.util.ValidationHelper.parseAndValidateCsv;
import static org.example.songservice.util.ValidationHelper.parseAndValidateId;

import java.util.ArrayList;
import java.util.List;

import org.example.songservice.dto.SongDto;
import org.example.songservice.entity.Song;
import org.example.songservice.exception.SongAlreadyExistsException;
import org.example.songservice.exception.SongNotFoundException;
import org.example.songservice.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SongService {

  @Autowired
  private SongRepository songRepository;

  @Transactional
  public Long createSong(SongDto songDto) {
    if (songRepository.existsById(songDto.getId())) {
      throw new SongAlreadyExistsException("Metadata for resource ID=" + songDto.getId() + " already exists");
    }

    Song song = new Song();
    song.setId(songDto.getId());
    song.setName(songDto.getName());
    song.setArtist(songDto.getArtist());
    song.setAlbum(songDto.getAlbum());
    song.setDuration(songDto.getDuration());
    song.setYear(songDto.getYear());

    return songRepository.save(song).getId();
  }

  public SongDto getSong(String id) {
    Song song = songRepository.findById(parseAndValidateId(id))
      .orElseThrow(() -> new SongNotFoundException("Song metadata for ID=" + id + " not found"));

    SongDto dto = new SongDto();
    dto.setId(song.getId());
    dto.setName(song.getName());
    dto.setArtist(song.getArtist());
    dto.setAlbum(song.getAlbum());
    dto.setDuration(song.getDuration());
    dto.setYear(song.getYear());

    return dto;
  }

  @Transactional
  public List<Long> deleteSongs(String idsCsv) {
    List<Long> ids = parseAndValidateCsv(idsCsv);
    List<Long> deletedIds = new ArrayList<>();

    for (Long id : ids) {
      if (songRepository.existsById(id)) {
        songRepository.deleteById(id);
        deletedIds.add(id);
      }
    }

    return deletedIds;
  }
}

