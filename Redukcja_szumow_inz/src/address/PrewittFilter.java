package address;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class PrewittFilter extends Filter{
	
	boolean vertical=false;
	boolean horizontal=false;

	public PrewittFilter(Mat matrix, int size, boolean vertical, boolean horizontal){
		super(matrix,size);
		this.vertical=vertical;
		this.horizontal=horizontal;
	}
	
	public PrewittFilter(String path, int size, boolean vertical, boolean horizontal) throws Exception{
		super(path,size);
		this.vertical=vertical;
		this.horizontal=horizontal;
	}
	
	public void filtrImage(){
		//Mat grayImage=new Mat(imageMatrix.rows(),imageMatrix.cols(),CvType.CV_8U);
		//Imgproc.cvtColor(imageMatrix, grayImage,Imgproc.COLOR_BGR2GRAY);
		Mat filtrMatrix= prepareMatrix();
		Imgproc.filter2D(imageMatrix,imageMatrix, -1, filtrMatrix);
		//Imgproc.filter2D(grayImage,grayImage, -1, filtrMatrix);
		//imageMatrix=grayImage;
	}
	
	private Mat prepareMatrix(){
		Mat matrix = null;
		//for size = 3
		if(size==3){
			if(vertical && horizontal){
				
			}else if(vertical){
				matrix=new Mat(3,3,CvType.CV_32F){
					{
						   put(-1,-1,-1);
			               put(0,0,0);
			               put(1,1,1);

					}
				};
			}else if(horizontal){
				matrix = new Mat(3,3,CvType.CV_32F){
					{
						put(-1,0,1);
						put(-1,0,1);
						put(-1,0,1);
					}
				};
			}
			
		}
		//for size = 5
		if(size==5){
			
		}
		//for size = 7
		if(size==7){
			
		}
		return matrix;
	}
}
