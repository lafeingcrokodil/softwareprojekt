package main;

public class Tupel<A,B> {
	private A a;
	private B b;
	
	Tupel(A s, B d){
		this.a = s;
		this.b = d;
	}
	Tupel(){
		this.a = null;
		this.b = null;
	}
	void setFirst(A a){this.a=a;}
	void setSecond(B b){this.b=b;}
	A getFirst(){return this.a;}
	B getSecond(){return this.b;}
	String print(){
		return "(" + a.toString() + ";" + b.toString() + ")";
	}
}
