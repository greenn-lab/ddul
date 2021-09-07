package io.github.greennlab.ddul.authority.repository;

import static io.github.greennlab.ddul.authority.QMappedMenuAuthority.mappedMenuAuthority;

import com.google.common.collect.Sets;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.greennlab.ddul.authority.Authority;
import io.github.greennlab.ddul.authority.MappedMenuAuthority;
import io.github.greennlab.ddul.authority.QAuthority;
import io.github.greennlab.ddul.authority.QMappedMenuAuthority;
import io.github.greennlab.ddul.menu.Menu;
import io.github.greennlab.ddul.menu.QMenu;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DDulMappedMenuAuthorityQuerydslRepositoryImpl
    implements DDulMappedMenuAuthorityQuerydslRepository {

  private static final QMappedMenuAuthority mapped = mappedMenuAuthority;
  private static final QAuthority authority = mappedMenuAuthority.authority;
  private static final QMenu menu = mappedMenuAuthority.menu;


  private final JPAQueryFactory queryFactory;

  private final QBean<MappedMenuAuthority> columns = Projections.bean(
      MappedMenuAuthority.class,
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
  public Set<MappedMenuAuthority> findAll() {
    return Sets.newHashSet(queryFactory
        .select(columns)
        .from(mapped)
        .leftJoin(authority).on(authority.removal.eq(false))
        .leftJoin(menu).on(menu.removal.eq(false))
        .where(mapped.removal.eq(false))
        .fetch());
  }

}
