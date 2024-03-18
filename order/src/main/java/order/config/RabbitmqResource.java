package order.config;

public class RabbitmqResource {
    public static final String ORDER_QUEUE = "q.saga.order";
    public static final String EXCHANGE = "ex.saga";
    public static final String TO_ORCHESTRATOR_ROUTE_KEY = "rk.saga.order.orch";
}
