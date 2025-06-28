package rw.ac.auca.ecommerce.controller.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.ecommerce.core.product.model.Product;
import rw.ac.auca.ecommerce.core.product.service.IProductService;
import rw.ac.auca.ecommerce.core.util.product.EStockState;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final IProductService productService;

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("productForm", new ProductFormDTO());
        return "customer/product";  // Thymeleaf template name
    }

    @PostMapping("/create")
    public String saveProduct(@ModelAttribute("productForm") ProductFormDTO productForm, Model model) {
        Product product = new Product();
        product.setProductName(productForm.getProductName());
        product.setDescription(productForm.getDescription());
        product.setPrice(productForm.getPrice());
        product.setManufacturedDate(productForm.getManufacturedDate());
        product.setExpirationDate(productForm.getExpirationDate());
        product.setStockState(productForm.getStockState());
        product.setActive(true);

        productService.createProduct(product);

        model.addAttribute("message", "Product saved successfully!");
        model.addAttribute("productForm", new ProductFormDTO()); // reset form
        return "customer/product";
    }

}
