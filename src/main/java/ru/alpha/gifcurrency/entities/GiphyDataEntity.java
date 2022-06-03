package ru.alpha.gifcurrency.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class GiphyDataEntity {
    private Map[] data;
    private Map pagination;
    private Map meta;
}
