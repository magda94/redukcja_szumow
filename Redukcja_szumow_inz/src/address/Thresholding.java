package address;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class Thresholding extends Filter{
	
	double threshold;

	public Thresholding(Mat matrix, int size, double threshold){
		super(matrix,size);
		this.threshold = threshold;
	}
	
	public Thresholding(String path, int size, double threshold) throws Exception{
		super(path,size);
		this.threshold = threshold;
	}
	
	public void filtrImage(){
		if(imageMatrix.channels()>1)
			Imgproc.cvtColor(imageMatrix, imageMatrix, Imgproc.COLOR_BGR2GRAY);
		Imgproc.threshold(imageMatrix, imageMatrix, threshold, 255, Imgproc.THRESH_BINARY);
	}
}
