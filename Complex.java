package solver;

public class Complex {
    private double re;
    private double im;

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public double getRe() {
        return re;
    }

    public double getIm() {
        return im;
    }

    public void setRe(double re) {
        this.re = re;
    }

    public void setIm(double im) {
        this.im = im;
    }

    public static Complex sum(Complex cn1, Complex cn2) {
        return new Complex(cn1.getRe() + cn2.getRe(), cn1.getIm() + cn2.getIm());
    }

    public static Complex multiply(Complex cn1, Complex cn2) {
        return new Complex(cn1.getRe() * cn2.getRe() - cn1.getIm() * cn2.getIm(),
                cn1.getRe() * cn2.getIm() + cn1.getIm() * cn2.getRe());
    }

    public static Complex subtract(Complex cn1, Complex cn2) {
        return new Complex(cn1.getRe() - cn2.getRe(), cn1.getIm() - cn2.getIm());
    }

    public static Complex divide(Complex cn1, Complex cn2) {
        Complex temp = new Complex(cn2.getRe(), (-1) * cn2.getIm());
        temp = Complex.multiply(cn1, temp);
        double denominator = cn2.getRe() * cn2.getRe() + cn2.getIm() * cn2.getIm();
        return new Complex(temp.getRe() / denominator, temp.getIm() / denominator);
    }

    private String sign() {
        if (im > 0) return "+";
        else return "-";
    }

    public static int nullComplex(Complex cn) {
        if (cn.getIm() == 0 && cn.getRe() == 0)
            return 0;
        else if (cn.getRe() == 1 && cn.getIm() == 0)
            return 1;
        return  -1;
    }

    @Override
    public String toString() {
        String string;
        if (im == 1 || im == -1) {
            if (re == 0) {
                string = sign() + "i";
            } else {
                string = re + sign() + "i";
            }
        } else if (im == 0) {
            string = Double.toString(re);
        } else if (re == 0) {
            string = im + "i";
        } else {
            string = re + sign() + Math.abs(im) + "i";
        }
        return string;
    }

    public static Complex readComplex(String s) {
        boolean firstPositive = true;
        boolean secondPositive = true;
        if (s.charAt(0) == '-') {
            firstPositive = false;
        }
        if (s.substring(1).contains("-")) {
            secondPositive = false;
        }
        String[] split = s.split("[-+]");
        if (split[0].equals("")) {
            split[0] = split[1];
            split[1] = "";
            if (split.length > 2)
                split[1] = split[2];
        }
        double re = 0;
        double im = 0;
        if (split[0].contains("i") && split[0].length() == 1) {
            im = Double.parseDouble((firstPositive ? "+" : "-") + 1);
        } else if (split[0].contains("i") && split[0].length() > 1){
            im = Double.parseDouble((firstPositive ? "+" : "-") + split[0].substring(0, split[0].length() - 1));
        } else {
            re = Double.parseDouble((firstPositive ? "+" : "-") + split[0]);
        }
        if (split.length > 1 && !split[1].equals("")) {
            if (split[1].contains("i") && split[1].length() == 1) {
                im = Double.parseDouble((secondPositive ? "+" : "-") + 1);
            } else if (split[1].contains("i") && split[1].length() > 1){
                im = Double.parseDouble((secondPositive ? "+" : "-") + split[1].substring(0, split[1].length() - 1));
            } else {
                re = Double.parseDouble((secondPositive ? "+" : "-") + split[1]);
            }
        }
        return new Complex(re, im);
    }
}
