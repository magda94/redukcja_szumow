package address;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class CannyFilter extends Filter{
	
	double lowerTh;
	double upperTh;

	public CannyFilter(String path, int size,double lowerTh,double upperTh) throws Exception{
		super(path,size);
		this.lowerTh=lowerTh;
		this.upperTh=upperTh;
	}
	
	public CannyFilter(Mat matrix, int size,double lowerTh, double upperTh){
		super(matrix,size);
		this.lowerTh=lowerTh;
		this.upperTh=upperTh;
	}
	
	public void filtrImage(){
		Imgproc.Canny(imageMatrix, imageMatrix, lowerTh, upperTh, size, false);
	}
}
