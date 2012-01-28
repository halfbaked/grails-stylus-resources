package com.saasplex.stylus


import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.springframework.core.io.ClassPathResource


class StylusEngineTests extends grails.test.GrailsUnitTestCase {

  def stylusEngine

  void setUp(){
    stylusEngine = new StylusEngine()  
  }

  void testCompile(){
    def input, output
    input = """
body {
  font: 14px/1.5 Helvetica, arial, sans-serif;
  #logo {
    border-radius: 5px;
  }
}
    """
    output = stylusEngine.compile(input)
    assert output.contains('body') && output.contains('logo') : "Output $output"

    input = (new ClassPathResource('com/saasplex/stylus/example.styl', getClass().classLoader)).file.text
    output = stylusEngine.compile(input)
    assert output.contains('div#my-box') : "Output $output"

  }
  
}

