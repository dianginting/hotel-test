package com.example.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class HotelData {
    Integer hotelId
    Integer availableRooms
    String name
}
