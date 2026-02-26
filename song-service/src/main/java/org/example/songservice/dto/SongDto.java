package org.example.songservice.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongDto {

  @NotNull(message = "ID is required")
  private Long id;

  @NotBlank(message = "Song name is required")
  @Size(min = 1, max = 100, message = "Song name must be between 1 and 100 characters")
  private String name;

  @NotBlank(message = "Artist name is required")
  @Size(min = 1, max = 100, message = "Artist name must be between 1 and 100 characters")
  private String artist;

  @NotBlank(message = "Album name is required")
  @Size(min = 1, max = 100, message = "Album name must be between 1 and 100 characters")
  private String album;

  @NotNull(message = "Duration is required")
  @Pattern(regexp = "^\\d{2}:[0-5][0-9]$", message = "Duration must be in mm:ss format with leading zeros")
  private String duration;

  @NotNull(message = "Year is required")
  @Pattern(regexp = "^(19|20)\\d{2}$", message = "Year must be between 1900 and 2099")
  private String year;
}

