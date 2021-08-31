package io.github.greennlab.ddul.file.web;

import io.github.greennlab.ddul.Application;
import io.github.greennlab.ddul.file.File;
import io.github.greennlab.ddul.file.service.FileService;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequestMapping("/_file")
@RequiredArgsConstructor
public class DDulFileController {

  public static final String HEADER_CONTENT_LENGTH = "Content-Length";
  public static final ClassPathResource NO_IMAGE
      = new ClassPathResource(Application.PACKAGE.replace('.', '/') + "/images/no-image.png");


  private final FileService service;


  @GetMapping("/{id}")
  public void download(@PathVariable String id, HttpServletResponse response) throws IOException {
    final File file = service.getFile(id);

    response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
    response.setHeader(HEADER_CONTENT_LENGTH, Long.toString(file.getSize()));
    response.setHeader("Content-Disposition", "attachment; filename=" +
        URLEncoder.encode(file.getName(), StandardCharsets.UTF_8.toString()));

    try (
        final InputStream in =
            Files.newInputStream(service.getStoredPath(file.getPath()))
    ) {
      FileCopyUtils.copy(in, response.getOutputStream());
    }

    service.addAccessCount(id);
  }

  @GetMapping(params = "group")
  public List<File> downloadByGroup(String group) {
    return service.getFilesByGroup(group);
  }


  @GetMapping("/info/{id}")
  public File download(@PathVariable String id) {
    return service.getFile(id);
  }

  @GetMapping("/media/{id}")
  public void image(@PathVariable String id, HttpServletResponse response) throws IOException {
    try {
      final File file = service.getFile(id);
      response.setContentType(file.getMime());
      response.setHeader(HEADER_CONTENT_LENGTH, Long.toString(file.getSize()));

      try (final InputStream in = Files.newInputStream(service.getStoredPath(file.getPath()))) {
        FileCopyUtils.copy(in, response.getOutputStream());
      }

      service.addAccessCount(id);
    } catch (Exception e) {
      response.setContentType(MediaType.IMAGE_PNG_VALUE);

      try (final InputStream in = NO_IMAGE.getInputStream()) {
        response.setHeader(HEADER_CONTENT_LENGTH, Long.toString(in.available()));
        FileCopyUtils.copy(in, response.getOutputStream());
      }
    }
  }

  @PostMapping
  public Map<String, Object> upload(HttpServletRequest request) throws IOException {
    MultipartHttpServletRequest multipartRequest
        = (MultipartHttpServletRequest) request;

    final Map<String, Object> result = new HashMap<>();

    final MultiValueMap<String, MultipartFile> multipartFiles =
        multipartRequest.getMultiFileMap();

    for (Map.Entry<String, List<MultipartFile>> entry : multipartFiles.entrySet()) {
      final List<MultipartFile> files = entry.getValue();
      final List<File> storedList = new ArrayList<>(files.size());

      for (MultipartFile multipartFile : files) {
        storedList.add(service.save(multipartFile));
      }

      result.put(entry.getKey(), 1 == storedList.size() ? storedList.get(0) : storedList);
    }

    return result;
  }

  @PostMapping("/image-from-editor")
  public Map<String, Object> uploadImage(@RequestPart MultipartFile upload) throws IOException {
    final File file = service.save(upload);

    final Map<String, Object> result = new HashMap<>(3);
    result.put("uploaded", 1);
    result.put("fileName", file.getName());
    result.put("url", "/_file/media/" + file.getId());

    return result;
  }

  @DeleteMapping("/{id}")
  public File deleteFile(@PathVariable String id) {
    return service.delete(id);
  }

}
