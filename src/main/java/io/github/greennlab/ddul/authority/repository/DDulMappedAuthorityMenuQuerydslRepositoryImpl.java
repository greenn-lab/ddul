package io.github.greennlab.ddul.authority.repository;

import static io.github.greennlab.ddul.authority.QMappedAuthorityMenu.mappedAuthorityMenu;

import com.google.common.collect.Sets;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.greennlab.ddul.authority.Authority;
import io.github.greennlab.ddul.authority.MappedAuthorityMenu;
import io.github.greennlab.ddul.authority.QMappedAuthorityMenu;
import io.github.greennlab.ddul.menu.Menu;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DDulMappedAuthorityMenuQuerydslRepositoryImpl
    implements DDulMappedAuthorityMenuQuerydslRepository {

  private static final QMappedAuthorityMenu mam = mappedAuthorityMenu;


  private final JPAQueryFactory queryFactory;

  private final QBean<MappedAuthorityMenu> columns = Projections.bean(
      MappedAuthorityMenu.class,
      mam.id,
      Projections.bean(Authority.class, mam.authority.role).as("a"),
      Projections.bean(Menu.class, mam.menu.uri).as("m")
  );


  @Override
  public Set<MappedAuthorityMenu> findAll() {
    return Sets.newHashSet(queryFactory
        .select(columns)
        .from(mam)
        .leftJoin(mam.authority).on(mam.authority.removal.eq(false))
        .leftJoin(mam.menu).on(mam.menu.removal.eq(false))
        .where(mam.removal.eq(false))
        .fetch());
  }
}
