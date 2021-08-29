package com.github.greennlab.ddul.file.service;

import com.github.greennlab.ddul.file.File;
import com.github.greennlab.ddul.file.repository.FileRepository;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

  private static final DateTimeFormatter DAILY_DIR = DateTimeFormatter.ofPattern("yyyyMMdd");


  private final FileRepository repository;


  @Value("${file.storage.path}")
  private Path fileStoragePath;


  @PostConstruct
  public void setup() throws IOException {
    fileStoragePath = fileStoragePath.toAbsolutePath();

    if (Files.notExists(fileStoragePath)) {
      Files.createDirectories(fileStoragePath);
    }
  }

  @Override
  public Path getStoredPath(String path) throws FileNotFoundException {
    final Path stored = Paths.get(fileStoragePath.toString(), path);
    if (Files.notExists(stored)) {
      throw new FileNotFoundException();
    }

    return stored;
  }

  @Override
  public File getFile(String id) {
    return repository.findById(id).orElse(null);
  }

  @Override
  public List<File> getFilesByGroup(Object group) {
    return repository.findAllByPack(group.toString());
  }

  @Override
  public Path newStoragePath(String id) throws IOException {
    final String date = DAILY_DIR.format(LocalDate.now());

    Path directory = fileStoragePath.resolve(date);

    if (Files.notExists(directory)) {
      directory = Files.createDirectories(directory);
    }

    return directory.resolve(id);
  }

  @Transactional
  @Override
  public File save(MultipartFile multipartFile) throws IOException {
    final String id = UUID.randomUUID().toString();

    final Path path = newStoragePath(id);
    final File file = new File();
    file.setId(id);
    file.setName(multipartFile.getOriginalFilename());
    file.setPath(path.toString());
    file.setMime(multipartFile.getContentType());
    file.setSize(multipartFile.getSize());

    final File save = repository.save(file);
    save.setPath(changeRelativePath(path));

    FileCopyUtils.copy(multipartFile.getInputStream(), Files.newOutputStream(path));

    return save;
  }

  @Async("asyncThreadExecutor")
  @Transactional
  @Override
  public void addAccessCount(String id) {
    repository.addAccessCount(id);
  }

  @Transactional
  @Override
  public File delete(String id) {
    final File file = getFile(id);

    if (null == file) {
      return null;
    }

    file.setRemoval(true);
    return repository.save(file);
  }

  private String changeRelativePath(Path absolutePath) {
    return absolutePath.toString()
        .replace(fileStoragePath.toString(), "");
  }

}
