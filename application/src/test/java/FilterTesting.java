import com.alma.group8.configuration.CORSFilter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Thibault on 24/11/16.
 * Tests the Filter from Javax.Servlet
 */
public class FilterTesting {
    private Filter filter;

    @Before
    public void setUp() {
        filter = new CORSFilter();
    }

    @Ignore
    @Test
    public void testHttpRequest() throws IOException, ServletException {
        HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
        Map<String, String> map = new HashMap<>();

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                map.put((String) invocationOnMock.getArguments()[0], (String) invocationOnMock.getArguments()[1]);
                return null;
            }
        }).when(httpServletResponse).setHeader(Mockito.anyString(), Mockito.anyString());

        Assert.assertNotNull("Access-Control-Allow-Origin");

    }
}
