package com.invia.challenge.cashpool.traveler;

import com.invia.challenge.cashpool.CashpoolApplicationTest;
import com.invia.challenge.cashpool.model.Traveler;
import com.invia.challenge.cashpool.service.TravelerService;
import com.invia.challenge.cashpool.service.dto.TravelerDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by khayatzadeh on 1/24/2018.
 */
public class TravelerControllerTest extends CashpoolApplicationTest {

    public static final String URL_PREFIX = "/rest-api/traveler";

    @Autowired
    private TravelerService travelerService;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private HttpMessageConverter messageConverter;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.messageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        Assert.assertNotNull("the JSON message converter must not be null",
                this.messageConverter);
    }

    @Before
    public void init() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        travelerService.persist(new TravelerDto("Max"));
        travelerService.persist(new TravelerDto("Bill"));
        travelerService.persist(new TravelerDto("Martha"));
    }

    @Test
    public void getAllTravelersTest() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/getAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void persistTravelerTest() throws Exception {
        String json = json(new Traveler("New Traveler"));
        mockMvc.perform(post(URL_PREFIX + "/persist")
                .contentType(contentType)
                .content(json))
                .andExpect(status().isOk());
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.messageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
