package io.swagger.api.factories;

import io.swagger.api.ProductsApiService;
import io.swagger.api.impl.ProductsApiServiceImpl;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2016-10-25T11:38:51.795Z")
public class ProductsApiServiceFactory {
    private final static ProductsApiService service = new ProductsApiServiceImpl();

    public static ProductsApiService getProductsApi() {
        return service;
    }
}
