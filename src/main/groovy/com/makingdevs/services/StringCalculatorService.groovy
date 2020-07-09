package com.makingdevs.services

class StringCalculatorService {

  int add(String numbers) {
    String delimiter = "[\n,]"
    def the_numbers = numbers

    if (numbers.startsWith("//")) {
      def lines_numbers = numbers.tokenize("\n")
      delimiter = lines_numbers.first() - "//"
      the_numbers = lines_numbers[1]
    }

    def list_string_numbers = the_numbers.split(delimiter)
    def list_numbers = list_string_numbers.collect { it.isEmpty() ? 0 : it.toInteger() }
    def negative_numbers = list_numbers.findAll{ it < 0 }
    if (!negative_numbers.isEmpty()) {
      throw new RuntimeException("negatives not allowed: ${negative_numbers.join(",")}")
    }
    list_numbers.sum()
  }

}