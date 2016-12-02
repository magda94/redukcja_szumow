package address;

import org.opencv.core.Mat;

public class BrightnessChanger extends Filter{

	double beta;
	
	public BrightnessChanger(Mat matrix, int size, double beta){
		super(matrix,size);
		this.beta = beta;
	}
	
	public BrightnessChanger(String path, int size, double beta) throws Exception{
		super(path,size);
		this.beta = beta;
	}
	
	public void filtrImage(){
		imageMatrix.convertTo(imageMatrix, -1, 1, beta);
	}
}
