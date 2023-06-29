package za.co.absa;


import com.gargoylesoftware.htmlunit.javascript.host.fetch.Response;
import io.restassured.http.ContentType;

import javax.net.ssl.SSLContext;

import javax.net.ssl.TrustManagerFactory;

import java.io.FileInputStream;

import java.io.FileNotFoundException;

import java.io.IOException;

import java.security.*;

import java.security.cert.CertificateException;

import static com.thoughtworks.selenium.SeleneseTestBase.assertEquals;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;


public class test2 {

    public static void main(String[] args) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, KeyManagementException {

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());

        trustStore.load(new FileInputStream("C:\\Users\\AB025X9\\OneDrive - Absa\\My Documents\\API's\\nucleus.jks"), "Password1".toCharArray());

        trustManagerFactory.init(trustStore);

        SSLContext sslContext = SSLContext.getInstance("TLC");

        sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
        baseURI = "https://esb.host.uat.absa.co.za:29882/services/CIgetClientDetailsByIDNOV2";

        Response response = (Response) given().relaxedHTTPSValidation().contentType(ContentType.JSON).when().get(baseURI);

    }

}








