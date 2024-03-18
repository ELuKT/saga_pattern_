package product.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import product.bean.message.CreateSaleRecordMessage;
import product.bean.message.ProductServiceResultMessage;
import product.config.RabbitmqResource;
import product.dao.ProductSaleRecordMapper;
import product.entity.ProductSaleRecord;
import product.mware.IRabbitmqMware;
import product.service.IProductService;
import product.status.ProductServiceStatus;
import product.status.SaleStatus;

@Service
@AllArgsConstructor
@Transactional
public class ProductService implements IProductService {

    private final ProductSaleRecordMapper productSaleRecordMapper;
    private final IRabbitmqMware rabbitmqMware;

    @Override
    public void createSaleRecord(CreateSaleRecordMessage createSaleRecordMessage) {
        ProductSaleRecord productSaleRecord = ProductSaleRecord.builder()
                .productId(createSaleRecordMessage.getProductId())
                .amount(createSaleRecordMessage.getAmount())
                .orderId(createSaleRecordMessage.getOrderId())
                .saleStatus(SaleStatus.PURCHASE)
                .build();

        boolean isSuccess;

        if ("A003".equals(createSaleRecordMessage.getProductId())) {
            isSuccess = false;
        } else {
            isSuccess = productSaleRecordMapper.insertProductSaleRecord(productSaleRecord) > 0;
        }

        ProductServiceResultMessage message = new ProductServiceResultMessage(
                productSaleRecord.getProductSaleRecordId(),
                createSaleRecordMessage.getOrderId(),
                isSuccess ? ProductServiceStatus.SUCCESS : ProductServiceStatus.FAIL
        );

        rabbitmqMware.sendMessage(
                message,
                RabbitmqResource.EXCHANGE,
                RabbitmqResource.TO_ORCHESTRATOR_ROUTE_KEY
        );
    }
}
