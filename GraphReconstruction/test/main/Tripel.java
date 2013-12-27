package main;

public class Tripel<A,B,C> {
	private A a;
	private B b;
	private C c;
	Tripel(A s, B d, C e){
		this.a = s;
		this.b = d;
		this.c = e;
	}
	Tripel(){
		this.a = null;
		this.b = null;
		this.c = null;
	}
	Tripel(Tupel<A,B> tup , C c){
		this.a = tup.getFirst();
		this.b = tup.getSecond();
		this.c = c;
	}
	void setFirst(A a){this.a=a;}
	void setSecond(B b){this.b=b;}
	void setThird(C c){this.c=c;}
	A getFirst(){return this.a;}
	B getSecond(){return this.b;}
	C getThird(){return this.c;}
	String print(){
		return "(" + a.toString() + ";" + b.toString() + ";" + c.toString() + ")";
	}
	
}
