import com.alma.group8.handlers.ExceptionHandling;
import com.alma.group8.model.Error;
import com.alma.group8.model.exceptions.AlreadyExistingProductException;
import com.alma.group8.exceptions.FunctionalException;
import com.alma.group8.model.exceptions.NotEnoughProductsException;
import com.alma.group8.model.exceptions.ProductNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Thibault on 22/11/2016.
 * Tests the {@link com.alma.group8.handlers.ExceptionHandling} class
 */
public class ExceptionHandlingTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private ExceptionHandling exceptionHandling;
    private HttpServletResponse httpServletResponse;

    @Before
    public void setUp() {
        exceptionHandling = new ExceptionHandling();
        httpServletResponse = Mockito.mock(HttpServletResponse.class);
    }

    @Test
    public void testFunctionalExceptionHandler() throws IOException {
        FunctionalException functionalException = new FunctionalException("Functional Exception");
        String errorAsString = exceptionHandling.handleFunctionalException(functionalException, httpServletResponse);
        Error error = OBJECT_MAPPER.readValue(errorAsString, Error.class);

        Assert.assertEquals(400, error.getCode());
    }

    @Test
    public void testProductNotFoundException() throws IOException {
        ProductNotFoundException productNotFoundException = new ProductNotFoundException("product not found");
        String errorAsString = exceptionHandling.handleProductNotFoundException(productNotFoundException, httpServletResponse);
        Error error = OBJECT_MAPPER.readValue(errorAsString, Error.class);

        Assert.assertEquals(404, error.getCode());
    }

    @Test
    public void testRuntimeException() throws IOException {
        NullPointerException nullPointerException = new NullPointerException();
        String errorAsString = exceptionHandling.handleTechnicalException(nullPointerException, httpServletResponse);
        Error error = OBJECT_MAPPER.readValue(errorAsString, Error.class);

        Assert.assertEquals(500, error.getCode());
    }

    @Test
    public void testIOException() throws IOException {
        IOException ioException = new IOException();
        String errorAsString = exceptionHandling.handleIOException(ioException, httpServletResponse);
        Error error = OBJECT_MAPPER.readValue(errorAsString, Error.class);

        Assert.assertEquals(400, error.getCode());
    }

    @Test
    public void testNotEnoughProductException() throws IOException {
        NotEnoughProductsException notEnoughProductsException = new NotEnoughProductsException("Not Enough Product Exception");
        String errorAsString = exceptionHandling.handleNotEnoughProductsException(notEnoughProductsException, httpServletResponse);
        Error error = OBJECT_MAPPER.readValue(errorAsString, Error.class);

        Assert.assertEquals(404, error.getCode());
    }

    @Test
    public void testAlreadyExistingException() throws IOException {
        AlreadyExistingProductException alreadyExistingProductException = new AlreadyExistingProductException("Already exists product exception");
        String errorAsString = exceptionHandling.handleAlreadyExistingProductException(alreadyExistingProductException, httpServletResponse);
        Error error = OBJECT_MAPPER.readValue(errorAsString, Error.class);

        Assert.assertEquals(409, error.getCode());
    }
}
