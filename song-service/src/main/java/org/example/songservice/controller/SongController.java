package org.example.songservice.controller;

import jakarta.validation.Valid;
import org.example.songservice.dto.DeleteSongsResponse;
import org.example.songservice.dto.SongDto;
import org.example.songservice.dto.PostSongResponse;
import org.example.songservice.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/songs")
public class SongController {

  @Autowired
  private SongService songService;

  @PostMapping
  public ResponseEntity<PostSongResponse> createSong(@Valid @RequestBody SongDto songDto) {
    Long id = songService.createSong(songDto);
    return ResponseEntity.ok(new PostSongResponse(id));
  }

  @GetMapping("/{id}")
  public ResponseEntity<SongDto> getSong(@PathVariable String id) {
    SongDto song = songService.getSong(id);
    return ResponseEntity.ok(song);
  }

  @DeleteMapping
  public ResponseEntity<DeleteSongsResponse> deleteSongs(@RequestParam(name="id") String idsCsv) {
    List<Long> deletedIds = songService.deleteSongs(idsCsv);
    return ResponseEntity.ok(new DeleteSongsResponse(deletedIds));
  }

}

