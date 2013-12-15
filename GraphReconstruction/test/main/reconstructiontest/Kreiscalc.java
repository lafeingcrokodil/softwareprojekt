package maintest;
import java.util.LinkedList;



public class Kreiscalc {
	private Double r1,r2,a,b,c,d;
	
	public void setKreis1(Double x,Double y,Double r){
		this.r1=r;
		this.b=y;
		this.a=x;
	}
	public void setKreis2(Double x,Double y,Double r){
		this.r2=r;
		this.d=y;
		this.c=x;
	}
	
	public Integer anz_schnittp(){
		// TODO
		return 0;
	}
	
	public LinkedList<Tupel<Double,Double>> berechne_schnittp() {
		LinkedList<Tupel<Double,Double>> result = new LinkedList<Tupel<Double,Double>>();
		double p = a-c; 
		double q = b-d;
		double subterm1 = (-(p*p)-(q*q)+(r1-r2)*(r1-r2));
		double subterm2 = (-(p*p)-(q*q)+(r1+r2)*(r1+r2));
		double wurz = Math.sqrt(-(p*p)*subterm1*subterm2);
		double x1_res =a - 0.5 * ((p*p*(q*q+p*p+r1*r1-r2*r2) +q* wurz )/(q*q*p+p*p*p));
		double x2_res =a - 0.5 * ((p*p*(q*q+p*p+r1*r1-r2*r2) -q* wurz )/(q*q*p+p*p*p));
		double y1_res =b - 0.5 * ((q*(q*q+p*p+r1*r1-r2*r2) - wurz )/(q*q+p*p));
		double y2_res =b - 0.5 * ((q*(q*q+p*p+r1*r1-r2*r2) + wurz )/(q*q+p*p));
		/*System.out.println(x1_res);
		System.out.println(y1_res);
		System.out.println("____________");
		System.out.println(x2_res);
		System.out.println(y2_res);*/
		result.add(new Tupel<Double,Double>(x1_res,y1_res));
		result.add(new Tupel<Double,Double>(x2_res,y2_res));
		return result;
	}
}
