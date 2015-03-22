package anyviewj.outline;


import java.io.CharArrayReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import anyviewj.interfaces.view.JavaParser;

import com.bluemarsh.jswat.parser.java.lexer.LexerException;
import com.bluemarsh.jswat.parser.java.node.Token;
import com.bluemarsh.jswat.parser.java.parser.ParserException;

/**
 *测试结果:
 *1、源码中加入注解，将会导致解析失败
 *2、不能解析出变量
 *3、源码必须语法正确，语义不做要求
 *
 * @author hou
 *
 */
public class ParserMain {

	public static void main(String[] args) throws IOException {
		
		
		String fileName = 
				"J:\\ecplise工作区\\anyviewj\\src\\anyviewj\\outline\\TestDemo1.java";
		
		FileReader fr = new FileReader( fileName );
		char[] tmp = new char[10240];
		
        // Read in the class definitions from the source file.
		int len = fr.read( tmp );
        CharArrayReader car = new CharArrayReader( tmp, 0, len );
        System.out.println( car );
        
		JavaParser javaParser = new JavaParser(car);
		try {
			javaParser.parse();
			
		} catch (LexerException e) {
			e.printStackTrace();
		} catch (ParserException e) {
			
			
			e.printStackTrace();
            // Highlight the line with the error.
            Token token = e.getToken();
            // Translate absolute to relative numbering.
            int badline = token.getLine() - 1;
            int badpos = token.getPos();
		}
		System.out.println( "-------------------1" );
		
		ArrayList classLines = ( ArrayList<?>)javaParser.getClassLines();
		ArrayList methodLines = ( ArrayList<?>)javaParser.getMethodLines();
		
		for ( int i=0; i<classLines.size(); i++ )
		{
			System.out.println( "class--" + classLines.get(i) );
		}
		for ( int i=0; i<methodLines.size(); i++ )
		{
			System.out.println( "method_i " + methodLines.get( i ) );
		}
		
		System.out.println( "--------------------------------end" );
	}
	
	
	public void f0(){}
	public void f1(){}
	public void f2(){}
}
