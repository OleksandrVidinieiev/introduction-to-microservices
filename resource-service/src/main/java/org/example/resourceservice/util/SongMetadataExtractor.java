package org.example.resourceservice.util;

import java.io.ByteArrayInputStream;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.example.resourceservice.dto.SongMetadataDto;
import org.example.resourceservice.exception.InvalidMp3Exception;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SongMetadataExtractor {

  public static SongMetadataDto extractMetadata(byte[] audioData) {
    if (audioData == null || audioData.length == 0) {
      throw new InvalidMp3Exception("The request body is invalid MP3");
    }

    try {
      BodyContentHandler handler = new BodyContentHandler();
      Metadata metadata = new Metadata();
      ByteArrayInputStream inputStream = new ByteArrayInputStream(audioData);
      ParseContext parseContext = new ParseContext();

      Parser parser = new Mp3Parser();
      parser.parse(inputStream, handler, metadata, parseContext);

      // Extract metadata fields
      String title = metadata.get("dc:title");
      String artist = metadata.get("xmpDM:artist");
      String album = metadata.get("xmpDM:album");
      String duration = metadata.get("xmpDM:duration");
      String year = metadata.get("xmpDM:releaseDate");
      String formattedDuration = formatDuration(duration);

      SongMetadataDto dto = new SongMetadataDto();
      dto.setName(title);
      dto.setArtist(artist);
      dto.setAlbum(album);
      dto.setDuration(formattedDuration);
      dto.setYear(year);

      return dto;

    } catch (Exception e) {
      throw new InvalidMp3Exception("The request body is invalid MP3");
    }
  }

  private String formatDuration(String durationInSeconds) {
    if (durationInSeconds == null || durationInSeconds.isEmpty()) {
      return "00:00";
    }

    try {
      double totalSeconds = Double.parseDouble(durationInSeconds);
      int minutes = (int) (totalSeconds / 60);
      int seconds = (int) (totalSeconds % 60);
      return String.format("%02d:%02d", minutes, seconds);
    } catch (NumberFormatException e) {
      return "00:00";
    }
  }

}
