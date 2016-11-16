package address;

import java.util.Arrays;

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
		double [] medianPixel;
		//go to for each pixel in image (without first and last row and col) NOW WORK ONLY FOR 3x3 MATRIX!!
		for(int i=1; i<imageMatrix.rows()-1; i++){
			for(int j=1; j<imageMatrix.cols()-1; j++){
				double [] pixel = imageMatrix.get(i,j);
				int pixelSize = pixel.length;
				medianPixel = new double[pixelSize];
				//check every value for pixel
				for(int k=0;k<pixelSize;k++){
					int windowSize = size-1;
					double minValue;
					double maxValue;
					int addToSize = -1;
					//LEVEL A
					do{
						windowSize += 2;
						addToSize++;
						double [] tempMedianArray = new double [windowSize*windowSize];
						int nrElement = 0;
						
						//take value of neighbor pixels
						for(int m=-1-addToSize;m<2+addToSize;m++){
							for(int n=-1-addToSize;n<2+addToSize;n++){
								if(i+m<0 || j+n<0 || i+m>imageMatrix.rows()-1 || j+n>imageMatrix.cols()-1){
									//tempMedianArray[nrElement] = 255;
									//nrElement++;
									continue;
								}
								tempMedianArray[nrElement] = imageMatrix.get(i+m,j+n)[k];
								nrElement++;
							}
						}
						Arrays.sort(tempMedianArray);
						medianPixel[k]=tempMedianArray[(int)(nrElement-1)/2];
						minValue = tempMedianArray[0];
						Arrays.toString(tempMedianArray);
						maxValue = tempMedianArray[nrElement-1];
						//maxValue = tempMedianArray[tempMedianArray.length-1];
						
						//check conditions from LEVEL A
					}while(medianPixel[k]-minValue<=0 && medianPixel[k]-maxValue>=0);
					//LEVEL B
					if(pixel[k]-minValue>0 && pixel[k]-maxValue<0){
						medianPixel[k]=pixel[k];
					}
				}
				tempMatrix.put(i,j,medianPixel);
			}
		}
		imageMatrix=tempMatrix;
	}
}
