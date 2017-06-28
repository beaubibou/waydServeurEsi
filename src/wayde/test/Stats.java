package wayde.test;

public class Stats {

public static int nbRequete=0; 
public static long ref=System.currentTimeMillis();
public static double stat;
public static double getPerf(){
	
	nbRequete++;
	long echanti=System.currentTimeMillis();
	long diff=echanti-ref;
	if (diff>1000){//Temps superieur à 1 secon
	
		stat=nbRequete*1000/diff;
		nbRequete=0;
		ref=System.currentTimeMillis();
		
	}
	
	return stat;
}
}
