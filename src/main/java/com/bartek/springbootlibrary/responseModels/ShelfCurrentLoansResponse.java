package com.bartek.springbootlibrary.responseModels;

import com.bartek.springbootlibrary.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShelfCurrentLoansResponse {

    private Book book;
    private int daysLeft;
}
