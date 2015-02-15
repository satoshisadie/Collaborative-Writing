package org.diploma.controllers.client.model;

public class DocumentSaveForm {
    private long documentId;
    private String content;

    public long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(long documentId) {
        this.documentId = documentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
