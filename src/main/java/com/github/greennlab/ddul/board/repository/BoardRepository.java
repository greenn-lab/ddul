package com.github.greennlab.ddul.board.repository;

import com.github.greennlab.ddul.board.Board;
import com.github.greennlab.ddul.entity.AFewRepository;
import java.util.Optional;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends AFewRepository<Board, Long> {

  @Override
  default Optional<Board> findById(Long aLong) {
    throw new UnsupportedOperationException();
  }

  @Modifying
  @Query("update Board b set b.order = b.order + 1 where b.id <> :id and b.bid = :bid and b.order >= :order")
  void updateReplyOrder(@Param("id") Long id, @Param("bid") Long bid,
      @Param("order") Integer order);

}
