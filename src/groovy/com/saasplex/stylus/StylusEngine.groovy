package com.saasplex.stylus

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.JavaScriptException;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.tools.shell.Global;
import org.springframework.core.io.ClassPathResource

// Stylus engine uses Mozilla Rhino to compile stylus sheets
// using an existing javascript in-browser compiler
class StylusEngine {

  def Scriptable globalScope
  def ClassLoader classLoader

  def StylusEngine(){
    try {
      classLoader = getClass().getClassLoader()
      def stylusJsResource = (new ClassPathResource('com/saasplex/stylus/stylus.js', getClass().classLoader))
      assert stylusJsResource.exists() : "StylusJs resource not found"    

      def stylusJsStream = stylusJsResource.inputStream

      Context cx = Context.enter()
      cx.setOptimizationLevel(9)
      globalScope = cx.initStandardObjects()
      cx.evaluateReader(globalScope, new InputStreamReader(stylusJsStream, 'UTF-8'), stylusJsResource.filename, 1, null)    

    } catch (Exception e) {
      throw new Exception("Stylus Engine initialization failed.", e)
    } finally {
      try {
        Context.exit()
      } catch (java.lang.IllegalStateException e) {}
    }
  }

  def compile(String content) {    
    try {
      def cx = Context.enter()
      def compileScope = cx.newObject(globalScope)
      compileScope.setParentScope(globalScope)
      def stylusSheet = content.trim()
      compileScope.put("stylusSheet", compileScope, stylusSheet)
      def callingCode = """
        (function(){
        var result = stylus(stylusSheet).renderSync(stylusSheet);     
        if (result.err) { return result.err; }
        return result.css; 
        })();
      """      
      def result = cx.evaluateString(compileScope, callingCode, "Stylus compile command", 0, null)       
      return "$result"
    } catch (Exception e) {
      throw new Exception("Stylus Engine compilation failed.", e)
    } finally {
      Context.exit()
    }      
  }

}
