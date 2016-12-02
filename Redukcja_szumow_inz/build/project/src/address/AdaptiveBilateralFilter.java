package address;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;


public class AdaptiveBilateralFilter extends Filter{
	
	double sigmaSpace;

	public AdaptiveBilateralFilter(String path, int size, double sigmaSpace) throws Exception{
		super(path,size);
		this.sigmaSpace = sigmaSpace;
	}
	
	public AdaptiveBilateralFilter(Mat matrix, int size, double sigmaSpace){
		super(matrix,size);
		this.sigmaSpace = sigmaSpace;
	}
	
	public void filtrImage(){
		Mat tempMatrix=new Mat(imageMatrix.rows(),imageMatrix.cols(),imageMatrix.type());
		Imgproc.adaptiveBilateralFilter(imageMatrix, tempMatrix, new Size(size,size), sigmaSpace);
		imageMatrix = tempMatrix;
	}
}
