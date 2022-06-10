public class lab1 {

    public static double fst (double n) {
        return Math.pow((1.0/3 * Math.pow(Math.pow(n, 1.0/3), 1.0/3)), 2);
    }
    public static double snd (double n) {
        return Math.asin(Math.sin(Math.pow(2 * Math.pow(2.0/n, n), 3)));
    }
    public static double trd (double n) {
        return Math.pow(3.0/4/(Math.pow(Math.pow(n * (n + 1), n), 1.0/3) + 1.0/3), 3)/2;
    }
    public static double RandomRange (double a, double b) {
        return Math.random() * (b - a) + a;
    }
    public static void PrintArray (double[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.printf("%.5f ", arr[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {

        //1. Создать одномерный массив d типа long. Заполнить его нечётными числами от 1 до 23 включительно в порядке возрастания.
        final int len = 12;
        long[] d = new long[len];
        for(int i = 0; i < len; i++) {
            d[i] = 2 * i + 1;
        }

        //2. Создать одномерный массив x типа double. Заполнить его 12-ю случайными числами в диапазоне от -6.0 до 9.0.
        double[] x = new double[len];
        for(int i = 0; i < len; i++) {
            x[i] = RandomRange(-6.0, 9.0);
        }

        //3. Создать двумерный массив d размером 12x12. Вычислить его элементы по следующей формуле (где x = x[j]):
        double[][] dd = new double[len][len];
        for(int i = 0; i < len; i++) {
            for(int j = 0; j < len; j++) {
                if(d[i] == 7) {
                    dd[i][j] = fst(x[j]);
                } else if (d[i] == 1 || d[i] == 9 || d[i] == 11 || d[i] == 13 || d[i] == 15 || d[i] == 17) {
                    dd[i][j] = snd(x[j]);
                } else {
                    dd[i][j] = trd(x[j]);
                }
            }
        }

        //4. Напечатать полученный в результате массив в формате с пятью знаками после запятой.
        PrintArray(dd);
    }
}