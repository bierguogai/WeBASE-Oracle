package com.webank.test.oracle; /**
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

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.webank.oracle.Application;
import com.webank.oracle.base.properties.EventRegisterProperties;

/**
 * test front controller
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class CommonTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private EventRegisterProperties properties;

    @BeforeTestClass
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
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
