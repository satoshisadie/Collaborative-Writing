package org.diploma.dao;

import org.diploma.controllers.client.model.DocumentCreateForm;
import org.diploma.controllers.client.model.DocumentSaveForm;
import org.diploma.model.Document;

import java.util.List;
import java.util.Optional;

public interface DocumentDao {

    Optional<Document> getDocumentById(long id);
    List<Document> getDocuments();
    int createDocument(DocumentCreateForm form);
    void saveDocument(DocumentSaveForm form);
}
