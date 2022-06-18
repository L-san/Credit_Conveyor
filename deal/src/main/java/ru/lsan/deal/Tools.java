package ru.lsan.deal;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Tools {

    public static <T> String asJsonString(T t) {
        try {
            return new ObjectMapper().writeValueAsString(t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
