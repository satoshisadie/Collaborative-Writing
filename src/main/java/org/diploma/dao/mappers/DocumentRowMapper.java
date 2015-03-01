package org.diploma.dao.mappers;

import org.diploma.model.Document;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DocumentRowMapper implements RowMapper<Document> {
    @Override
    public Document mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Document document = new Document();

        document.setId(rs.getInt("id"));
        document.setAuthorId(rs.getInt("authorId"));
        document.setTitle(rs.getNString("title"));
        document.setDescription(rs.getNString("description"));
        document.setCreatedDate(rs.getDate("createdDate"));
        document.setContent(rs.getNString("content"));

        return document;
    }
}
