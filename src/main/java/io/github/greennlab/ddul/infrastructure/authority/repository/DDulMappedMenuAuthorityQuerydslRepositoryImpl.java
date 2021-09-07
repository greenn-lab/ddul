package io.github.greennlab.ddul.infrastructure.authority.repository;

import static io.github.greennlab.ddul.infrastructure.authority.QMappedMenuAuthority.mappedMenuAuthority;

import com.google.common.collect.Sets;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.greennlab.ddul.infrastructure.authority.Authority;
import io.github.greennlab.ddul.infrastructure.authority.MappedMenuAuthority;
import io.github.greennlab.ddul.infrastructure.authority.QAuthority;
import io.github.greennlab.ddul.infrastructure.authority.QMappedMenuAuthority;
import io.github.greennlab.ddul.infrastructure.menu.QMenu;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DDulMappedMenuAuthorityQuerydslRepositoryImpl
    implements DDulMappedMenuAuthorityQuerydslRepository {

  private static final QMappedMenuAuthority mapped = mappedMenuAuthority;
  private static final QAuthority authority = mappedMenuAuthority.authority;
  private static final QMenu menu = QMenu.menu;


  private final JPAQueryFactory queryFactory;

  private final QBean<MappedMenuAuthority> columns = Projections.bean(
      MappedMenuAuthority.class,
      mapped.id,
      mapped.menuId,
      Projections.bean(Authority.class,
          authority.id,
          authority.role
      ).as("authority")
  );


  @Override
  public Set<MappedMenuAuthority> findAll() {
    return Sets.newHashSet(queryFactory
        .select(columns)
        .from(mapped)
        .leftJoin(menu).on(mapped.menuId.eq(menu.id))
        .leftJoin(authority)
        .where(menu.removal.eq(false)
            .and(authority.removal.eq(false)))
        .fetch());
  }

}
