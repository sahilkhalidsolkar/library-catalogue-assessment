package com.example.libraryBookCatalogue.exception;

import lombok.Data;

import java.util.Map;

@Data
public class Errorobj {
    Map<String,String> errors;
}
