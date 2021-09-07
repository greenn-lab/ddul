package io.github.greennlab.ddul.authority.repository;

import static io.github.greennlab.ddul.authority.QMappedAuthorityMenu.mappedAuthorityMenu;

import com.google.common.collect.Sets;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.greennlab.ddul.authority.Authority;
import io.github.greennlab.ddul.authority.MappedAuthorityMenu;
import io.github.greennlab.ddul.authority.QAuthority;
import io.github.greennlab.ddul.authority.QMappedAuthorityMenu;
import io.github.greennlab.ddul.menu.Menu;
import io.github.greennlab.ddul.menu.QMenu;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DDulMappedAuthorityMenuQuerydslRepositoryImpl
    implements DDulMappedAuthorityMenuQuerydslRepository {

  private static final QMappedAuthorityMenu mapped = mappedAuthorityMenu;
  private static final QAuthority authority = mappedAuthorityMenu.authority;
  private static final QMenu menu = mappedAuthorityMenu.menu;


  private final JPAQueryFactory queryFactory;

  private final QBean<MappedAuthorityMenu> columns = Projections.bean(
      MappedAuthorityMenu.class,
      mapped.id,
      Projections.bean(Authority.class,
          authority.id,
          authority.role
      ).as("authority"),
      Projections.bean(Menu.class,
          menu.id,
          menu.uri
      ).as("menu")
  );


  @Override
  public Set<MappedAuthorityMenu> findAll() {
    return Sets.newHashSet(queryFactory
        .select(columns)
        .from(mapped)
        .leftJoin(authority).on(authority.removal.eq(false))
        .leftJoin(menu).on(menu.removal.eq(false))
        .where(mapped.removal.eq(false))
        .fetch());
  }

}
