package com.example.demo.config;

import com.example.demo.entity.Cache;
import com.example.demo.services.CacheService;
import com.example.demo.services.DBService;
import com.example.demo.services.BrokerApiService;
import com.example.demo.services.implementations.InstrumentService;
import com.example.demo.services.implementations.SimpleCacheService;
import com.example.demo.services.implementations.TBankApiService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

@Configuration
public class Config {
    @Bean(name = "restTemplateLong")
    public RestTemplate getRestTemplateLong() {
        try {
            System.out.println("Creating RestTemplate with .cer certificate");

            // Загружаем .cer сертификат из ресурсов
            CertificateFactory cf = CertificateFactory.getInstance("X.509");

            // .cer файл в папке resources/certificates/
            InputStream certStream = new ClassPathResource("certificates/tbank_root.cer").getInputStream();
            X509Certificate caCert = (X509Certificate) cf.generateCertificate(certStream);

            // Создаем KeyStore с нашим сертификатом
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setCertificateEntry("tbank.ru", caCert);

            // Создаем TrustManagerFactory
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keyStore);

            // Создаем SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);

            // Создаем кастомную фабрику запросов
            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory() {
                @Override
                protected void prepareConnection(java.net.HttpURLConnection connection, String httpMethod)
                        throws java.io.IOException {
                    if (connection instanceof HttpsURLConnection) {
                        HttpsURLConnection httpsConnection = (HttpsURLConnection) connection;
                        httpsConnection.setSSLSocketFactory(sslContext.getSocketFactory());
                        httpsConnection.setHostnameVerifier((hostname, session) -> true);
                    }
                    super.prepareConnection(connection, httpMethod);
                }
            };
            requestFactory.setConnectTimeout(Integer.MAX_VALUE);
            requestFactory.setReadTimeout(Integer.MAX_VALUE);

            System.out.println("Successfully loaded certificate: " +
                    caCert.getSubjectDN().getName());

            return new RestTemplate(requestFactory);

        } catch (Exception e) {
            System.err.println("Failed to create custom RestTemplate: " + e.getMessage());
            e.printStackTrace();
            return createDefaultRestTemplate();
        }
    }

    private RestTemplate createDefaultRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(10 * 1000);
        factory.setReadTimeout(20 * 1000);
        return new RestTemplate(factory);
    }

    @Bean(name = "restTemplateShort")
    public RestTemplate getRestTemplateShort() {
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(3 * 1000);
        requestFactory.setReadTimeout(5 * 1000);
        restTemplate.setRequestFactory(requestFactory);
        return restTemplate;
    }

    @Bean(name = "tbank")
    public BrokerApiService getApiService(){
        return new TBankApiService();
    }

    @Bean(name = "instrument")
    public DBService getDBService(){
        return new InstrumentService();
    }

    @Bean(name = "simple")
    public CacheService getCacheService(){
        return new SimpleCacheService();
    }

}
