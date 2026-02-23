package org.example.resourceservice.client;

import org.example.resourceservice.dto.SongMetadataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SongServiceClient {

  @Autowired
  private RestTemplate restTemplate;

  @Value("${song.service.url}")
  private String songServiceUrl;

  public void createSongMetadata(SongMetadataDto metadata) {
    try {
      restTemplate.postForEntity(
          songServiceUrl + "/songs",
          metadata,
          Object.class
      );
    } catch (Exception e) {
      System.err.println("Failed to send metadata to Song Service: " + e.getMessage());
    }
  }

  public void deleteSongMetadata(Long id) {
    try {
      String url = songServiceUrl + "/songs?id={id}";
      restTemplate.delete(url, id);
    } catch (Exception e) {
      System.err.println("Failed to delete metadata in Song Service for ID " + id + ": " + e.getMessage());
    }
  }
}

