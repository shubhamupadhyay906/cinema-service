package com.mindtree.common;


public class CommonConstant {
    private CommonConstant() {
        throw new IllegalStateException("Utility class");
    }

    public static final String SUCCESS_DELETED = "Record has been successfully deleted!!!!";
    public static final String RECORD_NOT_FOUND = "Record has been not found!!!!.";

    public static final String RECORD_NOT_FOUND_EXCEPTION = "ResourceNotFoundException: {}";
    public static final String EXCEPTION = "Exception: {}";

    public static final String ID_LOG = "Id : {}";
}
