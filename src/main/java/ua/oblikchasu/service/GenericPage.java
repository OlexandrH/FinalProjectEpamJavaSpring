package ua.oblikchasu.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class GenericPage<T> implements Page <T> {

    int totalPages;
    List<T> content;

    public GenericPage(int totalPages, List<T> content) {
        this.totalPages = totalPages;
        this.content = content;
    }

    public GenericPage() {
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    @Override
    public int getTotalPages() {
        return totalPages;
    }

    @Override
    public List<T> getContent() {
        return this.content;
    }

    @Override
    public long getTotalElements() {
        return 0;
    }

    @Override
    public Page map(Function converter) {
        return null;
    }

    @Override
    public int getNumber() {
        return 0;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public int getNumberOfElements() {
        return 0;
    }

    @Override
    public boolean hasContent() {
        return false;
    }

    @Override
    public Sort getSort() {
        return null;
    }

    @Override
    public boolean isFirst() {
        return false;
    }

    @Override
    public boolean isLast() {
        return false;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    @Override
    public Pageable nextPageable() {
        return null;
    }

    @Override
    public Pageable previousPageable() {
        return null;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }
}