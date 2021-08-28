import java.util.*;
import java.io.*;

public class Test{
	public static void main (String [] args) throws IOException {
		FileReader fr = new FileReader("jj.txt");
		BufferedReader br = new BufferedReader(fr);
		int i=0;
		String [] sp;
		while (br.ready()) {
			p[i]=new Problem();
			String tem;
			tem=br.readLine();
			p[i].mainText=tem;
			System.out.println(p[i].mainText);

			tem=br.readLine();
		        sp=tem.split("\\s+");
			p[i].left=sp[0];
			System.out.println(p[i].left);
			for(int j=0 ; j<4 ; j++){
				p[i].dVL[j]=Integer.valueOf(sp[j+1]);
				System.out.println(p[i].dVL[j]);
			}

			tem=br.readLine();
			sp=tem.split("\\s+");
			p[i].right=sp[0];
			System.out.println(p[i].right);
			for(int j=0 ; j<4 ; j++){
				p[i].dVR[j]=Integer.valueOf(sp[j+1]);
				System.out.println(p[i].dVR[j]);
			}
￼			
			i++;	
		}
		fr.close();
		br.close();

	}

}

class Problem {
	String mainText;
	String left;
	int [] dVL = new int [4];
	String right;
	int [] dVR = new int [4];
}
