import java.util.Scanner;

/**
 * Created by yulpuma on 9/21/16.
 */
public class CaesarCipher {
    String userInput;
    String encryptedInput = "";
    String decryptedInput = "";
    int shiftNum;

    public CaesarCipher(){
        this.shiftNum = 0;
    }


    //Sets the input that will be used for the CaesarCipher
    public void setInput(String input){
        this.userInput = input;
    }

    //Gets the input that will be used for the CaesarCipher
    public String getInput(){
        return this.userInput;
    }

    //Sets the number of shifts for the Caesar Cipher
    public void setShiftNum(int shiftNum){ this.shiftNum = shiftNum; }

    //Gets the number of shifts for the Caesar Cipher
    public int getShiftNum(){ return this.shiftNum; }

    //Encrypts the input
    public String encryptInput(String s, int n){
        // The string that will get encrypted
        String substr = "";
        //The encrypted string above but in reverse
        String reverseStr = "";
        char ch;
        for(int i = 0; i < s.length(); i++){
            ch = Character.toLowerCase(s.charAt(i));
            if((int) ch > 96){
                if ((int) ch + n > 122)
                    substr = substr + (char) ((int) (ch + n - 26));
                else
                    substr = substr + (char) ((int) (ch + n));
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
        /*for(int i = substr.length()-1; i >= 0; i--){
            reverseStr = reverseStr + substr.charAt(i);
        }*/
        //Prints out the encrypted message
        System.out.println(substr);
        this.encryptedInput = substr;
        setShiftNum(n);
        //Prints out the reversed encrypted message
        //System.out.println(reverseStr);
        return substr;
    }

    public String decryptInput(){
        String substr = "";
        char ch;
        for(int i = 0; i < this.encryptedInput.length(); i++){
            ch = this.encryptedInput.charAt(i);
            if((int) ch > 96){
                if ((int) ch - getShiftNum() < 97)
                    substr = substr + (char) ((int) (ch - getShiftNum() + 26));
                else
                    substr = substr + (char) ((int) (ch - getShiftNum()));
            }
            else
                substr = substr + ch;
        }
        System.out.println(substr);
        this.decryptedInput = substr;
        return substr;
    }
    public static void main(String[] args){
        CaesarCipher cc = new CaesarCipher();
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter a sentence or message:");
        String s = scan.nextLine();
        System.out.println("How many shifts would you like(number): ");
        int n = scan.nextInt();
        cc.encryptInput(s, n);
        cc.decryptInput();
    }
}
