package com.works.services;

import com.works.entities.Product;
import com.works.entities.dtos.ProductSaveDto;
import com.works.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public Product save(ProductSaveDto productSaveDto) {
        Product product = modelMapper.map(productSaveDto, Product.class);
        return productRepository.save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }


}
