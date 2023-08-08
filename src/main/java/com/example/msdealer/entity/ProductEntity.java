package com.example.msdealer.entity;

@Entity
@Table(name = "products")
public class ProductEntity {
  @Id
  Long id;
  String name;
  String brand;
  String colour;
  String size;
  String description;
  Long dealerId;
  Long category_id;
  int quantity;
  
}
