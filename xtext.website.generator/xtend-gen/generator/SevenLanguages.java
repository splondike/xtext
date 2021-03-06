package generator;

import generator.AbstractWebsite;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class SevenLanguages extends AbstractWebsite {
  public String path() {
    return "7languages.html";
  }
  
  public CharSequence contents() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<!--Container-->");
    _builder.newLine();
    _builder.append("<div id=\"header_wrapper\" class=\"container\" >");
    _builder.newLine();
    _builder.append("</div>");
    _builder.newLine();
    _builder.append("<div id=\"page\" class=\"languages\">");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<div class=\"container\">");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<div style=\"float :right;\"><a href=\"http://www.bmbf.de/en/\" target=\"_blank\"><img src=\"images/bmbf-logo.png\"/></a></div>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<h1>7 Languages For The JVM<small>- Learning By Example</small></h1>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<hr />");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<div class=\"row\">");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("<div class=\"span6\" style=\"margin: 4px; text-align: justify;\">");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("<p style=\"margin: 5pt;\">");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("Structural DSLs are great but at some point a software system needs behavior. Xtext now lets you reuse and integrate all kinds of Java language constructs right in your language. The new approach allows for embedding the powerful expressions known from Xtend as well as Java type references and annotations. As a result you get a holistic development experience and a fully-integrated Eclipse IDE, too.");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("</p>");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("<p style=\"margin: 5pt;\">");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("<a href=\"7languagesDoc.html\">Learn this new API by example and have a look at the 7 different languages we\'ve built with it (in less than 7 weeks). It is surprisingly simple!</a>");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("</p>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("</div>");
    _builder.newLine();
    _builder.append("\t\t\t");
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("A simple scripting language that allows you to write code into a blank file without any");
    _builder_1.newLine();
    _builder_1.append("\t\t");
    _builder_1.append("structural boilerplate like class or method definitions.<br/>");
    _builder_1.newLine();
    _builder_1.append("\t\t");
    _builder_1.append("It translates to a Java class with a main method.");
    _builder_1.newLine();
    CharSequence _language = this.language("Scripting Language", "7languagesDoc.html#scripting", "images/red-glossy-scripting.png", _builder_1);
    _builder.append(_language, "			");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("</div>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<div class=\"row\">");
    _builder.newLine();
    _builder.append("\t\t\t");
    StringConcatenation _builder_2 = new StringConcatenation();
    _builder_2.append("A little language that lets you define tasks and dependencies between them. ");
    _builder_2.newLine();
    _builder_2.append("Much like a nice and concise Ant or a statically typed Gradle.");
    _builder_2.newLine();
    CharSequence _language_1 = this.language("Build Language", "7languagesDoc.html#builddsl", "images/red-glossy-helmet.png", _builder_2);
    _builder.append(_language_1, "			");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    StringConcatenation _builder_3 = new StringConcatenation();
    _builder_3.append("Document structures for MongoDB can be defined with this language. It generates");
    _builder_3.newLine();
    _builder_3.append("a handy Java API to interact with MongoDB in a statically typed and structured manner.");
    _builder_3.newLine();
    CharSequence _language_2 = this.language("DSL for MongoDB", "7languagesDoc.html#mongoDB", "images/red-glossy-database.png", _builder_3);
    _builder.append(_language_2, "			");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("</div>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<div class=\"row\">");
    _builder.newLine();
    _builder.append("\t\t\t");
    StringConcatenation _builder_4 = new StringConcatenation();
    _builder_4.append("This language allows you to declare Google Guice modules in a more readable and declarative way.");
    _builder_4.newLine();
    _builder_4.append("Thereby many of the runtime checks can be done at compile time.");
    _builder_4.newLine();
    CharSequence _language_3 = this.language("DSL for Guice", "7languagesDoc.html#guice", "images/red-glossy-guice.png", _builder_4);
    _builder.append(_language_3, "			");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    StringConcatenation _builder_5 = new StringConcatenation();
    _builder_5.append("Web.xml on steroids and without the XML: Write a URL pattern and map it to a method call in Java.");
    _builder_5.newLine();
    _builder_5.append("Similar to how the Play! framework does it, but with great Eclipse integration and not limited to static methods.");
    _builder_5.newLine();
    CharSequence _language_4 = this.language("Http Routing Language", "7languagesDoc.html#httpRouting", "images/red-glossy-web.png", _builder_5);
    _builder.append(_language_4, "			");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("</div>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<div class=\"row\">");
    _builder.newLine();
    _builder.append("\t\t\t");
    StringConcatenation _builder_6 = new StringConcatenation();
    _builder_6.append("A straight forward template language which allows for statically-typed expressions and ");
    _builder_6.newLine();
    _builder_6.append("named arguments with default values.");
    _builder_6.newLine();
    CharSequence _language_5 = this.language("Template Language", "7languagesDoc.html#template", "images/red-glossy-template.png", _builder_6);
    _builder.append(_language_5, "			");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    StringConcatenation _builder_7 = new StringConcatenation();
    _builder_7.append("Remember Logo? A cute little tortoise painting on a canvas whatever you want.");
    _builder_7.newLine();
    CharSequence _language_6 = this.language("Little Tortoise", "7languagesDoc.html#tortoise", "images/red-glossy-turtle.png", _builder_7);
    _builder.append(_language_6, "			");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("</div>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</div>");
    _builder.newLine();
    _builder.append("</div>");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence language(final String title, final String href, final String img, final CharSequence description) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<div class=\"span6 float language-card\">");
    _builder.newLine();
    _builder.append("<a href=\"");
    _builder.append(href, "");
    _builder.append("\" class=\"anchor-in-div\"></a>");
    _builder.newLineIfNotEmpty();
    _builder.append("<table>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<tr>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<td >");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("<img class=\"language-icon\" src=\"");
    _builder.append(img, "			");
    _builder.append("\" alt=\"Image\" />");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("</td>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<td>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("<h2>");
    _builder.append(title, "			");
    _builder.append("</h2>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    _builder.append("<p>");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append(description, "				");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    _builder.append("</p>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("</td>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</tr>");
    _builder.newLine();
    _builder.append("</table>");
    _builder.newLine();
    _builder.append("</div>");
    _builder.newLine();
    return _builder;
  }
}
