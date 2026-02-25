package org.example.resourceservice.controller;

import java.util.List;

import org.example.resourceservice.dto.DeleteResourceResponse;
import org.example.resourceservice.dto.PostResourceResponse;
import org.example.resourceservice.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resources")
public class ResourceController {

  @Autowired
  private ResourceService resourceService;

  @PostMapping(consumes = "audio/mpeg")
  public ResponseEntity<PostResourceResponse> uploadResource(@RequestBody byte[] audioData) {
    Long id = resourceService.uploadResource(audioData);
    return ResponseEntity.ok(new PostResourceResponse(id));
  }

  @GetMapping("/{id}")
  public ResponseEntity<byte[]> getResource(@PathVariable String id) {
    byte[] audioData = resourceService.getResource(id);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.parseMediaType("audio/mpeg"));

    return ResponseEntity.ok()
      .headers(headers)
      .body(audioData);
  }

  @DeleteMapping
  public ResponseEntity<DeleteResourceResponse> deleteResources(@RequestParam(name="id") String idsCsv) {
    List<Long> deletedIds = resourceService.deleteResources(idsCsv);
    return ResponseEntity.ok(new DeleteResourceResponse(deletedIds));
  }

}

