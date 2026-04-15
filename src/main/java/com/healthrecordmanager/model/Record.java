package com.healthrecordmanager.model;

import java.io.Serializable;

public abstract class Record implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String recordId;

    protected Record(String recordId) {
        this.recordId = recordId;
    }

    public String getRecordId() {
        return recordId;
    }

    public abstract String getSummary();
}
