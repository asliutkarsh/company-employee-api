package com.utkarsh.companyemployeeapi.exception;

public class ResourceNotFoundException extends RuntimeException {

        public ResourceNotFoundException(String message) {
            super(message);
        }
}
