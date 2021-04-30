package com.example.demo.dto;

import com.example.demo.model.Item;
import lombok.Data;

import java.io.Serializable;

@Data
public class ItemDto implements Serializable {
    private static final long serialVersionUID = -4961283700226852039L;
    private Long id;
    private String description;

    public static ItemDto createInstance(Item item) {
        ItemDto itemDTO = new ItemDto();
        itemDTO.setId(item.getId());
        itemDTO.setDescription(item.getDescription());
        return itemDTO;
    }
}
