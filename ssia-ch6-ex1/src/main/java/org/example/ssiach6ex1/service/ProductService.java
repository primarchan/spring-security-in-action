package org.example.ssiach6ex1.service;

import lombok.RequiredArgsConstructor;
import org.example.ssiach6ex1.domain.Product;
import org.example.ssiach6ex1.domain.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

}
