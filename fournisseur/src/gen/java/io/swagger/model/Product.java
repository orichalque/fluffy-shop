package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;




/**
 * Product
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2016-10-25T11:38:51.795Z")
public class Product   {
  private String id = null;

  private String name = null;

  private String descripion = null;

  private Double price = null;

  private BigDecimal quantity = null;

  public Product id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Unique identifier representing a specific product in the database.
   * @return id
  **/
  @ApiModelProperty(value = "Unique identifier representing a specific product in the database.")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Product name(String name) {
    this.name = name;
    return this;
  }

   /**
   * The name of the product.
   * @return name
  **/
  @ApiModelProperty(value = "The name of the product.")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Product descripion(String descripion) {
    this.descripion = descripion;
    return this;
  }

   /**
   * A description of the product in the repository. (Size, shape ...)
   * @return descripion
  **/
  @ApiModelProperty(value = "A description of the product in the repository. (Size, shape ...)")
  public String getDescripion() {
    return descripion;
  }

  public void setDescripion(String descripion) {
    this.descripion = descripion;
  }

  public Product price(Double price) {
    this.price = price;
    return this;
  }

   /**
   * The price of the item.
   * @return price
  **/
  @ApiModelProperty(value = "The price of the item.")
  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Product quantity(BigDecimal quantity) {
    this.quantity = quantity;
    return this;
  }

   /**
   * The amount of the item in the warehouse.
   * @return quantity
  **/
  @ApiModelProperty(value = "The amount of the item in the warehouse.")
  public BigDecimal getQuantity() {
    return quantity;
  }

  public void setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Product product = (Product) o;
    return Objects.equals(this.id, product.id) &&
        Objects.equals(this.name, product.name) &&
        Objects.equals(this.descripion, product.descripion) &&
        Objects.equals(this.price, product.price) &&
        Objects.equals(this.quantity, product.quantity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, descripion, price, quantity);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Product {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    descripion: ").append(toIndentedString(descripion)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

