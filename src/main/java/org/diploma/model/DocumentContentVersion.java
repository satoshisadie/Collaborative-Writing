package org.diploma.model;

import java.util.Date;

public class DocumentContentVersion {
    private int id;
    private int previousVersionId;
    private long documentId;
    private String content;
    private Date createdDate;
    private String difference;


}
