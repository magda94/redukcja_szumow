package address;

import java.util.Arrays;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class AdaptiveMedianFilter extends Filter{
	
	public AdaptiveMedianFilter(Mat matrix, int size){
		super(matrix,size);
	}
	
	public AdaptiveMedianFilter(String path, int size) throws Exception{
		super(path,size);
	}
	
	public void filtrImage(){
		//Imgproc.cvtColor(imageMatrix, imageMatrix, Imgproc.COLOR_BGR2GRAY);
		Mat tempMatrix = new Mat(imageMatrix.rows(),imageMatrix.cols(),imageMatrix.type());
		double [] medianPixel;
		//go to for each pixel in image (without first and last row and col)
		for(int i=1; i<imageMatrix.rows()-1; i++){
			for(int j=1; j<imageMatrix.cols()-1; j++){
				double [] pixel = imageMatrix.get(i,j);
				int pixelSize = pixel.length;
				medianPixel = new double[pixelSize];
				//check every value for pixel
				for(int k=0;k<pixelSize;k++){
					int windowSize = size;
					double minValue;
					double maxValue;
					int addToSize = (int)(windowSize/2);
					//LEVEL A
					do{
						
						//addToSize++;
						double [] tempMedianArray = new double [windowSize*windowSize];
						System.out.println("TABLICA ROZMIAR ZAINICJALIZOWANA: "+tempMedianArray.length);
						int nrElement = 0;
						
						//take value of neighbor pixels
						//for(int m=-1-addToSize;m<2+addToSize;m++){
						//	for(int n=-1-addToSize;n<2+addToSize;n++){
						  for(int m=-addToSize; m<=addToSize;m++){
							  for(int n=-addToSize; n<=addToSize;n++){
								if(i+m<0 && i+n<0){
									tempMedianArray[nrElement] = imageMatrix.get(i+2*m,j+2*n)[k];
									nrElement++;
									continue;
								}
								if(i+m<0) {
									if (j+n<imageMatrix.cols()-1)
										tempMedianArray[nrElement] = imageMatrix.get(i+2*m,j+n)[k];
									else
										tempMedianArray[nrElement] = imageMatrix.get(i+2*m,j)[k];
									nrElement++;
									continue;
								}
								if(i+n<0) {
									if (j+m<imageMatrix.rows()-1)
										tempMedianArray[nrElement] = imageMatrix.get(i+m,j+2*n)[k];
									else
										tempMedianArray[nrElement] = imageMatrix.get(i,j+2*n)[k];
									nrElement++;
									continue;
								}
								if(i+m>imageMatrix.rows()-1 && j+n>imageMatrix.cols()-1){
									tempMedianArray[nrElement] = imageMatrix.get(i-m,j-n)[k];
									nrElement++;
									continue;
								}
								if(i+m>imageMatrix.rows()-1){
									tempMedianArray[nrElement] = imageMatrix.get(i-m,j+n)[k];
									nrElement++;
									continue;
								}
								if(j+n>imageMatrix.cols()-1){
									tempMedianArray[nrElement] = imageMatrix.get(i+m,j-n)[k];
									nrElement++;
									continue;
								}
								//System.out.println(imageMatrix.get(i+m,j+n)[k]);
								tempMedianArray[nrElement] = imageMatrix.get(i+m,j+n)[k];
								nrElement++;
							}
						}
						Arrays.sort(tempMedianArray);
						System.out.println("ROZMIAR TABLICY: "+ tempMedianArray.length);
						if(nrElement%2 ==1)
								medianPixel[k]=tempMedianArray[(int)(nrElement-1)/2];
						else
							medianPixel[k]= (tempMedianArray[nrElement/2] + tempMedianArray[nrElement/2 -1])/2;
						minValue = tempMedianArray[0];
						System.out.println(Arrays.toString(tempMedianArray));
						maxValue = tempMedianArray[nrElement-1];
						//maxValue = tempMedianArray[tempMedianArray.length-1];
						System.out.println("DLA PIXELA: ("+i+", "+j+")  MEDIANA: "+medianPixel[k]+"  MIN: "+minValue+" MAX: "+maxValue);
						System.out.println("Median-MIN= "+(medianPixel[k]-minValue));
						System.out.println("Median-MAX= "+(medianPixel[k]-maxValue));
						//check conditions from LEVEL A
						windowSize += 2;
						addToSize = (int)(windowSize/2);
					}while(medianPixel[k]-minValue<=0 && medianPixel[k]-maxValue>=0);
					//LEVEL B
					if(pixel[k]-minValue>0 && pixel[k]-maxValue<0){
						medianPixel[k]=pixel[k];
					}
				}
				//tempMatrix.put(i,j,medianPixel);
				imageMatrix.put(i, j, medianPixel);
			}
		}
		//imageMatrix=tempMatrix;
	}
}
