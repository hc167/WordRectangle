import java.util.*;
import java.lang.*;

public class WordGroup
{
    public WordGroup(int letterCount)
    {
	LetterCount = letterCount;
	wordList = new Vector<String>();
    }

    public int getWordGroupLetterCount()
    {
	return LetterCount;
    }

    public boolean insertWord(String word)
    {
	return wordList.add(word);
    }

    public void setWords(Vector<String> strings)
    {
	wordList.removeAllElements();
	for(int i=0; i<strings.size(); ++i){
	    wordList.add(strings.elementAt(i));
	    assert LetterCount == strings.elementAt(i).length();
	}
    }

    public void setWordsAndSorted(Vector<String> strings)
    {
	wordList.removeAllElements();
	for(int i=0; i<strings.size(); ++i){
	    boolean inserted = false;
	    for(int j = 0; j<wordList.size(); ++j){
		if(wordList.elementAt(j).compareTo(strings.elementAt(i)) > 0){
		    wordList.insertElementAt(strings.elementAt(i), j);
		    inserted = true;
		    break;
		}
	    }
	    if(!inserted)
		wordList.add(strings.elementAt(i));
	}
    }

    public int getWordCount()
    {
	return wordList.size();
    }

    public String getWord(int index)
    {
	return wordList.elementAt(index);
    }

    public String getNextPossibleChar(String prefix)
    {
	String output = null;

        int front = 0;
        int back = getWordCount()-1;
        int mid_point = (front+back)/2;
        int prefix_size = prefix.length();
	String teststring = null;

	if(getWordGroupLetterCount() <= prefix_size)
	    return null;

	boolean test = false;

        while(front <= back){
            mid_point = (front + back)/2;

            teststring = getWord(mid_point).substring(0, prefix_size);

            int compare = prefix.compareTo(teststring);
            if(compare < 0)
                back = (back == mid_point)? mid_point - 1 : mid_point;
            else if(compare > 0)
                front = (front == mid_point) ? mid_point + 1 : mid_point;
            else{
		test = true;
                break;
	    }
        }

	if(!test)
	    return null;

	while (prefix.compareTo(getWord(mid_point).substring(0, prefix_size)) == 0){
	    --mid_point;
	    if(mid_point < 0)
		break;
	}

	++mid_point;
	assert mid_point >= 0 && mid_point<= getWordCount()-1;

	char lastchar;

	while (prefix.compareTo(getWord(mid_point).substring(0, prefix_size)) == 0){
	    lastchar = getWord(mid_point).charAt(prefix_size);

	    if(output == null)
		output = "" + lastchar;
	    else if(output.charAt(output.length()-1) != lastchar){
		output += lastchar;
	    }

	    ++mid_point;
	    if(mid_point > getWordCount()-1 )
		break;
	}

	assert mid_point >= 0 && mid_point<= getWordCount()-1;

	return output;
    }

    public int getLastPrefixIndex(String prefix_String, int index)
    {
        if(index >= getWordCount() - 1)
            return index;

        int prefix_size = prefix_String.length();

        String oldString = getWord(index).substring(0, prefix_size);
        String nextString = getWord(index+1).substring(0, prefix_size);

        int front = index;
        int back = getWordCount() - 1;

        boolean passover = false;

        while(front < back){

            if(passover)
                index = (front + back)/2;
            else{
                index = index + 10;
                if(index >= back)
                    index = (front + back)/2;
            }

            if(index == back)
                break;
            oldString = getWord(index).substring(0, prefix_size);
            nextString = getWord(index+1).substring(0, prefix_size);

            if( prefix_String.compareTo(oldString) != 0){
                passover = true;
                back = index-1;
            }
            else if( prefix_String.compareTo(nextString) == 0)
                front = index+1;
            else
                break;
        }

        return index;
    }

    public boolean hasPrefixInWordGroup(String prefix)
    {
        int front = 0;
        int back = getWordCount() - 1;
        int mid_point;
        int key_size = prefix.length();

        while(front <= back){
            mid_point = (front + back)/2;

            String teststring = getWord(mid_point).substring(0, key_size);

            int compare = prefix.compareTo(teststring);
            if(compare < 0)
                back = (mid_point == back)?mid_point-1:mid_point;
            else if(compare > 0)
                front = (mid_point == front)?mid_point+1:mid_point;
            else
                return true;
        }
        return false;
    }

    private Vector<String> wordList;
    private int LetterCount;
}
