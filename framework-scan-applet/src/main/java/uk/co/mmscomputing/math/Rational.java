package uk.co.mmscomputing.math;

public class Rational extends Number{

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private int    numerator;
  private int    denominator;

  public Rational(int numerator){
    this.numerator=numerator;
    this.denominator=1;
  }

  public Rational(double r){set(r);}

  public Rational(String value){
    String[] strs=value.split("/");
    if(strs.length==2){
      numerator=Integer.parseInt(strs[0]);
      denominator=Integer.parseInt(strs[1]);
      gcd();
    }else{
      set(new Double(value).doubleValue());
    }
  }

  public Rational(int n,int d)throws IllegalArgumentException{
    numerator=n;denominator=d;gcd();
  }

  public void set(double r){                      // [1] p.57
    double ab,ax;
    long   lastp,lastq,inter,ac;
   
    long p=0,q=1;
    ax=Math.abs(r);
    if(ax>1E-8){
      ab=ax;ac=(long)ab;p=ac;
      lastp=1;lastq=0;
      while((1E7*Math.abs(ax*q-p))>(ax*q)){
        ab=1.0/(ab-(double)ac);
        ac=(long)ab;
        inter=ac*p+lastp;lastp=p;p=inter;
        inter=ac*q+lastq;lastq=q;q=inter;
        if((q>1E7)||(p*q>1E9)){
          p=lastp/lastq;q=1;ax=p;
        }
      }
    }
    if(r<0){p=-p;}

    numerator   =(int)p;
    denominator =(int)q;
  }

  public int getNumerator(){return numerator;}
  public int getDenominator(){return denominator;}

  public void gcd(){
    long gcd=getGCD(numerator,denominator);
    this.numerator=(int)(((long)numerator)/gcd);
    this.denominator=(int)(((long)denominator)/gcd);
  }

  static public long getGCD(long a, long b){        // greatest common denominator
    if(b==0){throw new IllegalArgumentException();}

    long r;

    a=Math.abs(a);
    if(a==0){return 1;}
    b=Math.abs(b);
    if(b==0){return 1;}
    if(a<b){r=a;a=b;b=r;}
    do{r=a%b;a=b;b=r;}while(r>0);
    return a;
  }

  public byte   byteValue()  {return (byte)(numerator/denominator);}
  public short  shortValue() {return (short)(numerator/denominator);}
  public int    intValue()   {return numerator/denominator;}
  public long   longValue()  {return numerator/denominator;}
  public float  floatValue() {return (float)((float)numerator)/((float)denominator);}
  public double doubleValue(){return (double)((double)numerator)/((double)denominator);}

  public String toString(){return ""+numerator+"/"+denominator;}

  static public Rational valueOf(int i){return new Rational(i);}
  static public Rational valueOf(double d){return new Rational(d);}
  static public Rational valueOf(String s){return new Rational(s);}
  static public Rational valueOf(Rational r){return new Rational(r.getNumerator(),r.getDenominator());}

	public Rational negate(){return new Rational(-numerator,denominator);}
	public Rational inverse(){return new Rational(denominator,numerator);}

	public Rational add(Rational r){
    int ad =   numerator*r.getDenominator();
    int bc = denominator*r.getNumerator();
    int bd = denominator*r.getDenominator();

    return new Rational((ad+bc),bd);
  }

	public Rational sub(Rational r){
    int ad =   numerator*r.getDenominator();
    int bc = denominator*r.getNumerator();
    int bd = denominator*r.getDenominator();

    return new Rational((ad-bc),bd);
  }

  public Rational mul(Rational r){
    int ac = numerator  *r.getNumerator();
    int bd = denominator*r.getDenominator();
    return new Rational(ac,bd);
  }

  public Rational div(Rational r){
    int ad = numerator  *r.getDenominator();
    int bc = denominator*r.getNumerator();
    return new Rational(ad,bc);
  }

  public static void main(String[] argv){
    Rational a=new Rational(5,7);

    System.out.println("a="+a);

    Rational b=new Rational("13/7");

    System.out.println("b="+b);

    Rational c=a.div(b);

    System.out.println(c);
  }
}

// [1] Mathematical software tools in c++; Alain Reverchon and Marc Ducamp; Wiley; 1993; ISBN 0-471-93792-4







