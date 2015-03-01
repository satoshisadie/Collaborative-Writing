package org.diploma.dao;

import org.diploma.controllers.client.model.DocumentCreateForm;
import org.diploma.controllers.client.model.DocumentSaveForm;
import org.diploma.dao.mappers.DocumentRowMapper;
import org.diploma.model.Document;
import org.diploma.utils.CommonUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
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
                        "d.title," +
                        "d.description," +
                        "d.createdDate," +
                        "cv.content " +
                "FROM document d " +
                "JOIN contentVersion cv ON cv.id = (SELECT TOP 1 cv2.id " +
                                                   "FROM contentVersion cv2 " +
                                                   "WHERE cv2.documentId = d.id " +
                                                   "ORDER BY cv2.createdDate DESC) " +
                "WHERE d.id = ?;";

        return CommonUtils.selectOne(jdbcTemplate, sql, new DocumentRowMapper(), id);
    }

    @Override
    public List<Document> getDocuments() {
        final String sql =
                "SELECT " +
                        "d.id," +
                        "d.authorId," +
                        "d.title," +
                        "d.description," +
                        "d.createdDate," +
                        "cv.content " +
                "FROM document d " +
                "JOIN contentVersion cv ON cv.id = (SELECT TOP 1 cv2.id " +
                                                   "FROM contentVersion cv2 " +
                                                   "WHERE cv2.documentId = d.id " +
                                                   "ORDER BY cv2.createdDate DESC);";

        return jdbcTemplate.query(sql, new DocumentRowMapper());
    }

    @Override
    public int createDocument(DocumentCreateForm form) {
        final String sql =
                "INSERT INTO document(authorId, title, description, createdDate) " +
                "VALUES(1, ?, ?, GETDATE())";

        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    final PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});

                    preparedStatement.setString(1, form.getTitle());
                    preparedStatement.setString(2, form.getDescription());

                    return preparedStatement;
                },
                keyHolder
        );

        final int documentId = keyHolder.getKey().intValue();

        final String sql2 =
                "INSERT INTO contentVersion(" +
                        "previousVersionId," +
                        "documentId," +
                        "content," +
                        "createdDate," +
                        "difference" +
                ") VALUES(?, ?, ?, GETDATE(), ?)";

        jdbcTemplate.update(
                sql2,
                null,
                documentId,
                "Welcome to your new document!\n\n" +
                "Brush up on Markdown: http://daringfireball.net/projects/markdown/basics",
                ""
        );

        return documentId;
    }

    @Override
    public void saveDocument(DocumentSaveForm form) {
        final String sql1 =
                "MERGE document AS target " +
                "USING (VALUES(?, ?, ?)) AS source (id, authorId, createdDate) " +
                        "ON target.id = source.id " +
                "WHEN NOT MATCHED BY target THEN " +
                        "INSERT (id, authorId, createdDate) VALUES (id, authorId, createdDate);";

        jdbcTemplate.update(
                sql1,
                form.getId(),
                1,
                new Date()
        );

        final String sql2 =
                "INSERT INTO contentVersion(" +
                        "previousVersionId," +
                        "documentId," +
                        "content," +
                        "createdDate," +
                        "difference" +
                ") VALUES (?, ?, ?, GETDATE(), ?)";

        jdbcTemplate.update(
                sql2,
                null,
                form.getId(),
                form.getContent(),
                ""
        );
    }
}
