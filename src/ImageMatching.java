
public class ImageMatching {
	static final int[] move_X={1,0,-1,0}; //Right, Down, Left, Up
	static final int[] move_Y={0,1,0,-1};
	public static void main(String[] args) {
		int[] img1={0,0,1,1,1,0,1,0,0};
		int[] img2={0,0,1,1,1,0,1,0,1};
		int[] img3={0,0,1,0,1,1,1,0,0};
		int[] img4={0,0,1,0,1,1,1,0,1};
		int[] img5={0,0,1,1,0,1,0,0,0,1,1,0,0,1,0,1};
		int[] img6={0,0,1,1,0,1,0,0,0,1,1,1,0,1,0,1};
		//test eliminateRegion
		/*
		int[][] img={{0,0,1},{1,1,0},{1,0,0}};
		int[][] IMG=eliminateRegion(img, 1, 0);	
		 */
		//test isSameRegion
		/*
		int[][] img1={{1,0,1},{1,1,0},{1,0,0}};
		int[][] img2={{0,0,1},{1,1,0},{1,0,1}};
		System.out.print(isSameRegion(img1,img2,1,0));
		*/
		System.out.println(checkMatching(img1,img2));
		System.out.println(checkMatching(img3,img4));
		System.out.println(checkMatching(img5,img6));
	}

	private static int checkMatching(int[] img1, int[] img2) {
		int size=(int) Math.sqrt(img1.length);
		int[][] IMG1=new int[size][size];
		int[][] IMG2=new int[size][size];
		for (int i=0;i<size;i++){
			for (int j=0;j<size;j++){
				IMG1[j][i]=img1[i+size*j];
				IMG2[j][i]=img2[i+size*j];
			}
		}
		int count=0;
		for (int row=0;row<size;row++){
			for (int col=0;col<size;col++){
				if ((IMG1[row][col]|IMG2[row][col])==1){//one of the grid is 1
					if ((IMG1[row][col]&IMG2[row][col])==1){//both are 1
						if(isSameRegion(IMG1,IMG2,row,col)){
							count++;
							
						}else{
							IMG1=eliminateRegion(IMG1,row,col);
							IMG2=eliminateRegion(IMG2,row,col);
						}
					}
					else if(IMG1[row][col]==1){
						IMG1=eliminateRegion(IMG1,row,col);
					}else if(IMG2[row][col]==1){
						IMG2=eliminateRegion(IMG2,row,col);
					}
				}
			}
		}
		return count;
	}
	
	private static boolean isSameRegion(int[][] IMG1, int[][] IMG2, int row, int col) {
		if (IMG1[row][col]!=IMG2[row][col]){return false;}
		IMG1[row][col]=2;IMG2[row][col]=2;
		for (int dir =0; dir<4;dir++){
			int nextRow=row+move_Y[dir];
			int nextCol=col+move_X[dir];
			
			if(isValid(IMG1.length,nextRow,nextCol)){
				if (IMG1[nextRow][nextCol]!=IMG2[nextRow][nextCol]){
					return false;
				} else if ((IMG1[nextRow][nextCol]&IMG2[nextRow][nextCol])==1){
					 if (isSameRegion(IMG1, IMG2, nextRow, nextCol)){
						 continue;
					 }else {return false;}
				} 
			}			
		}
		return true;
	}

	private static int[][] eliminateRegion(int[][] IMG, int row, int col) {
		if (IMG[row][col]==0){
			return IMG;
		}
		IMG[row][col]=0;
		for (int dir =0; dir<4;dir++){
			int nextRow=row+move_Y[dir];
			int nextCol=col+move_X[dir];
			if(isValid(IMG.length,nextRow,nextCol)&&IMG[nextRow][nextCol]!=0){
				
				IMG=eliminateRegion(IMG,nextRow,nextCol);
			}else{
				continue;
			}
		}
		return IMG;
		
	}

	private static boolean isValid(int size, int row, int col){
		return (row<size && row>=0 && col<size && col>=0);
	}

}
