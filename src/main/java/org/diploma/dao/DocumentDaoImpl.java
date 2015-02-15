package org.diploma.dao;

import org.diploma.controllers.client.model.DocumentSaveForm;
import org.diploma.dao.mappers.DocumentRowMapper;
import org.diploma.model.Document;
import org.diploma.utils.CommonUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.Optional;

public class DocumentDaoImpl implements DocumentDao {
    private JdbcTemplate jdbcTemplate;

    public DocumentDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Document> getDocumentById(long id) {
        final String sql =
                "SELECT " +
                        "d.id," +
                        "d.authorId," +
                        "d.createdDate," +
                        "dcv.content" +
                "FROM document d " +
                "JOIN documentContentVersion dcv ON dcv.documentId = (SELECT TOP 1 dcv2.id " +
                                                                     "FROM documentContentVersion dcv2 " +
                                                                     "WHERE dcv2.documentId = d.id " +
                                                                     "ORDER BY dcv2.dateCreated DESC) " +
                "WHERE d.id = ?;";

        return CommonUtils.selectOne(jdbcTemplate, sql, new DocumentRowMapper(), id);
    }

    @Override
    public void saveDocument(DocumentSaveForm form) {
        final String sql1 =
                "MERGE document AS target " +
                "USING (VALUES(?, ?, ?)) AS source (id, authorId, createdDate) " +
                        "ON target.id = source.id " +
                "WHEN NOT MATCHED BY target THEN " +
                        "INSERT (id, authorId, createdDate) VALUES (id, authorId, createdDate);";

        final long documentId = CommonUtils.generateId();
        jdbcTemplate.update(
                sql1,
                documentId,
                1,
                new Date()
        );

        final String sql2 =
                "INSERT INTO documentContentVersion(" +
                        "id," +
                        "previousVersionId," +
                        "documentId," +
                        "content," +
                        "createdDate," +
                        "difference" +
                ") VALUES (?, ?, ?, ?, GETDATE(), ?)";

        jdbcTemplate.update(
                sql2,
                2,
                null,
                documentId,
                form.getContent(),
                ""
        );
    }
}
