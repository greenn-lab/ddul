package io.github.greennlab.ddul.infrastructure.file.service;

import io.github.greennlab.ddul.infrastructure.file.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

  default Path newStoragePath() throws IOException {
    final String id = UUID.randomUUID().toString();
    return newStoragePath(id);
  }

  File getFile(String id);

  Path getStoredPath(String path) throws FileNotFoundException;

  Path newStoragePath(String fileName) throws IOException;

  File save(MultipartFile multipartFile) throws IOException;

  File delete(String id);

  void addAccessCount(String id);

}
