package io.swagger.api;

import io.swagger.api.*;
import io.swagger.model.*;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import io.swagger.model.Product;
import io.swagger.model.Error;

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2016-10-25T11:38:51.795Z")
public abstract class ProductsApiService {
    public abstract Response productsGet(Double min,Double max,SecurityContext securityContext) throws NotFoundException;
    public abstract Response productsIdGet(String id,SecurityContext securityContext) throws NotFoundException;
}
