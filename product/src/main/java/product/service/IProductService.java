package product.service;

import product.bean.message.CreateSaleRecordMessage;

public interface IProductService {
    void createSaleRecord(CreateSaleRecordMessage createSaleRecordMessage);
}
