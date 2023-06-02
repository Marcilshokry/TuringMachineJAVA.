
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        int statesNum, alphabetNum, machinNum, index;
        String str;
        Scanner sc = new Scanner(System.in);
//**********************************************************
        System.out.println("1.Enter number of states :");
        statesNum = sc.nextInt();
        isValidNum(statesNum);//zero or negative numbers are not allowed


        //states array
        String[] states = new String[statesNum];
        for (int n = 0; n < statesNum; n++) {
            states[n] = "" + n;
            // System.out.println(states[n] );

        }
//**********************************************************

        System.out.println("2.Enter number of string alphabet :");
        alphabetNum = sc.nextInt();
        isValidNum(alphabetNum);//zero or negative numbers are not allowed

//**********************************************************

        System.out.println("3.Enter string alphabet : ");
        String[] alphabet = new String[alphabetNum];
        int l;

        for (l = 0; l < alphabetNum; l++) {

            System.out.println("  -string alphabet num(" + (l + 1) + ") :");
            String input = String.valueOf(alphabet[l] = sc.next());

            if (input.equals("l") || input.equals("r") || input.equals("y") || input.equals("n")) {
                System.out.println("this symbol is taken please enter another one");
                l--;
            }//can not input y n r l are token for actions

            if (l > 0) {
                if (Search(alphabet, input)) l--;
            }// test that repeating same symbol not allowed

        }
        /*System.out.println("your alphabet: ");
        for ( l= 0; l< alphabetNum; l++) {
        System.out.print(alphabet[l]);
        } // test your alphabet array */
//**********************************************************

        System.out.println("4.Enter number of machine alphabet :" + "\n note : that alphabet has been taken as " + "\n  r:step to right           l: step to left " + "\n  y: accept                 n: reject");
        machinNum = sc.nextInt();
        isValidNum(machinNum);//zero or negative numbers are not allowed

//**********************************************************

        System.out.println("5.Enter machine alphabet : ");

        String[] machine = new String[machinNum];
        for (int i = 0; i < machinNum; i++) {
            System.out.println("  -machine alphabet num(" + (i + 1) + ") :");
            String input = String.valueOf(machine[i] = sc.next());

            if (input.equals("l") || input.equals("r") || input.equals("y") || input.equals("n") || SearchExistOne(alphabet, input)) {
                System.out.println("this symbol is taken please enter another one");
                i--;
            }//can not input y n r l or taken alphabet

            if (i > 0) {
                if (Search(machine, input)) i--;
            }//test that repeating same symbol not allowed
        }

        //**********************************************************

        System.out.println("6.Enter the transitions: ");
        // trans must be  Consist of (alphabet,machine,first and third must be in states , last one must be in action)
        System.out.println("( note that : you should enter your transitions separated by 'enter' by this way :" +
                "\ncurrent state " +
                "\ncurrent alphabet" +
                "\nnew state" +
                "\nnew alphabet" +
                "\naction");

        int transNum = statesNum * (machinNum + alphabetNum);
        String[][] transition = new String[transNum][5];
        for (int i = 0; i < transNum; i++) {

            System.out.println("  -transition num(" + (i + 1) + ") :");

            for (int j = 0; j < 5; j++) {
                String input = String.valueOf(transition[i][j] = sc.next());
                //System.out.println("j= "+j);


//test validation input
               /*if(j==0||j==2){//to test if current state and new one are valid
                    if (!SearchExistOne( states,input ))
                    {
                        System.out.println(" this symbol not exist in states ,please try again! ");
                        j--;
                    }

                }
               else if ( j==1 ||j==3) {//to test if current alphabet and new one are valid
                   if (!SearchExistOne( alphabet,input )||!SearchExistOne( machine,input ))
                   {
                       System.out.println(" this symbol not exist in alphabet ,please try again! ");
                       j--;
                   }
               }
               else {//to test if current action is valid
                   if ( !input.equals("l")|| !input.equals("r")|| !input.equals("y")|| !input.equals("n"))
                   {
                       System.out.println(" actions must be  r:step to right  OR  l: step to left  OR  y: accept  OR  n: reject ,please try again! ");
                       j--;
                   }

               }*/

            }
        }
