package com.works.configs;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return parseError(e.getFieldErrors());
    }

    private List<Map<String, Object>> parseError(List<FieldError> fieldErrors) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            Map<String, Object> map = new HashMap<>();
            map.put("field", fieldError.getField());
            map.put("defaultMessage", fieldError.getDefaultMessage());
            map.put("rejectedValue", fieldError.getRejectedValue());
            list.add(map);
        }
        return list;
    }

}
