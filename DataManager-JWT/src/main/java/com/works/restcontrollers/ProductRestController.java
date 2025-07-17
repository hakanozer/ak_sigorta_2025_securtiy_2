package com.works.restcontrollers;

import com.works.entities.Product;
import com.works.entities.dtos.ProductSaveDto;
import com.works.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductRestController {

    final ProductService productService;

    @PostMapping("save")
    public Product save(@Valid @RequestBody ProductSaveDto productSaveDto) {
        return productService.save(productSaveDto);
    }

    @GetMapping("list")
    public List<Product> list() {
        return productService.findAll();
    }

}
