import org.grails.plugin.resource.mapper.*
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.codehaus.groovy.grails.plugins.support.aware.GrailsApplicationAware
import org.codehaus.groovy.grails.commons.GrailsApplication
import grails.util.BuildSettings


class StylusResourceMapper {

  def pluginManager
  def buildSettings
  def phase = MapperPhase.GENERATION
  static defaultExcludes = ['**/*.js','**/*.png','**/*.gif','**/*.jpg','**/*.jpeg','**/*.gz','**/*.zip']
  GrailsApplication grailsApplication

  def map(resource, config) {
    File originalFile = resource.processedFile
    if (originalFile.name.endsWith('.styl')) {
      def stylusEngine = new com.saasplex.stylus.StylusEngine()
      def input = grailsApplication.parentContext.getResource(resource.sourceUrl).file.text
      log.debut "Stylus Input: $input"
      def output = stylusEngine.compile(input)
      log.debug "Stylus Output: $output"
      File target = new File("${originalFile.absolutePath}.css")         
      target.write(output)

      resource.processedFile = target
      resource.updateActualUrlFromProcessedFile()
      resource.sourceUrlExtension = 'css'
      resource.contentType = 'text/css'
    }
  }

}

