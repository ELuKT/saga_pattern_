package order.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order.bean.*;
import order.service.IOrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class OrderController {

    private final IOrderService orderService;

    @PostMapping("createOrder")
    public BaseResult createOrder(@RequestBody OrderRequest orderRequest) {
        CreateOrderResult result;
        try {
            result = orderService.createOrder(orderRequest);
        } catch (SimpleException e) {
            log.error(e.getAppMsg(), e);
            return new OrderResponse(e.getAppMsg());
        }
        return new OrderResponse(result.getOrderId(), result.getOrderStatus());
    }

}
