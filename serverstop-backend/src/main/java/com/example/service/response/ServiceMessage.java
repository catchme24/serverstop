package com.example.service.response;

public enum ServiceMessage {
    SHOULD_HAS_EXISTING_ID,
    SHOULD_NOT_HAS_ID,
    DB_QUERY_TROUBLE,

    AUTH_USER_ID_NOT_EQUAL_TO_SERVER_OWNER_ID,

    ALREADY_EXIST_USERNAME
}
