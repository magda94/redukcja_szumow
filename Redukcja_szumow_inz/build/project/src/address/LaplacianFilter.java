package address;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class LaplacianFilter extends Filter{

	double scale;
	double delta;
	
	public LaplacianFilter(String path,int size,double scale,double delta) throws Exception{
		super(path,size);
		this.scale=scale;
		this.delta=delta;
	}
	
	public LaplacianFilter(Mat matrix,int size, double scale, double delta){
		super(matrix,size);
		this.scale=scale;
		this.delta=delta;
	}
	
	public void filtrImage(){
		Imgproc.Laplacian(imageMatrix, imageMatrix, CvType.CV_8U ,size, scale, delta);
	}
}
