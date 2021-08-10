package com.github.greennlab.ddul.board;

import com.github.greennlab.ddul.board.BoardDTO.BoardMapper;
import com.github.greennlab.ddul.entity.BaseEntity;
import com.github.greennlab.ddul.file.File;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.mapstruct.factory.Mappers;

@Entity
@Table(name = "BOARD")
@Getter
@Setter
public class Board extends BaseEntity {

  public static final BoardMapper mapped = Mappers.getMapper(BoardMapper.class);

  private static final long serialVersionUID = 5317379996515377215L;


  @Column(name = "GRP")
  private String group;

  private Long upperId;
  private String title;

  @Embedded
  private BoardAuthor author = new BoardAuthor();

  private boolean secret;

  @Column(name = "ACS_COUNT")
  private int accessCount;

  @OneToOne(mappedBy = "board", fetch = FetchType.LAZY, optional = false)
  @PrimaryKeyJoinColumn
  private BoardContent content;

  @OneToMany
  @JoinColumn(name = "GRP")
  @Where(clause = "DELETED IS NULL")
  private List<File> attachFiles = new ArrayList<>();

}
