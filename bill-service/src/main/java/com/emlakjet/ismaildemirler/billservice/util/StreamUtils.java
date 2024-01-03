package com.emlakjet.ismaildemirler.billservice.util;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class StreamUtils {
    public static <T> Stream<T> collectionStream(Collection<T> collection) {
        return collection == null || collection.isEmpty() ? Stream.empty() : collection.stream();
    }
    
    public static <T> String getStringFromStream(Collection<T> collection) {
        return collection == null || collection.isEmpty() ? "" : Stream.of(collection).collect(Collectors.toList()).toString();
    }
    
    public static <T> String getStringFromStream(T[] collection) {
        return collection == null || collection.length == 0 ? "" : Stream.of(collection).collect(Collectors.toList()).toString();
    }
}