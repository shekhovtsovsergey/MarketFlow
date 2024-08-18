package ru.shekhovtsov.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
   private Long id;
   private String name;
   private Integer quantity;
   private BigDecimal price;
}