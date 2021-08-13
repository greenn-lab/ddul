package com.github.greennlab.ddul.mybatis.handlers;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

@SuppressWarnings("unused")
public class BooleanToCharYNTypeHandler extends BaseTypeHandler<Boolean> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, Boolean parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setString(i, Boolean.TRUE.equals(parameter) ? "Y" : "N");
  }

  @Override
  public Boolean getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return "Y".equals(rs.getString(columnName)) ? Boolean.TRUE : Boolean.FALSE;
  }

  @Override
  public Boolean getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return "Y".equals(rs.getString(columnIndex)) ? Boolean.TRUE : Boolean.FALSE;
  }

  @Override
  public Boolean getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return "Y".equals(cs.getString(columnIndex)) ? Boolean.TRUE : Boolean.FALSE;
  }

}
