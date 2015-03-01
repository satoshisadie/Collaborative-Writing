package org.diploma.utils;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Optional;
import java.util.UUID;

public class CommonUtils {

    public static ObjectNode prepareSuccessResponse() {
        final JsonNodeFactory jsonNodeFactory = JsonNodeFactory.instance;
        final ObjectNode responseObjectNode = jsonNodeFactory.objectNode();

        responseObjectNode.put("status", "success");
        final ObjectNode dataObjectNode = jsonNodeFactory.objectNode();
        responseObjectNode.set("data", dataObjectNode);

        return responseObjectNode;
    }

    public static ObjectNode prepareErrorResponse(String message) {
        final JsonNodeFactory jsonNodeFactory = JsonNodeFactory.instance;
        final ObjectNode responseObjectNode = jsonNodeFactory.objectNode();

        responseObjectNode.put("status", "error");

        final ObjectNode dataObjectNode = jsonNodeFactory.objectNode();
        dataObjectNode.put("message", message);

        responseObjectNode.set("data", dataObjectNode);

        return responseObjectNode;
    }

    public static long generateId() {
        return Math.abs(UUID.randomUUID().getMostSignificantBits());
    }

    public static <T> Optional<T> selectOne(JdbcTemplate jdbcTemplate,
                                            String sql,
                                            RowMapper<T> rowMapper,
                                            Object ... arguments) {
        try {
            final T object = jdbcTemplate.queryForObject(sql, rowMapper, arguments);
            return Optional.of(object);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }
}
