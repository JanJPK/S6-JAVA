public class DotProduct
{
    private double[] a;
    private double[] b;
    private double c;

    //<editor-fold desc="get/set">

    public double[] getA()
    {
        return a;
    }
    public double[] getB()
    {
        return b;
    }
    public double getC()
    {
        return c;
    }

    public void setA(double[] a)
    {
        this.a = a;
    }
    public void setB(double[] b)
    {
        this.b = b;
    }
    public void setC(double c)
    {
        this.c = c;
    }

    //</editor-fold>

    public native double multi01(double[] a, double[] b);

    public native double multi02(double[] a);

    public native void multi03();

    private void multi04()
    {
        double new_c = 0.0;
        for(int i = 0; i < a.length; i++)
        {
            new_c += a[i] * b[i];
        }
        setC(new_c);
    }

    // https://www3.ntu.edu.sg/home/ehchua/programming/java/JavaNativeInterface.html
    
    static
    {
        // Load native library at runtime
        // hello.dll (Windows) or libhello.so (Unixes)
        System.loadLibrary("hello");
    }

    // Test Driver
    public static void main(String[] args)
    {
        DotProduct dp = new DotProduct();
        double[] new_b = {1.0, 1.0, 1.0};
        double[] new_a = {1.0, 2.0, 3.0};
        dp.setA(new_a);
        dp.setB(new_b);

        System.out.println("multi01: " + dp.multi01(dp.getA(), dp.getB()));
        System.out.println("multi02: " + dp.multi02(dp.getA()));
        dp.multi03();
        System.out.println(dp.getC());
    }
}
