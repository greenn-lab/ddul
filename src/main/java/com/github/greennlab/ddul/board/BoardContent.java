package com.github.greennlab.ddul.board;

import com.github.greennlab.ddul.entity.Auditor;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "BOARD_CONTENT")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class BoardContent extends Auditor {

  private static final long serialVersionUID = 954534381701799419L;


  @Id
  @GeneratedValue
  private Long id;

  @OneToOne
  @PrimaryKeyJoinColumn(name = "ID", referencedColumnName = "ID")
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
