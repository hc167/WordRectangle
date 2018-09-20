import java.io.*;
import java.util.*;

class MainApp {
    public static void main(String[] args) {

	wph = new WordProcessHelper();

	try{
	    BufferedReader in = new BufferedReader(new FileReader("WORD.LST"));

	    String text;

	    while (in.ready()) {		
		text = in.readLine();
		wph.addWord(text);
	    }
	    
	    in.close();
	}
	catch (FileNotFoundException e) {
            System.out.println("File not found");
        } 
	catch (IOException e) {
            System.out.println("Other IO exception");
        }

	System.out.println("Finish reading file");

	/*
	for(int i=0; i<wph.getWordListSize(); ++i){
	    WordGroup wg = wph.getWordGroup(i);

	    for(int j=0; j<wg.getWordCount(); ++j){
		System.out.println(wg.getWord(j));

	    }
	    }*/
	
	wph.ProcessRectangle();

    }

    private static WordProcessHelper wph;
}