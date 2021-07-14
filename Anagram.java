import java.util.* ;
import java.io.*;
import java.lang.*;

public class Anagram
{
	
		class Node{
			
			String val;
			Node next;
			
			public Node( String word){
				
				val = word;
				next = null;
			}
			
		}
		class hashnode{
			
			String key;
			Node head ;
			Node last ;
			
			public hashnode(String Sortedword, String word ){
				
				key = Sortedword;
				Node newNode = new Node(word);
				head = newNode;
				last= newNode;
				last.next = head.next;
				
			}
			
			
		}
		
	public class HashTable{
	
		// storing hashnodes as keys
		//and Node of first and last node as values of the key
		
		
		
		
		int Size;
		hashnode[] arr;
		
		
		
	
		public HashTable(int N){
			if(N<100)
				Size = 101;
			else if(N<1000 && N>=100)
				Size = 1009;
			else if(N<10000 && N>=1000)
				Size = 10007;
			else if(N>=10000 && N<=24971)
				Size = 24971; 
			else
				Size = 26003; 
			arr = new hashnode[Size];
		
		}
		
		private int hashf(String word){
			
			//implementing horner's rule
			
			int r=0;
			for(int i=word.length()-1; i>-1; i--)
				
				r = ( (int)word.charAt(i) + 256*r)%Size ; 
			
			return r;
			
			
		}
		
		private int h2(int t){
			
			if(Size<= 101)
				return (1+7-t%7);
			return (1+101-t%101);
		}
		
		public void insert(String Sortedword, String Word)
		{
			int t = hashf(Sortedword);
			
			
			if( arr[t] == null ){
				
				//if(Sortedword.equals("elno"))
						//System.out.println("hi1"+Word);
				arr[t] = new hashnode(Sortedword, Word);
				
				 
			}else if(arr[t].key.equals(Sortedword)){
				
				Node newNode =new Node(Word);
				arr[( t ) ].last.next = newNode;		
				arr[( t ) ].last = newNode;
			}
			else{
				
				//probing 
				int i = 1;
				while( arr[ (t + i*h2(t) ) % Size] != null && !arr[( t + i*h2(t) ) % Size].key.equals(Sortedword ))
					
					i++;
				int t2 = ( t + i*h2(t) ) % Size;
				//found the right spot to insert
				//if the SOrted word not present
				if(	arr[t2 ] == null ){
					//if(Sortedword.equals("elno"))
						//System.out.println("hi2"+Word);
					arr[t2] = new hashnode(Sortedword, Word);
				}	
				else{
					//System.out.println(10);
					//if the Sorted word present
					//if(Sortedword.equals("elno"))
						//System.out.println("hi3"+Word);
					Node newNode =new Node(Word);
					arr[t2].last.next = newNode;		
					arr[t2].last = newNode;
					
							
					
				}
					
				
				
			}
				
		}
		
		public hashnode hasWord(String Sortedword)
		{
			int t = hashf(Sortedword) ;
			//System.out.println(arr[t].key);
			if(arr[t] == null ){
				//System.out.println("one");
				return null;
			}
			else{
				
				if(arr[t].key.equals(Sortedword))
					return arr[t];
				
				else{
					
					int i=0; 
					while(arr[(t+ i*h2(t))%Size] != null &&  !arr[(t + i*h2(t))%Size].key.equals(Sortedword)){
						//System.out.println(arr[(t+ h2(i))%Size].key);
						i++;
					}
					int t2 = ( t + i*h2(t) ) % Size;
					if (arr[t2] == null){
						//System.out.println(arr[(t+ h2(i))%Size].key);
						//System.out.println("two");
						return null;
					}
					else
						return arr[t2];
					
				}
					
				
			}
				
		}
		
		
	}
	
	private void subset(String W, int startidx, String currset, Vector<String> res, Anagram.HashTable Vo){
		
		int n= W.length();
		
		//when max only two words possible
		
		if(n >=6 && n <9){
			
			//startidx is the first char with which our substring is made
			if(startidx == n ){
				
				//inserting only when the word exist in vocab
				if(currset.length()>=3 && currset.length()<=n-3 && Vo.hasWord(currset)!=null)
					res.add(currset);
				return ;
				
			}
			
			//TO add currset of lengths 3 to n-3 in res
			if(currset.length()>=3 && currset.length()<=n-3 && Vo.hasWord(currset)!= null)
				subset(W , n , currset, res, Vo);
			
			
			for(int i = startidx; i< n ; i++){
				
				if(currset.length()<n-3){
					if( i>startidx && W.charAt(i-1) == W.charAt(i) )
					
						continue;
					
				
					else{
						String temp1 =currset+ Character.toString(W.charAt(i));
						//System.out.println(res);
						subset(W, i+1, temp1, res, Vo);
					}
				}else{
					
					break;
				}
			}
			
			//when max three words possible
		}else if(n >=9 ){
			
			if(startidx == n ){
				
				if(currset.length()>=3 && currset.length()<=n-3 )
					res.add(currset);
				return ;
				
			}
			if(currset.length()>=3 && currset.length()<=n-3 )
				subset(W , n , currset, res ,Vo);
			
			for(int i = startidx; i< n ; i++){
				
				if(currset.length()<n-3){
					if( i>startidx && W.charAt(i-1) == W.charAt(i) )
						
						continue;
						
					
					else{
						
						String temp2 = currset+ Character.toString(W.charAt(i));
						
						subset(W, i+1, temp2, res, Vo);
					}
				}else
					break;
			}
			
			
		}else
			return ;
	
		
	}
	
