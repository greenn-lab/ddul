package com.github.greennlab.ddul.file.repository;


import com.github.greennlab.ddul.entity.AFewRepository;
import com.github.greennlab.ddul.file.File;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FileRepository extends AFewRepository<File, String> {

  @Modifying
  @Query("UPDATE File SET accessCount = accessCount + 1 WHERE id = :id")
  void addAccessCount(@Param("id") String id);

  List<File> findAllByGroup(String group);
}
