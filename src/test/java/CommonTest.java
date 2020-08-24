/**
 * Copyright 2014-2019  the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

import com.webank.oracle.Application;
import com.webank.oracle.base.properties.EventRegisterProperties;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * test front controller
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class CommonTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private EventRegisterProperties properties;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void givenAcceptingAllCertificatesUsing4_4_whenUsingRestTemplate_thenCorrect()
            throws ClientProtocolException, IOException {
        // String urlOverHttps = "https://api.kraken.com/0/public/Ticker?pair=ETHXBT";
        String urlOverHttps = "https://api.kraken.com/0/public/Ticker?pair=ETHXBT";
        CloseableHttpClient httpClient
                = HttpClients.custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory
                = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

//        ResponseEntity<String> response
//                = new RestTemplate(requestFactory).exchange(
//                urlOverHttps, HttpMethod.GET, null, String.class);
        String response =  new RestTemplate(requestFactory).getForObject(urlOverHttps, String.class);
        System.out.println(response);
      //  assertThat(response.getStatusCode().value(), equalTo(200));
    }

    @Test
    public void testHttp() {
        String url1 = "https://www.random.org/integers/?num=100&min=1&max=100&col=1&base=10&format=plain&rnd=new";
        //String url = "https://api.kraken.com/0/public/Ticker?pair=ETHXBT";
        Object result = restTemplate.getForObject(url1, Object.class);
        ResponseEntity<?> entity = restTemplate.getForEntity(url1, Object.class);
        HttpHeaders header = entity.getHeaders();
        for (Map.Entry<String, List<String>> entityt : header.entrySet()) {
            String key = entityt.getKey();
            List<String> value = entityt.getValue();
            System.out.println("key" + entityt + " value:" + value);
        }
    }

//    @Test
//    public void testTopics() {
//        String eventName = properties.getEvents().get(0).getTopicList().get(0);
//        String eventName1 = StringUtils.replaceWhitespaceCharacters(properties.getEvents().get(0).getTopicList().get(0), "");
//        String str1 = TopicTools.stringToTopic(eventName);
//        String str2 = TopicTools.stringToTopic(eventName1);
//        String str3 = EventEncoder.encode(LOG1_EVENT);
//        assertThat(str1, equalTo(str3));
//    }

}
