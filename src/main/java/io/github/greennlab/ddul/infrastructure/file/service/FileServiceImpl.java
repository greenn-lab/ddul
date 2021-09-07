package io.github.greennlab.ddul.infrastructure.file.service;

import io.github.greennlab.ddul.DDulProperties;
import io.github.greennlab.ddul.infrastructure.file.File;
import io.github.greennlab.ddul.infrastructure.file.repository.DDulFileRepository;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

@Service("DDulFileService")
@Transactional
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

  private static final DateTimeFormatter DAILY_DIR = DateTimeFormatter.ofPattern("yyyyMMdd");


  private final DDulFileRepository repository;

  private final DDulProperties properties;

  private Path fileStorage;


  @PostConstruct
  public void setup() throws IOException {
    fileStorage = properties.getFileStorage().toAbsolutePath();

    if (Files.notExists(fileStorage)) {
      Files.createDirectories(fileStorage);
    }
  }

  @Override
  public Path getStoredPath(String path) throws FileNotFoundException {
    final Path stored = Paths.get(fileStorage.toString(), path);
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
  public Path newStoragePath(String id) throws IOException {
    final String date = DAILY_DIR.format(LocalDate.now());

    Path directory = fileStorage.resolve(date);

    if (Files.notExists(directory)) {
      directory = Files.createDirectories(directory);
    }

    return directory.resolve(id);
  }

  @Override
  public File save(MultipartFile multipartFile) throws IOException {
    final String id = UUID.randomUUID().toString();

    final Path path = newStoragePath(id);
    final File file = new File();
    file.setId(id);
    file.setName(multipartFile.getOriginalFilename());
    file.setPath(path.toString());
    file.setType(multipartFile.getContentType());
    file.setSize(multipartFile.getSize());

    final File save = repository.save(file);
    save.setPath(changeRelativePath(path));

    FileCopyUtils.copy(multipartFile.getInputStream(), Files.newOutputStream(path));

    return save;
  }

  @Async("asyncThreadExecutor")
  @Override
  public void addAccessCount(String id) {
    repository.addAccessCount(id);
  }

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
        .replace(fileStorage.toString(), "");
  }

}
