package address;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class MedianFilter extends Filter{
	
	public MedianFilter(String path,int size){
		super(path,size);
	}
	
	public MedianFilter(Mat matrix,int size){
		super(matrix,size);
	}
	
	@Override
	public void filtrImage(){
		Imgproc.medianBlur(imageMatrix,imageMatrix,size);
	}
}
