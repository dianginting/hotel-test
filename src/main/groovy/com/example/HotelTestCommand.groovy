package com.example

import com.example.service.HotelService
import io.micronaut.configuration.picocli.PicocliRunner
import jakarta.inject.Inject
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import picocli.CommandLine.Parameters

@Command(name = 'pagination', description = 'Get Hotel Pagination Data',
        mixinStandardHelpOptions = true)
class HotelTestCommand implements Runnable {

    @Parameters(index = '0', description = 'Start Value')
    Integer start

    @Parameters(index = '1',description = 'Max Data Get')
    Integer limit

    @Parameters(index = '2', description = 'Sort Order',defaultValue = "desc")
    String sort

    @Option(names = ['-v', '--verbose'], description = '...')
    boolean verbose

    static void main(String[] args) throws Exception {
        PicocliRunner.run(HotelTestCommand.class, args)
    }


    @Inject
    HotelService service

    void run() {
        // business logic here
        if (verbose) {
            println "Hi!"
        }
        def getData = service.getPagination(start,limit,sort).blockingGet()
        println(getData)
    }
}
