package com.github.greennlab.ddul.board;

import com.github.greennlab.ddul.entity.Auditor;
import com.github.greennlab.ddul.file.File;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class BoardContent extends Auditor {

  private static final long serialVersionUID = 954534381701799419L;


  @Id
  private Long id;

  private String content;

  @OneToMany
  @JoinColumn(name = "GRP")
  private List<File> attachFiles = new ArrayList<>();

}
