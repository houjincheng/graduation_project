package anyviewj.client.database;

public class chineseTransformer {
  
	private String chapter;
	private String exercise;
	
	private static String LinkList = "ÏßÐÔ±í";
	private static String Graph = "Í¼";
	private static String Tree = "Ê÷";
	private static String linklist = "LinkListTest";
	
	
	public chineseTransformer(String chapter, String exercise){
		this.chapter = chapter;
		this.exercise = exercise;
		
		if(searchNames(chapter,exercise))
			System.out.println("Not exist !");
	}
	
	public boolean searchNames(String c, String e){
		
		if(c.equals(Tree))
		{
			chapter = "Tree";
			exercise = "Tree";
			return true;
		}
		if(c.equals(LinkList))
		{
			chapter = linklist;
			exercise = linklist;
			return true;
		}
		
		return false;
	}
	
	
	public String returnChapter(){
		return chapter;
	}
	
	public String returnExercise(){
		return exercise;
	}
}
