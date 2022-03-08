package com.tabeldata.bootcamp.web.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class Category {

    private Integer category_id;
    private Integer department_id;

    @NotEmpty(message = "Gaboleh Kosong")
    @Length(min = 4, message = "Panjang Minimal 4")
    private String name;

    @NotEmpty(message = "Not Empty")
    @Length(min = 6, message = "Panjang Minimal 6")
    private String description;
}
