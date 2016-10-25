package io.swagger.api;

import io.swagger.model.*;
import io.swagger.api.ProductsApiService;
import io.swagger.api.factories.ProductsApiServiceFactory;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;

import io.swagger.model.Product;
import io.swagger.model.Error;

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;

@Path("/products")

@Produces({ "application/json" })
@io.swagger.annotations.Api(description = "the products API")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2016-10-25T11:38:51.795Z")
public class ProductsApi  {
   private final ProductsApiService delegate = ProductsApiServiceFactory.getProductsApi();

    @GET
    
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Database containing all the products", notes = "Contains all the products for the different shop, especially Fluffy-shop.  Each product is identified by an unique ID, but also with a name, a price  and a small description. The price can be increased by the resellers if  the want to do some profit. ", response = Product.class, responseContainer = "List", tags={ "Products", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "An array of products", response = Product.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 200, message = "Unexpected error", response = Product.class, responseContainer = "List") })
    public Response productsGet(@ApiParam(value = "Minimum price of the products wanted.") @QueryParam("min") Double min
,@ApiParam(value = "Maximum price of the products wanted.") @QueryParam("max") Double max
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.productsGet(min,max,securityContext);
    }
    @GET
    @Path("/{id}")
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Return a single product with the id", notes = "", response = Product.class, tags={ "Product", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "A single product", response = Product.class),
        
        @io.swagger.annotations.ApiResponse(code = 200, message = "Unexpected error", response = Product.class) })
    public Response productsIdGet(@ApiParam(value = "ID of the product to fetch",required=true) @PathParam("id") String id
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.productsIdGet(id,securityContext);
    }
}
