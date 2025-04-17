package com.hunger.saviour.portal.apis;

import com.hunger.saviour.portal.dtos.OrderDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderAPI {

    @GetMapping("/{username}")
    public List<OrderDTO> getOrdersByUsername(){
        // tmrw implementation
        return null;
    }
}
