import com.alma.group8.api.exceptions.TechnicalException;
import com.alma.group8.application.util.SoapMailVerifier;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Thibault on 25/11/2016.
 * Check if the SOAP service call is working
 */
public class SoapMailVerifierTest {
    private SoapMailVerifier soapMailVerifier;

    @Before
    public void setUp() throws TechnicalException {
        soapMailVerifier = new SoapMailVerifier();
    }

    @Test
    public void testMailVerifierOk()  {
        Assert.assertTrue(soapMailVerifier.isValid("email@gmail.com"));
    }

    @Test
    public void testMailVerifierNok() {
        Assert.assertFalse(soapMailVerifier.isValid("qsdfzrz"));
    }

}
