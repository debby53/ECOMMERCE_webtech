package rw.ac.auca.ecommerce.controller.product;

import rw.ac.auca.ecommerce.core.util.product.EStockState;
import java.time.LocalDate;

public class ProductFormDTO {
    private String productName;
    private String description;
    private Double price;
    private LocalDate manufacturedDate;
    private LocalDate expirationDate;
    private EStockState stockState;

    // Getters and setters for all fields
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public LocalDate getManufacturedDate() { return manufacturedDate; }
    public void setManufacturedDate(LocalDate manufacturedDate) { this.manufacturedDate = manufacturedDate; }

    public LocalDate getExpirationDate() { return expirationDate; }
    public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }

    public EStockState getStockState() { return stockState; }
    public void setStockState(EStockState stockState) { this.stockState = stockState; }
}
