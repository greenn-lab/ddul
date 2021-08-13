package com.github.greennlab.ddul.file.repository;


import com.github.greennlab.ddul.file.File;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface FileRepository extends CrudRepository<File, String> {

  @Modifying
  @Query("UPDATE File SET access = access + 1 WHERE id = :id")
  void addAccessCount(@Param("id") String id);

  @Modifying
  @Query("UPDATE File f SET f.pack = :pack WHERE f.id IN (:ids)")
  void updateGroupById(@Param("pack") String pack, @Param("ids") String... ids);

  List<File> findAllByPack(Object pack);
}
