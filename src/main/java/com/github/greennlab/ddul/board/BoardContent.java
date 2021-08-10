package com.github.greennlab.ddul.board;

import com.github.greennlab.ddul.entity.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "BOARD_CONTENT")
@Getter
@Setter
public class BoardContent extends BaseEntity {

  private static final long serialVersionUID = 954534381701799419L;


  @Id
  private Long id;

  @OneToOne
  @MapsId
  @JoinColumn(name = "ID")
  private Board board;

  @Lob
  private String body;

  @Lob
  @Getter(AccessLevel.NONE)
  private String text;


  @PrePersist
  @PreUpdate
  public void removeHtmlTags() {
    text = body.replaceAll("<[^>]*>", "");
  }

}
