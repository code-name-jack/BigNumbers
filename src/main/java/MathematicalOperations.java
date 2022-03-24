import java.util.LinkedList;

public class MathematicalOperations {
    static String AddHuge(String a, String b) {
        LinkedList<Integer> Number1;
        LinkedList<Integer> Number2;
        LinkedList<Integer> Sum = new LinkedList<>();

        Number1 = MathematicsJava.StringToList(a, 9);
        Number2 = MathematicsJava.StringToList(b, 9);

        int Shorter = Math.min(Number1.size(), Number2.size()), Carry = 0;
        for (int i = 0; i < Shorter; i++) {
            int TempSum = Number1.get(i) + Number2.get(i) + Carry;
            Carry = TempSum / (int) Math.pow(10, 9);
            Sum.add(TempSum % (int) Math.pow(10, 9));

        }
        if (Number1.size() != Number2.size())
            for (int i = Shorter; i < (Math.max(Number1.size(), Number2.size())); i++) {

                if (Number2.size() == Shorter) {
                    Sum.add(Number1.get(i) + Carry);
                    Carry = 0;
                } else {
                    Sum.add(Number2.get(i) + Carry);
                    Carry = 0;
                }
            }
        return ListToString(Sum, 9);

    }

    static String SubtractHuge(String a, String b) {
        boolean Reversed = false;

        if (!MathematicsJava.isGreater(a, b)) {

            String Temporary = a;
            a = b;
            b = Temporary;
            Reversed = true;
        }

        LinkedList<Integer> Number1;
        LinkedList<Integer> Number2;
        LinkedList<Integer> Difference = new LinkedList<>();
        Number1 = MathematicsJava.StringToList(a, 8);
        Number2 = MathematicsJava.StringToList(b, 8);
        int Shorter = Math.min(Number1.size(), Number2.size()), Carry = 0;
        for (int i = 0; i < Shorter; i++) {
            int TempDifference;
            if (Number1.get(i) >= Number2.get(i)) {
                TempDifference = Number1.get(i) - Number2.get(i) - Carry;
                Carry = 0;
            } else {
                TempDifference = (int) Math.pow(10, 8) + Number1.get(i) - Number2.get(i) - Carry;
                Carry = 1;
            }
            Difference.add(TempDifference);
        }
        if (Number1.size() != Number2.size())
            for (int i = Shorter; i < (Math.max(Number1.size(), Number2.size())); i++) {
                if (Number2.size() == Shorter) {
                    Difference.add(Number1.get(i) - Carry);
                } else {
                    Difference.add(Number2.get(i) - Carry);
                }
                Carry = 0;
            }

        if (!Reversed) return ListToString(Difference, 8);
        else return "-" + ListToString(Difference, 8);
    }

    static String PowerHuge(String a, String b) {
        String Temp = a;
        for (int i = 0; i < Integer.parseInt(b) - 1; i++) {
            Temp = MultiplyHuge2(Temp, a);

        }
        return Temp;
    }

    static String DivideHuge(String a, String b) {
        LinkedList<Integer> Number = new LinkedList<>();
        // LinkedList<Integer> Number2;
        long Divider;
        long Remainder = 0;
        if (a.length() <= 4 && b.length() <= 4) return (Integer.parseInt(a) / Integer.parseInt(b)) + "";
        LinkedList<Long> Quotient = new LinkedList<>();

        while (a.length() > (b.length() + 1)) {
            Number.add(Integer.parseInt(a.substring(0, b.length() + 1)));
            a = a.substring(b.length() + 1);
        }
        Number.add(Integer.parseInt(a));
        Divider = Integer.parseInt(b);
        for (long Divident : Number) {
            Divident += Remainder * (long) Math.pow(10, (Divident + "").length());

            Remainder = Divident % Divider;
            Quotient.add(Divident / Divider);

        }
        //long Divident= Number.get(Number.size()-1);


        StringBuilder Solution = new StringBuilder();
        for (long Part : Quotient) {
            String Check = Part + "";
            Check = ExtraZeroes(b.length() - Check.length()) + Check;
            Solution.append(Check);
        }
        while (Solution.toString().startsWith("0")) Solution = new StringBuilder(Solution.substring(1));
        if (Solution.length() == 0) Solution = new StringBuilder("0");
        return Solution.toString();
    }

    /* This no Multiply one no. from another in a recursive manner. It first brakes the no
     * into Smaller chunks. And then multiplis them and adds required no of zeroes to the result*/
    static String MultiplyHuge2(String a, String b) {
        String r1, r2, r3, r4;
        String c, d, e, f;
        if (a.length() > 3 && b.length() > 3) {
            c = a.substring(0, a.length() / 2);
            d = a.substring(a.length() / 2);
            e = b.substring(0, b.length() / 2);
            f = b.substring(b.length() / 2);
            r1 = MultiplyHuge2(c, e) + ExtraZeroes(d.length()) + ExtraZeroes(f.length());
            r2 = MultiplyHuge2(c, f) + ExtraZeroes(d.length());
            r3 = MultiplyHuge2(d, e) + ExtraZeroes(f.length());
            r4 = MultiplyHuge2(d, f);

            return AddHuge(AddHuge(r1, r2), AddHuge(r3, r4));

        } else if (a.length() > 3) {

            c = a.substring(0, a.length() / 2);
            d = a.substring(a.length() / 2);

            r1 = MultiplyHuge2(c, b) + ExtraZeroes(d.length());
            r2 = MultiplyHuge2(d, b);

            return AddHuge(r1, r2);

        } else if (b.length() > 3) {
            c = b.substring(0, b.length() / 2);
            d = b.substring(b.length() / 2);
            r1 = MultiplyHuge2(a, c) + ExtraZeroes(d.length());
            r2 = MultiplyHuge2(a, d);

            return AddHuge(r1, r2);

        } else {
            return (Integer.parseInt(a) * Integer.parseInt(b)) + "";

        }
    }

    static String ExtraZeroes(int Count) {
        StringBuilder Zeroes = new StringBuilder();
        while (Count > 0) {
            Zeroes.append("0");
            Count--;
        }
        return Zeroes.toString();
    }

    /*it Converts a no in Linked list format to a string equivalent*/
    static String ListToString(LinkedList<Integer> L, int FragmentSize) {
        StringBuilder Solution = new StringBuilder();

        for (int temporary : L) {
            StringBuilder Check = new StringBuilder(temporary + "");
            while (Check.length() < FragmentSize) Check.insert(0, "0");
            Solution.insert(0, Check);
        }
        while (Solution.toString().startsWith("0")) Solution = new StringBuilder(Solution.substring(1));
        if (Solution.length() == 0) Solution = new StringBuilder("0");

        return Solution.toString();
    }
}
