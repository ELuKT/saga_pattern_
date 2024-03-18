package orchestrator.config;

public class RabbitmqResource {

    public static final String EXCHANGE = "ex.saga";
    public static final String ORCHESTRATOR_QUEUE = "q.saga.orch";
    public static final String ORCHESTRATOR_ROUTE_KEY = "rk.saga.*.orch";
    public static final String PRODUCT_QUEUE = "q.saga.product";
    public static final String PRODUCT_ROUTE_KEY= "rk.saga.*.product";
    public static final String ORDER_QUEUE = "q.saga.order";
    public static final String ORDER_ROUTE_KEY= "rk.saga.*.order";
    public static final String DEAD_LETTER_QUEUE = "q.saga.dl";
    public static final String DEAD_LETTER_ROUTE_KEY = "rk.saga.dl";
    public static final String SEND_TO_ORDER_ROUTE_KEY= "rk.saga.orch.order";
    public static final String SEND_TO_PRODUCT_ROUTE_KEY= "rk.saga.orch.product";

}
