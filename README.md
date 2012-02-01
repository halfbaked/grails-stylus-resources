This is a Grails plugin than enables the easy inclusion of files of the Stylus stylesheet language into a Grails appplication. 
It requires the well established resources plugin.

## Background

[Stylus](https://github.com/LearnBoost/stylus) is yet another stylesheet language. There are quite a few these days! Stylus is written by the prolific [TJ Holowaychuk](https://github.com/visionmedia). 

This plugin uses Mozilla's [Rhino engine](https://github.com/mozilla/rhino) to execute a slightly modified version of the [browser compatible version](http://learnboost.github.com/stylus/try.html) of the Stylus compiler.

## Usage

To add Stylus files to your Grails project:

* Install the plugin (adding to your BuildConfig is best)
* Actually add the stylus files to your project. I placed mine adjacent to the css folder in styl.
* Reference your stylus files in your ApplicationResources file (or where ever your defining your resources)

Example
<pre>
  stylus {
    resource url: 'styl/main.styl'
  }  
</pre>

The above example will create a resource you can include in pages or have another resource depend on. The Stylus files
are converted into Css files. 

Example main.styl

<pre>
  border-radius()
    -webkit-border-radius arguments  
    -moz-border-radius arguments  
    border-radius arguments 
  div#my-box
    border-radius 4px
</pre>

which would be converted to main.css
<pre>
  div#my-box {
    -webkit-border-radius: 4px;
    -moz-border-radius: 4px;
    border-radius: 4px;
  }
</pre>

Note: that not all features of Stylus may work due to the porting process of the compiler from nodeJs to the browser. 
