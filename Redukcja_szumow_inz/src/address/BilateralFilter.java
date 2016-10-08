package address;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class BilateralFilter extends Filter{
	
	double sigmaColor;
	double sigmaSpace;

	public BilateralFilter(String path,int size, double sigmaColor, double sigmaSpace) throws Exception{
		super(path,size);
		this.sigmaColor=sigmaColor;
		this.sigmaSpace=sigmaSpace;
	}
	
	public BilateralFilter(Mat matrix,int size, double sigmaColor, double sigmaSpace){
		super(matrix,size);
		this.sigmaColor=sigmaColor;
		this.sigmaSpace=sigmaSpace;
	}
	
	public void filtrImage(){
		Imgproc.bilateralFilter(imageMatrix, imageMatrix, size, sigmaColor, sigmaSpace);
	}
}
