package org.diploma.model;

import java.util.Date;

public class ContentVersion {
    private int id;
    private int previousVersionId;
    private int documentId;
    private String content;
    private Date createdDate;
    private String difference;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPreviousVersionId() {
        return previousVersionId;
    }

    public void setPreviousVersionId(int previousVersionId) {
        this.previousVersionId = previousVersionId;
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getDifference() {
        return difference;
    }

    public void setDifference(String difference) {
        this.difference = difference;
    }
}
