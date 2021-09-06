package io.github.greennlab.ddul.authority.repository;

import static io.github.greennlab.ddul.authority.QMapAuthorityMenu.mapAuthorityMenu;

import com.google.common.collect.Sets;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.greennlab.ddul.authority.Authority;
import io.github.greennlab.ddul.authority.MapAuthorityMenu;
import io.github.greennlab.ddul.menu.Menu;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DDulMapAuthorityMenuQuerydslRepositoryImpl implements
    DDulMapAuthorityMenuQuerydslRepository {

  public static final QBean<MapAuthorityMenu> columns = Projections.bean(
      MapAuthorityMenu.class,
      mapAuthorityMenu.id,
      Projections.bean(Authority.class, mapAuthorityMenu.authority.role)
          .as("a"),
      Projections.bean(Menu.class, mapAuthorityMenu.menu.uri)
          .as("m")
  );


  private final JPAQueryFactory queryFactory;


  @Override
  public Set<MapAuthorityMenu> findAll() {
    return Sets.newHashSet(queryFactory
        .select(columns)
        .from(mapAuthorityMenu)
        .leftJoin(mapAuthorityMenu.authority).on(mapAuthorityMenu.authority.removal.eq(false))
        .leftJoin(mapAuthorityMenu.menu).on(mapAuthorityMenu.menu.removal.eq(false))
        .where(mapAuthorityMenu.removal.eq(false))
        .fetch());
  }
}
