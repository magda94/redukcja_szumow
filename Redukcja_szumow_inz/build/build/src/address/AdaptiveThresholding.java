package address;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class AdaptiveThresholding extends Filter{
	
	double constantC;
	
	public AdaptiveThresholding(Mat matrix, int size, double constantC){
		super(matrix, size);
		this.constantC = constantC;
	}
	
	public AdaptiveThresholding(String path, int size, double constantC) throws Exception{
		super(path, size);
		this.constantC = constantC;
	}
	
	public void filtrImage(){
		if(imageMatrix.channels()>1)
			Imgproc.cvtColor(imageMatrix, imageMatrix, Imgproc.COLOR_BGR2GRAY);
		Imgproc.adaptiveThreshold(imageMatrix, imageMatrix, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, size, constantC);
	}
}
