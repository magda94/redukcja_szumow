package address;

import org.opencv.core.Mat;

public class AdaptiveMedianFilter extends Filter{
	
	public AdaptiveMedianFilter(Mat matrix, int size){
		super(matrix,size);
	}
	
	public AdaptiveMedianFilter(String path, int size) throws Exception{
		super(path,size);
	}
	
	public void filtrImage(){
		Mat tempMatrix = new Mat(imageMatrix.rows(),imageMatrix.cols(),imageMatrix.type());
		
		imageMatrix=tempMatrix;
	}
}
