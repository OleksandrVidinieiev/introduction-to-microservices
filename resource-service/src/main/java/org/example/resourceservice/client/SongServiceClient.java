package org.example.resourceservice.client;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.example.resourceservice.dto.SongMetadataDto;
import org.example.resourceservice.exception.SongServiceException;
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
      String errorMessage = "Failed to create metadata for resource " + metadata.getId();
      System.err.println(errorMessage);
      throw new SongServiceException(errorMessage, e);
    }
  }

  public void deleteSongMetadata(Collection<Long> ids) {
    try {
      String idsCsv = StringUtils.join(ids, ',');
      String url = songServiceUrl + "/songs?id={id}";
      restTemplate.delete(url, idsCsv);
    } catch (Exception e) {
      String errorMessage = "Failed to delete metadata for resources with IDs: " + ids;
      System.err.println(errorMessage);
      throw new SongServiceException(errorMessage, e);
    }
  }
}