//**********************************************************

        System.out.println("7.Enter string :");
        str = sc.next() + "#";

       /* if (!SearchExistOne( alphabet,str.toCharArray() )||!SearchExistOne( machine,str.toCharArray())) {//string must be Consist of (alphabet,machine)
            System.out.println(" this symbol not exist in alphabet ,please try again! ");}*/


//**********************************************************

        System.out.println("8.Enter initial point of head:");
        index = sc.nextInt();

//**********************************************************
        String currentAlphabet = String.valueOf(str.charAt(index));//head is refers to currentAlphabet
        String currentState = states[0];


        String action = null;
        int steps = 0;

        boolean flag = false;
        while (!"y".equals(action) && !"n".equals(action)) //looping into transitions
        {
            if (steps >= 1000000) {
                System.out.println("The Turing machine has entered an infinite loop.");
                break;
            }//test infinite loop and handling it
            steps++;

            // if ( index==str.length()) currentAlphabet = "#";
            if (index < 0 || index > str.length()) {
                currentAlphabet = "#";
            } else {
                currentAlphabet = String.valueOf(str.charAt(index));
            }//handling if the  head pointer in blank char

            for (int i = 0; i < transNum; i++) {
                String[] trans = transition[i];//i for raw because we set col  into our following coding
                if (currentState.equals(trans[0]) && trans[1].equals(currentAlphabet)) {
                    currentState = trans[2];//new state
                    //if( !currentAlphabet.equals(trans[3])){//replacement
                    if (index < 0 || index >= str.length()) {//current str=#
                        str += trans[3];//new alphabet
                    } else {
                        StringBuilder stringBuilder = new StringBuilder(str);
                        stringBuilder.setCharAt(index, trans[3].charAt(0));
                        str = stringBuilder.toString();

                    }

                    currentAlphabet = trans[3];//new alphabet
                    action = trans[4];//action
                    flag = true;

                   /* System.out.println("iteration num " + steps);
                    System.out.println("Head is on: " + currentAlphabet);
                    System.out.println("index of head is : " + index);
                    System.out.println( " string now: " + str);*/

                    break;

                }
            }


            if (!flag) {
                break;
            }
            flag = false;

            //machinAlphabet
            if ("r".equals(action)) {//go to right
                index++;
            } else if ("l".equals(action)) {// go to left
                index--;
            } else if ("y".equals(action)) {//to accept
                System.out.println("accepted");
                break;
            } else if ("n".equals(action)) {//to reject
                System.out.println("rejected");
                break;
            }
        }
/*index=index-1;
 str.replaceFirst("#","");*/

        System.out.println("Head is on: " + currentAlphabet);
        System.out.println("index of head is : " + index);
        System.out.println("Final string: " + str);


    }

    private static void isValidNum(int Num) {
        Scanner sc = new Scanner(System.in);

        while (Num <= 0) {

            System.out.println("number can not be 0 nor negative!! please try again  ");
            Num = sc.nextInt();
            // continue;
        }

    }

    public static <Y> boolean Search(Y[] arr, Y value) {
        boolean b = false;
        int c = 0;
        int i;
        for (i = 0; i < arr.length; i++) {

            if (value.equals(arr[i])) c++;
        }
        if (c > 1) {
            System.out.println("this is already exist ");

            b = true;
        }

        return b;
    }

    public static <Y> boolean SearchExistOne(Y[] arr, Y value) {
        boolean b = false;
        int c = 0;
        int i;
        for (i = 0; i < arr.length; i++) {

            if (value.equals(arr[i])) c++;
        }
        if (c >= 1) {
            // System.out.println("this is already exist in alphabet, ");

            b = true;
        }

        return b;
    }


}
