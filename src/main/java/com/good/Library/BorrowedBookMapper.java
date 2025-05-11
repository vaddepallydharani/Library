
package com.good.Library;

import com.good.Library.entity.BorrowedBookHistory;
import com.good.Library.model.HistoryOfBooksResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
@Mapper(componentModel = "spring")
public interface BorrowedBookMapper {

    BorrowedBookMapper INSTANCE= Mappers.getMapper(BorrowedBookMapper.class);
    @Mapping(source = "returned", target = "status", qualifiedByName = "mapReturnedStatus")
    @Mapping(source = "returnDate", target = "returnDate", qualifiedByName = "mapReturnDate")
    HistoryOfBooksResponse mapStatusToReturned( BorrowedBookHistory borrowedBookHistory);

    @Named("mapReturnedStatus")
    default String mapReturnedStatus(String returned) {
        return returned.equals("Y") ? "returned" : "not returned";
    }

    @Named("mapReturnDate")
    default String mapReturnDate(LocalDate returnDate) {
        return returnDate != null ? returnDate.toString() : "no return date";
    }

}

