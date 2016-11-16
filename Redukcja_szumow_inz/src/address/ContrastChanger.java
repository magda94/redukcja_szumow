package address;

import org.opencv.core.Mat;

public class ContrastChanger extends Filter{
	
	double alpha;

	public ContrastChanger(Mat matrix, int size, double alpha){
		super(matrix, size);
		this.alpha = alpha;
	}
	
	public ContrastChanger(String path, int size, double alpha) throws Exception{
		super(path,size);
		this.alpha = alpha;
	}
	
	public void filtrImage(){
		imageMatrix.convertTo(imageMatrix, -1,alpha+1,0);
	}
}
