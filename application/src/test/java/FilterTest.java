import com.alma.group8.application.configuration.CORSFilter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Thibault on 24/11/16.
 * Tests the Filter from Javax.Servlet
 */
public class FilterTest {
    private Filter filter;

    @Before
    public void setUp() {
        filter = new CORSFilter();
    }

    @Test
    public void testHttpRequest() throws IOException, ServletException {
        HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        FilterChain filterChain = Mockito.mock(FilterChain.class);
        Map<String, String> map = new HashMap<>();

        Mockito.doAnswer(invocationOnMock -> {
            map.put((String) invocationOnMock.getArguments()[0], (String) invocationOnMock.getArguments()[1]);
            return null;
        }).when(httpServletResponse).setHeader(Mockito.anyString(), Mockito.anyString());

        filter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        Assert.assertNotNull(map.get("Access-Control-Allow-Credentials"));
        Assert.assertNotNull(map.get("Access-Control-Allow-Methods"));
        Assert.assertNotNull(map.get("Access-Control-Max-Age"));
        Assert.assertNotNull(map.get("Access-Control-Allow-Headers"));

    }
}
