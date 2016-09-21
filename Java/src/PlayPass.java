import java.util.Scanner;

/**
 * Created by yulpuma on 9/20/16.
 * Takes a string input and encrypts the data
 * First it accepts the user input and asks for the amount of shifts
 */
public class PlayPass {
    public static String playPass(String s, int n) {
        // The string that will get encrypted
        String substr = "";
        //The encrypted string above but in reverse
        String reverseStr = "";
        char ch;
        for(int i = 0; i < s.length(); i++){
            ch = Character.toLowerCase(s.charAt(i));
            if((int) ch > 96){
                if ((int) ch + n > 122){
                    if(i % 2 == 0)
                        substr = substr + Character.toUpperCase((char) ((int) (ch + n - 26)));
                    else
                        substr = substr + (char) ((int) (ch + n - 26));

                }
                else{
                    if(i % 2 == 0)
                        substr = substr + Character.toUpperCase((char) ((int) (ch + n)));
                    else
                        substr = substr + (char) ((int) (ch + n));
                }
            }
            else{
                //Makes sure that the complement of the numbers in the message is changed
                //i.e if the message contains a "5"
                switch((int) ch){
                    case 48: substr = substr + (char) 57; break;
                    case 49: substr = substr + (char) 56; break;
                    case 50: substr = substr + (char) 55; break;
                    case 51: substr = substr + (char) 54; break;
                    case 52: substr = substr + (char) 53; break;
                    case 53: substr = substr + (char) 52; break;
                    case 54: substr = substr + (char) 51; break;
                    case 55: substr = substr + (char) 50; break;
                    case 56: substr = substr + (char) 49; break;
                    case 57: substr = substr + (char) 48; break;
                    default: substr = substr + ch;
                }
            }
        }
        for(int i = substr.length()-1; i >= 0; i--){
            reverseStr = reverseStr + substr.charAt(i);
        }
        //Prints out the encrypted message
        System.out.println(substr);
        //Prints out the reversed encrypted message
        System.out.println(reverseStr);
        return reverseStr;
    }

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter a sentence or message:");
        String s = scan.nextLine();
        System.out.println("How many shifts would you like(number): ");
        int n = scan.nextInt();
        playPass(s, n);
    }
}
