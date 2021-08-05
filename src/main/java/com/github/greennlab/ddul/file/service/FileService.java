package com.github.greennlab.ddul.file.service;

import com.github.greennlab.ddul.file.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

  default Path newStoragePath() throws IOException {
    final String id = UUID.randomUUID().toString();
    return newStoragePath(id);
  }

  File getFile(String id);

  List<File> getFileByGroup(String group);

  Path getStoredPath(String path) throws FileNotFoundException;

  Path newStoragePath(String fileName) throws IOException;

  File save(MultipartFile multipartFile) throws IOException;

  void addAccessCount(String id);

  File delete(String id);
}
