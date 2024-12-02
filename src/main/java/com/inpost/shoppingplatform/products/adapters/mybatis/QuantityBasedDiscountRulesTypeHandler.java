package com.inpost.shoppingplatform.products.adapters.mybatis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inpost.shoppingplatform.products.ports.QuantityBasedDiscount.QuantityBasedDiscountRule;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class QuantityBasedDiscountRulesTypeHandler implements TypeHandler<List<QuantityBasedDiscountRule>> {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void setParameter(PreparedStatement ps, int i, List<QuantityBasedDiscountRule> parameter, JdbcType jdbcType) throws SQLException {
        try {
            ps.setObject(i, objectMapper.writeValueAsString(parameter));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<QuantityBasedDiscountRule> getResult(ResultSet rs, String columnName) throws SQLException {
        try {
            return objectMapper.readValue(rs.getString(columnName), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<QuantityBasedDiscountRule> getResult(ResultSet rs, int columnIndex) throws SQLException {
        try {
            return objectMapper.readValue(rs.getString(columnIndex), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<QuantityBasedDiscountRule> getResult(CallableStatement cs, int columnIndex) throws SQLException {
        try {
            return objectMapper.readValue(cs.getString(columnIndex), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
