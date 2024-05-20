package com.aves.flamingodb.App.core;

import com.aves.flamingodb.App.FlamingoApp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Filter {

    public Filter(Filter filter){

    }

    public static String notInArray(String field, List<?> values) {
        return String.format("%s=%s", field, values);
    }

    public static String inArray(String field, List<?> values) {
        return String.format("%s=%s", field, values);
    }

    public static String arrayContainsAny(String field, List<?> values) {
        return String.format("%s=%s", field, values);
    }

    public static String arrayContains(String field, Object value) {
        return String.format("%s=%s", field, value);
    }

    public static String greaterThanOrEqualTo(String field, Object value) {
        return String.format("%s=%s", field, value);
    }

    public static String greaterThan(String field, Object value) {
        return String.format("%s=%s", field, value);
    }

    public static String lessThanOrEqualTo(String field, Object value) {
        return String.format("%s=%s", field, value);
    }

    public static String lessThan(String field, Object value) {
        return String.format("%s=%s", field, value);
    }

    public static String notEqualTo(String field, Object value) {
        return String.format("%s=%s", field, value);
    }

    public static String equalTo(String field, Object value) {
        return String.format("%s=%s", field, value);
    }

    public ArrayList<?> getFilters(){
        return new ArrayList<>();
    }

}
