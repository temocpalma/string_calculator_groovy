package com.makingdevs.services

import spock.lang.*

class StringCalculatorServiceSpec extends Specification {

  StringCalculatorService service = new StringCalculatorService()

  @Unroll("when numbers is #the_numbers, the sum is #the_sum")
  def "take up to two numbers, separated by commas, and will return their sum"() {
    given:
      String numbers = the_numbers
    when:
      int sum = service.add(numbers)
    then:
      sum == the_sum
    where:
      the_numbers     |  the_sum
      ""              |   0
      "2"             |   2
      "1,1"           |   2
      "1,2"           |   3
      "10,20"         |   30
  }

  @Unroll("when numbers is #the_numbers, the sum is #the_sum")
  def "the Add method handle an unknown amount of numbers"() {
    given:
      String numbers = the_numbers
    when:
      int sum = service.add(numbers)
    then:
      sum == the_sum
    where:
      the_numbers     |  the_sum
      "10,20,30"      |   60
      "10,20,30,5"    |   65
      "1\n2,3"        |   6
      "1,\n"          |   1
  }

  @Unroll("when numbers is #the_numbers, the sum is #the_sum")
  def "the Add method handle new lines between numbers, instead of commas"() {
    given:
      String numbers = the_numbers
    when:
      int sum = service.add(numbers)
    then:
      sum == the_sum
    where:
      the_numbers     |  the_sum
      "1\n2,3"        |   6
      "1,\n"          |   1
  }

  @Unroll("when numbers is #the_numbers, the sum is #the_sum")
  def "the Add method support different delimiters"() {
    given:
      String numbers = the_numbers
    when:
      int sum = service.add(numbers)
    then:
      sum == the_sum
    where:
      the_numbers     |  the_sum
      "//;\n1;2"      |   3
  }

  @Unroll("when numbers is #the_numbers, thrown a exception with message #the_message")
  def "with a negative number will throw an exception 'negatives not allowed' - and the negative that was passed"() {
    given:
      String numbers = the_numbers
    when:
      int sum = service.add(numbers)
    then:
      RuntimeException ex = thrown()
      ex.message == the_message
    where:
      the_numbers     | the_message
      "1,2,-3,4"      | "negatives not allowed: -3"
      "1,-2,3,4"      | "negatives not allowed: -2"
      "1\n-2,3,4"     | "negatives not allowed: -2"
      "//;\n1;-5;2;4" | "negatives not allowed: -5"
  }

  @Unroll("when numbers is #the_numbers, thrown a exception with message #the_message")
  def "if there are multiple negatives, show all of them in the exception message"() {
    given:
      String numbers = the_numbers
    when:
      int sum = service.add(numbers)
    then:
      RuntimeException ex = thrown()
      ex.message == the_message
    where:
      the_numbers       | the_message
      "1,-2,-3,4"       | "negatives not allowed: -2,-3"
      "-1,-2,5,3,-4"    | "negatives not allowed: -1,-2,-4"
      "-1,-2,5,3\n-4"   | "negatives not allowed: -1,-2,-4"
      "//;\n1;-5;2;-4"  | "negatives not allowed: -5,-4"
  }
}