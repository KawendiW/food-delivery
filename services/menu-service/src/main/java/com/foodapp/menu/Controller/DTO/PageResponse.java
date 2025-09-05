package com.foodapp.menu.Controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PageResponse<T> {

    private List<T> content;

    private long total;

    private int page;

    private int size;

    boolean hasNext;

}
