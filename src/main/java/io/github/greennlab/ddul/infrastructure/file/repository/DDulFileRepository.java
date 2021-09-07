package io.github.greennlab.ddul.infrastructure.file.repository;


import io.github.greennlab.ddul.infrastructure.file.File;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DDulFileRepository extends CrudRepository<File, String> {

  @Modifying
  @Query("UPDATE File SET read = read + 1 WHERE id = :id")
  void addAccessCount(@Param("id") String id);

}
