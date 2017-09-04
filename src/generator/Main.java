package generator;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import generator.parser.Handler;

public class Main {
	
	public static void main(String[] args) {
		
		DefaultHandler handler = new Handler();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			try {
				parser.parse(new File("resources/model/CinemaExample.xml"), handler);
				String outputPath = new File("").getAbsolutePath() + "\\generated";
				GeneratorOptions genOpt = new GeneratorOptions(outputPath, "", "resources/templates", "{0}.java", true, "cinema");
				System.out.println(outputPath);
				Generator generator = new Generator(genOpt);
				generator.generate();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
