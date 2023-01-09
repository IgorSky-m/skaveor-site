package com.skachko.entities.rest.api_case;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skachko.libraries.search.advice.ExceptionHandlerAdvice;
import com.skachko.entities.rest.api_case.api.IMyService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MyControllerTest {

    private final UUID first = UUID.fromString("7c2e1f2f-d5e6-46e8-acad-abbb8a79ad53");
    private final UUID second = UUID.fromString("f195d613-b70d-45d3-a684-8f25c78c669f");
    private final UUID third = UUID.fromString("91220ce6-2c2d-46b1-84e3-2ae748bb6708");

    private final Set<UUID> validUuids = Set.of(first, second, third);

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ObjectMapper mapper;
    private MockMvc mockMvc;
    @Mock
    private IMyService service;

    @InjectMocks
    private MyController controller;

    @BeforeAll
    public void setupAll() {

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ExceptionHandlerAdvice(messageSource))
                .build();

        setup();
    }

    @BeforeEach
    public void setup() {
        when(service.findAll()).thenReturn(createAll());

        when(service.findById(first))
                .thenReturn(createFirst());

        when(service.findById(second))
                .thenReturn(createSecond());

        when(service.findById(third))
                .thenReturn(createThird());

        ArgumentMatcher<UUID> invalidIdMatcher = (id) -> !validUuids.contains(id);

        when(service.findById(argThat(invalidIdMatcher)))
                .thenThrow(new IllegalArgumentException("Entity not found"));

        when(service.create(any())).then(AdditionalAnswers.returnsFirstArg());

        when(service.update(any(), any())).then(AdditionalAnswers.returnsSecondArg());

        doNothing().when(service).delete(any());
    }


    @AfterEach
    public void reset() {
        Mockito.reset(service);
    }

    @Test
    @DisplayName("get all test")
    public void getAll() throws Exception {
        MyEntity firstEntity = createFirst();
        MyEntity secondEntity = createSecond();
        MyEntity thirdEntity = createThird();

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/my").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(firstEntity.getId().toString())))
                .andExpect(jsonPath("$[0].name", is(firstEntity.getName())))
                .andExpect(jsonPath("$[0].description", is(firstEntity.getDescription())))
                .andExpect(jsonPath("$[1].id", is(secondEntity.getId().toString())))
                .andExpect(jsonPath("$[1].name", is(secondEntity.getName())))
                .andExpect(jsonPath("$[1].description", is(secondEntity.getDescription())))
                .andExpect(jsonPath("$[2].id", is(thirdEntity.getId().toString())))
                .andExpect(jsonPath("$[2].name", is(thirdEntity.getName())))
                .andExpect(jsonPath("$[2].description", is(thirdEntity.getDescription())));
    }


    @Test
    @DisplayName("get one test")
    public void getOne() throws Exception {

        MyEntity firstEntity = createFirst();

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/my/" + first).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(firstEntity.getId().toString())))
                .andExpect(jsonPath("$.name", is(firstEntity.getName())))
                .andExpect(jsonPath("$.description", is(firstEntity.getDescription())));

        MyEntity secondEntity = createSecond();

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/my/" + second).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(secondEntity.getId().toString())))
                .andExpect(jsonPath("$.name", is(secondEntity.getName())))
                .andExpect(jsonPath("$.description", is(secondEntity.getDescription())));

        MyEntity thirdEntity = createThird();

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/my/" + third).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(thirdEntity.getId().toString())))
                .andExpect(jsonPath("$.name", is(thirdEntity.getName())))
                .andExpect(jsonPath("$.description", is(thirdEntity.getDescription())));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/my/" + UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("create one")
    public void create() throws Exception {
        MyEntity firstEntity = createFirst();

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/my")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(firstEntity)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(first.toString())))
                .andExpect(content().string(this.mapper.writeValueAsString(firstEntity)));
    }

    @Test
    @DisplayName("update one")
    public void update() throws Exception {
        MyEntity firstEntity = createFirst();
        firstEntity.setName("Upd Name");
        firstEntity.setDescription("Upd Description");


        mockMvc.perform(
                        MockMvcRequestBuilders.put("/my/" + firstEntity.getId().toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(firstEntity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(first.toString())))
                .andExpect(jsonPath("$.name", is("Upd Name")))
                .andExpect(jsonPath("$.description", is("Upd Description")))
                .andExpect(content().string(this.mapper.writeValueAsString(firstEntity)));
    }

    @Test
    @DisplayName("delete")
    public void delete() throws Exception {


        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/my/" + first)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private List<MyEntity> createAll() {
        return List.of(createFirst(), createSecond(), createThird());
    }

    private MyEntity createFirst() {
        return MyEntity.builder()
                .id(first)
                .name("Entity1")
                .description("this is first description")
                .build();
    }

    private MyEntity createSecond() {
        return MyEntity.builder()
                .id(second)
                .name("Entity2")
                .description("this is second description")
                .build();
    }

    private MyEntity createThird() {
        return MyEntity.builder()
                .id(third)
                .name("Entity2")
                .description("this is second description")
                .build();
    }


}