package com.codemaster.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Data
@Getter
@Setter
public class ErrorDetails {
    private HttpStatus status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timestamp;
    private String message;
    private List<String> errors;

    public ErrorDetails()
    {
        this.timestamp = LocalDateTime.now();
    }
    public ErrorDetails(HttpStatus status, LocalDateTime timestamp, String message, List<String> errors) {
        this();
        this.status = status;
        this.timestamp = timestamp;
        this.message = message;
        this.errors = errors;
    }
    public ErrorDetails(HttpStatus status, String message, List<String> errors) {
        this();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
    public ErrorDetails(HttpStatus status, String message, String error) {
        this();
        this.status = status;
        this.message = message;
        this.errors = Arrays.asList(error);
    }

    public ErrorDetails(HttpStatus status) {
        this();
        this.status = status;
    }

}
