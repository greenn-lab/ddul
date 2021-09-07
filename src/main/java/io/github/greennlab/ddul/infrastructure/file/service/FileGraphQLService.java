package io.github.greennlab.ddul.infrastructure.file.service;

import graphql.kickstart.tools.GraphQLQueryResolver;
import io.github.greennlab.ddul.infrastructure.file.File;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileGraphQLService implements GraphQLQueryResolver {

  private final FileService service;

  public File file(String id) {
    return service.getFile(id);
  }

}
