package com.ex01.basic.exception.post;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalPostExceptionHandler {
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ProblemDetail> notFoundHandler(
        MemberNotFoundException memberNotFoundException){

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("사용자 없음");
        problemDetail.setDetail(memberNotFoundException.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(problemDetail);
    }
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ProblemDetail> postNotFoundHandler(
           PostNotFoundException postNotFoundException){

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("게시글 없음");
        problemDetail.setDetail(postNotFoundException.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(problemDetail);
    }
    @ExceptionHandler(PostAccessDeniedException.class)
    public ResponseEntity<ProblemDetail> postAccessDeniedHandler(
            PostAccessDeniedException postAccessDeniedException){

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        problemDetail.setTitle("게시글 권한");
        problemDetail.setDetail(postAccessDeniedException.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(problemDetail);
    }
}
