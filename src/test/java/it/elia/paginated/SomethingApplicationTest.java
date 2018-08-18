package it.elia.paginated;

import it.elia.paginated.entity.Something;
import it.elia.paginated.entity.SomethingBuilder;
import it.elia.paginated.repository.SomethingRepository;
import it.elia.paginated.service.SomethingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.IntStream;

import static it.elia.paginated.controller.SomethingController.PAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class SomethingApplicationTest {

    public static final int NUM_SUM = 20;
    private static boolean done = false;
    @Autowired
    private SomethingRepository repository;
    private SomethingBuilder builder = new SomethingBuilder();
    @Autowired
    private SomethingService service;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void init() {
        if (!done) {
            IntStream.range(0, NUM_SUM).forEach(
                    it -> {
                        Something something = new Something(it, "asd", "sdsa", it * 0.5f);
                        repository.save(something);
                    }
            );
            done = true;
        }
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void count() {
        List<Something> all = (List<Something>) repository.findAll();
        assertThat(all.stream().count()).isEqualTo(NUM_SUM);
    }

    @Test
    public void page() {
        Page<Something> id = repository.findAll(new PageRequest(0, 5, new Sort("id")));
        assertThat(id.getSize()).isEqualTo(5);
        assertThat(id.isFirst()).isTrue();
        assertThat(id.map(Something::getId).getContent().stream().findFirst().get()).isEqualTo(0L);
    }

    @Test
    public void pageService() {
        Page<Something> id = repository.findAll(new PageRequest(0, 5, new Sort("id")));
        Page<Something> all = service.findAll(0, 5);
        List<Long> content = id.map(Something::getId).getContent();
        assertThat(all.map(Something::getId).getContent().containsAll(content)).isTrue();
    }

    @Test
    public void pagetemplate() throws Exception {
        mockMvc.perform(get("/" + PAGE).param(PAGE, "0").param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(xpath("//p").nodeCount(5));
        mockMvc.perform(get("/" + PAGE).param(PAGE, "0").param("size", "50"))
                .andExpect(status().isOk())
                .andExpect(xpath("//p").nodeCount(NUM_SUM));
    }

    @Test
    public void pagetemplate0param() throws Exception {
        mockMvc.perform(get("/" + PAGE))
                .andExpect(status().isOk())
                .andExpect(xpath("//p").nodeCount(5));
    }


    @Test
    public void pageControllerOutPage() throws Exception {
        mockMvc.perform(get("/" + PAGE).param(PAGE, "21").param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Page index")));
    }

    @Test
    public void pageControllerOutPageLess0() throws Exception {
        mockMvc.perform(get("/" + PAGE).param(PAGE, "-1").param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Page index")));
    }



    @Test
    public void pageControllerOutSize() throws Exception {
        mockMvc.perform(get("/" + PAGE).param(PAGE, "0").param("size", "0"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Page size")));
    }

    @Test
    public void pageControllerOutSizeGreater100() throws Exception {
        mockMvc.perform(get("/" + PAGE).param(PAGE, "0").param("size", "101"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Page size")));
    }


}
