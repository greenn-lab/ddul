package io.github.greennlab.ddul.team.repository;

import io.github.greennlab.ddul.team.TeamHierarchy;
import java.util.List;
import org.springframework.data.repository.Repository;

public interface TeamHierarchyRepository extends Repository<TeamHierarchy, Long> {

  TeamHierarchy findById(Long id);

  List<TeamHierarchy> findAllByPidIsNullOrderByOrder();

}
