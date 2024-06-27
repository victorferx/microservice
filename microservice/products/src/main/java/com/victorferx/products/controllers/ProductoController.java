package com.victorferx.products.controllers;

import com.victorferx.products.ProductsApplication;
import com.victorferx.products.model.dto.ProductRequest;
import com.victorferx.products.model.dto.ProductResponse;
import com.victorferx.products.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAll() {
        return this.productService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody ProductRequest request) {
        this.productService.add(request);
    }
}
