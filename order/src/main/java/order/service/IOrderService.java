package order.service;

import order.bean.CreateOrderResult;
import order.bean.OrderRequest;
import order.bean.message.CancelOrderMessage;
import order.bean.message.UpdateOrderMessage;

public interface IOrderService {

    CreateOrderResult createOrder(OrderRequest orderRequest);

    void updateOrder(UpdateOrderMessage updateOrderMessage);

    void cancelOrder(CancelOrderMessage updateOrderMessage);
}
