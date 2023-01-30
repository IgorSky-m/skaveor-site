package com.skachko.gateway.foreign.auth;

import java.util.List;
import java.util.UUID;


public class ValidateTokenResult {

    private EValidateTokenResult result;
    private String message;
    private TokenPayload payload;

    public ValidateTokenResult() {
    }

    public EValidateTokenResult getResult() {
        return result;
    }

    public void setResult(EValidateTokenResult result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TokenPayload getPayload() {
        return payload;
    }

    public void setPayload(TokenPayload payload) {
        this.payload = payload;
    }


    public static class TokenPayload {
        private UUID id;
        private Long version;
        private List<String> roles;
        private String name;

        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public Long getVersion() {
            return version;
        }

        public void setVersion(Long version) {
            this.version = version;
        }

        public List<String> getRoles() {
            return roles;
        }

        public void setRoles(List<String> roles) {
            this.roles = roles;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }




    public enum EValidateTokenResult {
        SUCCESS,
        FAIL
    }
}
