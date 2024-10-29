package com.good.Library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.good.Library.entity.BookDetailsEntity;
import com.good.Library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void testGetAllBooks() throws Exception {

        BookDetailsEntity newBook = getBookDetailsEntity();

        Mockito.when(bookService.getAllBooks()).thenReturn(List.of(newBook));

       ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/library/list-of-books")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        assertNotNull(result);
        assertNotNull(result.andReturn().getResponse().getContentAsString());
       System.out.println("actions: "+result);

    }

    private static BookDetailsEntity getBookDetailsEntity() {
        BookDetailsEntity newBook = new BookDetailsEntity();
        newBook.setBookId(1);
        newBook.setBookName("Ivy the very determined dog");
        newBook.setBookAuthor("Harrington");
        newBook.setBookPlace("A1");
        return newBook;


    }

    @Test
    public void testAddNewBook() throws Exception {

        Mockito.when(bookService.addNewBook(getBookDetailsEntity())).thenReturn(getBookDetailsEntity());

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/library/new-book")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(asJsonString(getBookDetailsEntity()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());


        System.out.println("actions: "+result);

        assertNotNull(result);
        assertNotNull(result.andReturn().getResponse().getContentAsString());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetBookByName() throws Exception {

        Mockito.when(bookService.getBookByName(getBookDetailsEntity().getBookName())).thenReturn(Optional.of(getBookDetailsEntity()));

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/library/getByBookName")
                        .param("bookName",getBookDetailsEntity().getBookName())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        System.out.println("actions: "+result);

        assertNotNull(result);
        assertNotNull(result.andReturn().getResponse().getContentAsString());

    }


}