	private String Complement(String InputWord, String sub){
		
		String Comp="";
		
		int idx=0;
		for(int j=0; j<InputWord.length(); j++){
			
			if(idx<sub.length() && Character.toString(InputWord.charAt(j)).equals(Character.toString(sub.charAt(idx))))
				idx++;
			else
				Comp = Comp + Character.toString(InputWord.charAt(j));
			
		}
		return Comp;
		
	}
	
	public Vector<Vector<String>> PartWords(String InputWord, Anagram.HashTable Vo){
		
		Vector<Vector<String>> SortedAnagrams = new Vector<Vector<String>>();
		
		
		// when 3 words are possible
		if(InputWord.length()>=9){
			
			Vector<String> temp ;
			Vector<String> subsets = new Vector<String>();
			
			subset(InputWord , 0 , "", subsets ,Vo);
			
			// this is the first word out of the three words
			for(int i=0; i< subsets.size(); i++){
				
				if(Vo.hasWord(subsets.get(i))==null)
					continue;
				else{
				
				temp = new Vector<String>(3);
				
				// this if consition not really needed but still used it
				if (subsets.get(i).length()>=3){
					
				
					if( InputWord.length()-subsets.get(i).length() >=6 ){
						
						Vector<String> subsets2 = new Vector<String> ();
						String t = Complement(InputWord, subsets.get(i));
						subset(t, 0, "", subsets2, Vo);
						
						//this will be the second word and its complement will be the third word
						for(int j=0; j< subsets2.size(); j++){
							
							if(Vo.hasWord(subsets2.get(j))==null)
								continue;
							else{
							// this condition also not needed but still used
							if (subsets2.get(j).length()>=3 ){
					
								Vector<String> tem = new Vector<String>(3);
								tem.add(subsets.get(i));
								tem.add(subsets2.get(j));
								tem.add(Complement(t , subsets2.get(j)));
								SortedAnagrams.add(tem);
					
							}
						
					}}
					}
					Vector<String> tem = new Vector<String>(2);
					tem.add(subsets.get(i));
					tem.add(Complement( InputWord ,subsets.get(i) ));
					SortedAnagrams.add(tem);
				}
		}}
			
			Vector<String> tem = new Vector<String>(1);
			tem.add(InputWord);
			SortedAnagrams.add(tem);
			
			return SortedAnagrams;
		}
		//when 2 or 1 word possible
		else if(InputWord.length()>=6 && InputWord.length()<9 ){
			
			Vector<String> subsets = new Vector<String>();
			subset(InputWord , 0 , "", subsets, Vo );
			//System.out.println(subsets);
			Vector<String> temp;
			for(int i=0; i< subsets.size(); i++){
				
				if(Vo.hasWord(subsets.get(i))==null)
					continue;
				else{
			
				temp = new Vector<String>(2);
				
				if (subsets.get(i).length()>=3){
					
					temp.add(subsets.get(i));
					temp.add(Complement(InputWord , subsets.get(i)));
					//System.out.println(Complement(InputWord , subsets.get(i)));
					SortedAnagrams.add(temp);
					
				}
			
		}}
			Vector<String> tem = new Vector<String>(1);
			tem.add(InputWord);
			SortedAnagrams.add(tem);
			
			return SortedAnagrams;
		}
		//only one word
		else {
			
			Vector<String> temp = new Vector<String>(1);
			temp.add(InputWord);
			
			SortedAnagrams.add( temp);
			
			return SortedAnagrams;
			
		}
		
		
		
	} 
	
