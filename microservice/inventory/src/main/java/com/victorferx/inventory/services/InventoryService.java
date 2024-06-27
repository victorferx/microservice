package com.victorferx.inventory.services;

import com.victorferx.inventory.model.dto.BaseResponse;
import com.victorferx.inventory.model.dto.OrderItemRequest;
import com.victorferx.inventory.model.entities.Inventory;
import com.victorferx.inventory.repositories.InventoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public boolean isInStock(String sku) {
        var inventory = inventoryRepository.findBySku(sku);
        return inventory.filter(value -> value.getQuantity() > 0).isPresent();
    }

    public BaseResponse areInStock(List<OrderItemRequest> orderItems) {
        var errorList = new ArrayList<String>();
        List<String> skus = orderItems.stream()
                .map(OrderItemRequest::getSku).toList();

        List<Inventory> inventoryList = inventoryRepository.findBySkuIn(skus);
        orderItems.forEach(orderItem -> {
            var inventory = inventoryList.stream()
                    .filter(value -> value.getSku().equals(orderItem.getSku()))
                    .findFirst();
            if (inventory.isEmpty()) {
                errorList.add("Product with sku " + orderItem.getSku() + " does not exist");
            } else if (inventory.get().getQuantity() < orderItem.getQuantity()) {
                errorList.add("Product with sku " + orderItem.getSku() + " has insufficient quantity");
            }
        });

        return errorList.size() > 0
                ? new BaseResponse(errorList.toArray(new String[0]))
                : new BaseResponse(null);
    }
}
