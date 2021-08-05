package com.github.greennlab.ddul.board;

import com.github.greennlab.ddul.board.BoardDTO.BoardMapper;
import com.github.greennlab.ddul.entity.Auditor;
import com.github.greennlab.ddul.file.File;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.mapstruct.factory.Mappers;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Board extends Auditor {

  public static final BoardMapper mapped = Mappers.getMapper(BoardMapper.class);

  private static final long serialVersionUID = 5317379996515377215L;


  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "GRP")
  private String group;

  private Long upperId;
  private String title;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "name", column = @Column(name = "AU_NAME")),
      @AttributeOverride(name = "email", column = @Column(name = "AU_EMAIL")),
      @AttributeOverride(name = "password", column = @Column(name = "AU_PWD")),
  })
  private BoardAuthor author = new BoardAuthor();

  private boolean secret;

  @Column(name = "ACS_COUNT")
  private int accessCount;

  @OneToOne(mappedBy = "board", optional = false)
  private BoardContent content;

  @OneToMany
  @JoinColumn(name = "GRP")
  @Where(clause = "DELETED IS NULL")
  private List<File> attachFiles = new ArrayList<>();

}