	public static void main(String[] args)
	{
		//try{PrintStream o = new PrintStream(new File("Student_out1_1.txt"));
        //PrintStream console = System.out;
  
         //Assign o to output stream
        //System.setOut(o);
        //}
         //catch (FileNotFoundException ex)  
    //{
        // insert code to run when exception occurs
    //}
	try{
		//long t1= (System.currentTimeMillis());
		FileInputStream fs1 = new FileInputStream(args[0]);
		FileInputStream fs2 = new FileInputStream(args[1]);
		
		Scanner s1 = new Scanner(fs1);
		Scanner s2 = new Scanner(fs2);
	
		int VocabLen = s1.nextInt();
		
		int numInput = s2.nextInt();
		
		//creating instance of the nested class of hashtable
		Anagram V = new Anagram();
		Anagram.HashTable Vocab = V.new HashTable(VocabLen);
		
		for(int j = 0; j< VocabLen+1; j++){
			
			//System.out.println(j);
			String temp = s1.nextLine();
			
			if(temp.equals(""))
				continue;
			else{
			
			if( temp.length()<=12){
				char tempArray[] = temp.toCharArray();
			  
				
				Arrays.sort(tempArray);
			  
		
				String temp2 = String.valueOf(tempArray);
				//if(j==13294)
					//System.out.println(temp2);
				Vocab.insert(temp2, temp);
				//System.out.println(Vocab.hasWord(temp2).key);
			}
			
		}}
		//long t1= (System.currentTimeMillis());
		//hashnode l = Vocab.hasWord("elno");
		//	Node t= l.head;
		//	while(t!=null){
		//		System.out.println(t.val);
		//		t =t.next;
		//	}
		for(int i=0 ; i< numInput+1 ; i++){
			
			
			String input = s2.nextLine();
			
			if(input.equals(""))
				continue;
			else{
			
		
			char tempArray[] = input.toCharArray();
          
		
			Arrays.sort(tempArray);
          
	
			String sortedInput = String.valueOf(tempArray);
			
			
			Vector<Vector<String>> SortedWords = V.PartWords(sortedInput, Vocab);
			
			
			Vector<String> res = new Vector<String>();
			
			
			for(int k=0; k<SortedWords.size(); k++){
				
				
				if( SortedWords.get(k).size() == 3){
					
					
					hashnode word1 = Vocab.hasWord(SortedWords.get(k).get(0));
					
					hashnode word2 = Vocab.hasWord(SortedWords.get(k).get(1));
					hashnode word3 = Vocab.hasWord(SortedWords.get(k).get(2));
					
					if( word1 != null && word2 != null  && word3 != null ){
						Node temp1 = word1.head;
						
						Vector<String> Word1S = new Vector<String>();
						Vector<String> Word2S = new Vector<String>();
						Vector<String> Word3S = new Vector<String>();
						
						while( temp1 != null){
							
							Word1S.add(temp1.val);
							
							temp1 = temp1.next;
							
						}
						//Collections.sort(Word1Sorted);
						
						temp1 = word2.head;
						
						while( temp1 != null){
							
							Word2S.add(temp1.val);
							temp1 = temp1.next;
							
						}
						//Collections.sort(Word2Sorted);
						
						temp1 = word3.head;
						
						while( temp1 != null){
							
							Word3S.add(temp1.val);
							temp1 = temp1.next;
							
						}
						//Collections.sort(Word3Sorted);
						
						for(int p=0; p<Word1S.size(); p++){
							
							for(int q=0; q<Word2S.size(); q++){
								
								for(int r=0; r<Word3S.size(); r++){
									
									res.add(Word1S.get(p)+" "+Word2S.get(q)+" "+Word3S.get(r));
									
								}
							}
						}
						
					}
					
					
					
				}else if(SortedWords.get(k).size() == 2){
					
					hashnode word1 = Vocab.hasWord(SortedWords.get(k).get(0));
					hashnode word2 = Vocab.hasWord(SortedWords.get(k).get(1));
					
					if(word1 != null && word2!=null ){
						
						Node temp1 = word1.head;
						
						Vector<String> Word1S = new Vector<String>();
						Vector<String> Word2S = new Vector<String>();
						
						while( temp1 != null){
							
							Word1S.add(temp1.val);
							temp1 = temp1.next;
							
						}
						//Collections.sort(Word1Sorted);
						
						temp1 = word2.head;
						
						while( temp1 != null){
							
							Word2S.add(temp1.val);
							temp1 = temp1.next;
							
						}
						//Collections.sort(Word2Sorted);
						
						for(int p=0; p<Word1S.size(); p++){
							
							for(int q=0; q<Word2S.size(); q++){
								
								
									
								res.add(Word1S.get(p)+" "+Word2S.get(q));
								
								
							}
						}//System.out.println(res);
						
						
					}
					
				}else if(SortedWords.get(k).size() == 1){
					
					hashnode word1 = Vocab.hasWord(SortedWords.get(k).get(0));
					
					//System.out.println(word1);
					if(word1 != null ){
						
						Node temp1 = word1.head;
						
						Vector<String> Word1S = new Vector<String>();
						
						while( temp1 != null){
							
							Word1S.add(temp1.val);
							temp1 = temp1.next;
							
						}
						//Collections.sort(Word1Sorted);
						for(int p=0; p<Word1S.size(); p++){
							
							res.add(Word1S.get(p));
									
								
							
						}
						
					}
					
					
				}
				
				
			}
			
			Collections.sort(res);
			for(int a=0; a<res.size(); a++)
				
				System.out.println(res.get(a));
				
			System.out.println(-1);
			
			
			
		}}
		//long t2= (System.currentTimeMillis());
		//System.out.println(t2-t1);
	}
	catch(FileNotFoundException f){
		System.out.println("File not found");
	}
	}
	


}