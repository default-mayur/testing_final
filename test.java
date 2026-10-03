class cal{
    public int add(int a,int b)
    {
        return a+b; 
    }
}

class test {
    public static void main(String args[]) {
        System.out.println("Hello World!!");

        cal obj = new cal();
        int c = obj.add(10,20);
        System.out.println(c);
    }
}