package com.example.service

import com.example.model.HotelData
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.logging.Slf4j
import io.micronaut.core.annotation.Nullable
import io.micronaut.validation.Validated
import io.reactivex.rxjava3.core.Single
import jakarta.inject.Singleton
import groovy.json.JsonBuilder

import javax.validation.constraints.NotNull
import java.util.stream.Collectors

@Singleton
@Validated
@Slf4j
class HotelService {

    static final DESC = "desc"

    String getPagination(@NotNull Integer start,
                                           @NotNull Integer limit,
                                           @Nullable String sort ) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader()
        def mapper = new ObjectMapper()
        InputStream cis
        Integer nextStart = 0
        List<HotelData> result = null
        try {
            cis = classloader.getResourceAsStream("mockup-data.json")
            def hotelList = Arrays.asList(mapper.readValue(cis, HotelData[].class))

            Integer maxValue  = hotelList
                    .stream()
                    .mapToInt(v -> v.availableRooms)
                    .max().orElse(start)

            start = start == 0 && sort == DESC ? maxValue + 1 : start

            result = hotelList.stream()
                    .filter{
                        sort == DESC ? it.availableRooms < start : it.availableRooms > start}
                    .sorted {j1, j2 ->
                        sort == DESC ? j2.availableRooms <=> j1.availableRooms :  j1.availableRooms <=> j2.availableRooms
                    }
                    .limit(limit)
                    .collect(Collectors.toList())

            nextStart = result.last()?.availableRooms
        } finally {
            if (cis != null) cis.close()
        }
        def rsp = new PaginationResult(nextStart: nextStart,hotels:result )

        return new JsonBuilder(rsp).toPrettyString()
    }

    static class PaginationResult{
        Integer nextStart
        @Nullable
        List<HotelData> hotels
    }
}
