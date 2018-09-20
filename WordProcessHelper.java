import java.util.*;
import java.lang.*;

public class WordProcessHelper{

    public WordProcessHelper()
    {
	wordGroupList = new ArrayList<WordGroup>();
    }

    public int getWordListSize()
    {
	return wordGroupList.size();
    }

    public WordGroup getWordGroup(int index)
    {
	return wordGroupList.get(index);
    }

    public void addWord(String word)
    {
	int size = word.length();

	boolean checkWG=false;

	for(int i=0; i<wordGroupList.size(); ++i){
	    WordGroup wg = wordGroupList.get(i);

	    if(size == wg.getWordGroupLetterCount()){
		wg.insertWord(word);
		checkWG = true;
		break;
	    }
	}

	if(!checkWG){
	    WordGroup tempwg = new WordGroup(size);
	    tempwg.insertWord(word);
	    
	    boolean checkList = false;
	    for(int i=0; i<wordGroupList.size();++i){
		int GLsize = wordGroupList.get(i).getWordGroupLetterCount();
		if(GLsize < size){
		    wordGroupList.add(i, tempwg);
		    checkList = true;
		    break;
		}
	    }

	    if(!checkList)
		wordGroupList.add(tempwg);
	}
    }

    private Vector<String> checkIfWGPossible(WordGroup wg1, WordGroup wg2, String [] columePrefix, 
				      int columeLength, int row, int colume, String prefix_check)
    {
	String newchar = wg2.getNextPossibleChar(columePrefix[colume]);
	String teststring = prefix_check;

	Vector<String> wordList = new Vector<String>();

	if(newchar == null)
	    return wordList;

	for(int j=0; j<newchar.length(); ++j){
	    teststring = prefix_check + newchar.charAt(j);

	    if(wg1.hasPrefixInWordGroup(teststring)){

		if(colume == columeLength - 1)
		    wordList.add(teststring);
		else{
		    Vector<String> wordList2 = 
			checkIfWGPossible(wg1, wg2, columePrefix, columeLength, row, colume+1, teststring);

		    for(int m=0; m<wordList2.size(); ++m)
			wordList.add(wordList2.elementAt(m));
		}
	    }
	}

	return wordList;
    }

    private WordGroup checkIfRectanglePossible(WordGroup wg1, WordGroup wg2, int row)
    {
	int columeLetterCount = wg1.getWordGroupLetterCount();

	String [] columePrefix = new String[columeLetterCount];

	// construct the prefix String object for each colume
	for(int i=0; i<columeLetterCount; ++i){
	    columePrefix[i] = "";

	    for(int k=0; k<=row; ++k)
		columePrefix[i] = columePrefix[i] + test_rect[k].charAt(i);
	}

	Vector<String> wordList = checkIfWGPossible(wg1, wg2, columePrefix, columeLetterCount, row, 0, "");

	if(wordList.size() == 0)
	    return null;
	else{
	    WordGroup wg = new WordGroup(wg1.getWordGroupLetterCount());
	    wg.setWordsAndSorted(wordList);

	    return wg;
	}
    }

    private boolean CalRect(WordGroup wg1, WordGroup wg2, int row, WordGroup wg3)
    {
	int row_count = wg3.getWordGroupLetterCount();

	for(int i=0; i < wg3.getWordCount(); ++i){
	    test_rect[row] = wg3.getWord(i);

	    if(row == rect_height - 1) // we reach the last row and all conditions satisfy.
		return true;
	    
	    // before we start calculate the next row by calling it recursively, we check if the current
	    // row has the potential fit for the word rectangle.
	    
	    WordGroup new_wg = checkIfRectanglePossible(wg1, wg2, row);
	    
	    if(new_wg == null){
		test_rect[row] = null;
		continue;
	    }

	    if(CalRect(wg1, wg2, row+1, new_wg))
		return true;
	    else{
		test_rect[row] = null;
		continue;
	    }
	}
	return false;
    }

    private boolean CalculateRectangle(WordGroup wg1, WordGroup wg2)
    {
	if(wg1.getWordCount() < wg2.getWordGroupLetterCount() ||
	   wg2.getWordCount() < wg1.getWordGroupLetterCount()){
	    System.out.println("Too few words to form a rectangle");
	    return false;
	}
	
	test_rect= null;
	rect_height = 0;

	if(wg1.getWordCount() < wg2.getWordCount()){
	    rect_height = wg2.getWordGroupLetterCount();
	    test_rect = new String [rect_height];

	    boolean cond = CalRect(wg1, wg2, 0, wg1);
	    if(cond){
		//print the rectangle
		for(int i = 0; i<rect_height; ++i)
		    System.out.println(test_rect[i]);
	    }
	    return cond;
	}
	else{
	    rect_height = wg1.getWordGroupLetterCount();
	    test_rect = new String [rect_height];

	    boolean cond = CalRect(wg2, wg1, 0, wg2);
	    if(cond){
		//print the rectangle
		for(int i = 0; i<rect_height; ++i)
		    System.out.println(test_rect[i]);
	    }
	    return cond;
	}
    }

    public boolean ProcessRectangle()
    {
	int[] size = new int[wordGroupList.size()];

	Vector<WordRectangleHelper> wrh_vector = new Vector<WordRectangleHelper>();

	for(int i=0; i<wordGroupList.size(); ++i){
	    size[i] = wordGroupList.get(i).getWordGroupLetterCount();
	}

	for(int i=0; i<wordGroupList.size();++i){
	    for(int j=i; j<wordGroupList.size(); ++j){

		WordGroup wg1 = null;
		WordGroup wg2 = null;
		for(int k=0; k< wordGroupList.size(); ++k){
		    if(size[i] == wordGroupList.get(k).getWordGroupLetterCount()){
			wg1 = wordGroupList.get(k);
			break;
		    }
		}

		for(int k=0; k< wordGroupList.size(); ++k){
		    if(size[j] == wordGroupList.get(k).getWordGroupLetterCount()){
			wg2 = wordGroupList.get(k);
			break;
		    }
		}
			
		WordRectangleHelper wrh = new WordRectangleHelper(size[i], size[j], wg1, wg2);
		wrh_vector.add(wrh);
	    }
	}

	Vector<WordRectangleHelper> wrh_vector2 = new Vector<WordRectangleHelper>();

	for(int i=0; i<wrh_vector.size(); ++i){

	    WordRectangleHelper wr_helper = wrh_vector.elementAt(i);
	    boolean check = false;

	    for(int j=0; j<wrh_vector2.size(); ++j){
		if(wrh_vector2.elementAt(j).getRectangleArea() < wr_helper.getRectangleArea()){
		    wrh_vector2.add(j, wr_helper);
		    check = true;
		    break;
		}
	    }
	    if(!check)
		wrh_vector2.add(wr_helper);
	}

	wrh_vector.removeAllElements();

	for(int i=0; i<wrh_vector2.size(); ++i){
	    WordRectangleHelper rect = wrh_vector2.elementAt(i);

	    System.out.println("Trying rectangle area " + 
			       rect.getRectangleArea() + " rect: " +
			       rect.getRectangleAreaInString());

	    if(CalculateRectangle(rect.getWordGroup1(), rect.getWordGroup2()))
		return true;
	}
	return false;
    }

    private List<WordGroup> wordGroupList;
    private int rect_height;
    private String [] test_rect;
}
