public class WordRectangleHelper
{
    public WordRectangleHelper(int input1, int input2, 
			       WordGroup workgroup1, WordGroup workgroup2)
    {
        size1 = input1;
        size2 = input2;
        product = input1 *input2;

	wg1 = workgroup1;
	wg2 = workgroup2;
    }

    public int getRectangleArea()
    {
        return product;
    }

    public String getRectangleAreaInString()
    {
	return size1 + "x" + size2;
    }

    public WordGroup getWordGroup1()
    {
	return wg1;
    }

    public WordGroup getWordGroup2()
    {
	return wg2;
    }

    private int size1;
    private int size2;
    private int product;

    private WordGroup wg1;
    private WordGroup wg2;
}
