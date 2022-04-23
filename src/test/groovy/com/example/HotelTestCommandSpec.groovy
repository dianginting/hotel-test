package com.example


import com.example.service.HotelService.PaginationResult
import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.configuration.picocli.PicocliRunner
import io.micronaut.context.ApplicationContext
import io.micronaut.context.env.Environment
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class HotelTestCommandSpec extends Specification {

    @Shared
    @AutoCleanup
    ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)

    @Shared
    def mapper = new ObjectMapper()

    void "Verify API not running when theres no parameters set"() {
        given:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        System.setOut(new PrintStream(baos))

        String[] args = ['-v'] as String[]
        PicocliRunner.run(HotelTestCommand, ctx, args)

        expect:
        baos.toString() == ""
        baos.size() == 0
    }

    void "Verify API Return List Of Data"() {
        given:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        System.setOut(new PrintStream(baos))

        int start = 0
        int limit = 3

        String[] args = [start,limit] as String[]
        PicocliRunner.run(HotelTestCommand, ctx, args)

        expect:
        def mapper = mappingData(baos.toString())
        mapper.nextStart == mapper.hotels.last()?.availableRooms
        mapper.hotels.get(0).availableRooms > start
    }

    void "Verify API Will Return List Desc When Sort Is Empty"() {
        given:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        System.setOut(new PrintStream(baos))

        int start = 0
        int limit = 5

        String[] args = [start,limit] as String[]
        PicocliRunner.run(HotelTestCommand, ctx, args)

        expect:
        def mapper = mappingData(baos.toString())
        mapper.nextStart == mapper.hotels.last()?.availableRooms
        mapper.hotels.get(0).availableRooms > mapper.hotels.last()?.availableRooms
    }


    void "Verify API Will Return List Ascending Order"() {
        given:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        System.setOut(new PrintStream(baos))

        int start = 0
        int limit = 5
        String sort = 'asc'

        String[] args = [start,limit,sort] as String[]
        PicocliRunner.run(HotelTestCommand, ctx, args)

        expect:
        def mapper = mappingData(baos.toString())
        mapper.nextStart == mapper.hotels.last()?.availableRooms
        mapper.hotels.get(0).availableRooms < mapper.hotels.last()?.availableRooms
    }

    void "Verify Next Start Greater than Start when order ascending"() {
        given:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        System.setOut(new PrintStream(baos))

        int start = 150
        int limit = 5
        String sort = 'asc'

        String[] args = [start,limit,sort] as String[]
        PicocliRunner.run(HotelTestCommand, ctx, args)

        expect:
        def mapper = mappingData(baos.toString())
        mapper.nextStart > start
    }

    PaginationResult mappingData(String json){
        return mapper.readValue(json, PaginationResult)
    }
}