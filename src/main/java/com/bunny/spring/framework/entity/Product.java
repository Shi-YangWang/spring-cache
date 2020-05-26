package com.bunny.spring.framework.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.lang.Nullable;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Accessors(chain = true)
public class Product {
    @Nullable  Long id;
    String name;
    String description;
    Long price;
}
