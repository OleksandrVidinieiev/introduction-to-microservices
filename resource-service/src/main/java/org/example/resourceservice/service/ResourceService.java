package org.example.resourceservice.service;

import static org.example.resourceservice.util.ValidationHelper.parseAndValidateCsv;
import static org.example.resourceservice.util.ValidationHelper.parseAndValidateId;
import static org.example.resourceservice.util.SongMetadataExtractor.extractMetadata;

import java.util.ArrayList;
import java.util.List;

import org.example.resourceservice.client.SongServiceClient;
import org.example.resourceservice.dto.SongMetadataDto;
import org.example.resourceservice.entity.Resource;
import org.example.resourceservice.exception.ResourceNotFoundException;
import org.example.resourceservice.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResourceService {

  @Autowired
  private ResourceRepository resourceRepository;

  @Autowired
  private SongServiceClient songServiceClient;

  @Transactional
  public Long uploadResource(byte[] audioData) {
    SongMetadataDto metadata = extractMetadata(audioData);
    Resource resource = new Resource();
    resource.setData(audioData);
    resource = resourceRepository.save(resource);
    Long resourceId = resource.getId();
    metadata.setId(resourceId);
    songServiceClient.createSongMetadata(metadata);

    return resourceId;
  }

  public byte[] getResource(String id) {
    Resource resource = resourceRepository.findById(parseAndValidateId(id))
      .orElseThrow(() -> new ResourceNotFoundException("Resource with ID=" + id + " not found"));

    return resource.getData();
  }

  @Transactional
  public List<Long> deleteResources(String idsCsv) {
    List<Long> ids = parseAndValidateCsv(idsCsv);
    List<Long> deletedIds = new ArrayList<>();

    for (Long id : ids) {
      if (resourceRepository.existsById(id)) {
        resourceRepository.deleteById(id);
        deletedIds.add(id);
      }
    }

    if (!deletedIds.isEmpty()) {
      songServiceClient.deleteSongMetadata(deletedIds);
    }

    return deletedIds;
  }

}

