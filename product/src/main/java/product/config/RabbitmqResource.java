package product.config;

public class RabbitmqResource {
    public static final String PRODUCT_QUEUE = "q.saga.product";
    public static final String EXCHANGE = "ex.saga";
    public static final String TO_ORCHESTRATOR_ROUTE_KEY = "rk.saga.product.orch";
}
