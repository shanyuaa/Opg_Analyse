
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class OpgAnalyse {
    public static char[][] SymTable= new char[110][110];

    public static void InitialTable(char[][] sym){
        sym[((int)'#')][((int)'+')] = '<';
        sym[(int)'#'][(int)'*'] = '<';
        sym[(int)'#'][(int)'i'] = '<';
        sym['#']['('] = '<';
        sym['#'][')'] = '<';

        sym['+']['#'] = '>';
        sym['*']['#'] = '>';
        sym['(']['#'] = '>';
        sym[')']['#'] = '>';
        sym['i']['#'] = '>';

        sym['+']['+'] = '>';
        sym['+']['*'] = '<';
        sym['+']['i'] = '<';
        sym['+']['('] = '<';
        sym['+'][')'] = '>';
        sym['*']['+'] = '>';
        sym['*']['*'] = '>';
        sym['*']['i'] = '<';
        sym['*']['('] = '<';
        sym['*'][')'] = '>';
        sym['i']['+'] = '>';
        sym['i']['*'] = '>';
        sym['i'][')'] = '>';
        sym['(']['+'] = '<';
        sym['(']['*'] = '<';
        sym['(']['i'] = '<';
        sym['('][')'] = '<';
        sym['('][')'] = '=';
        sym[')']['+'] = '>';
        sym[')']['*'] = '>';
        sym[')'][')'] = '>';
    }

//    public char ReadLine(String line, int at){
//        at ++;
//        return line.charAt(at);
//    }

//    public int isVT(char ch){ //是否为终结符
//        if(ch == '+' || ch == '*' || ch == 'i' || ch == '(' || ch == ')'){
//            return 1;
//        }
//        else return 0;
//    }

    public static void analyse(Stack s, StringBuffer line){
        s.push('#');
        for(int i = 0; i < line.length();){
            char ch = line.charAt(i); //获得字符串第一个字符
            char sch = (char)s.peek(); //获得栈顶字符

            if(sch>'i' || ch>'i'){
                System.out.println("E");
                return;
            }




            if(sch != 'N'){
                if(sch == '#' && ch == '#'){
                    return;
                }
                if(SymTable[sch][ch] == '<' || SymTable[sch][ch] == '='){ //进栈
                    s.push(ch);
                    System.out.println("I"+ch);
                    i++;
//                    if((ch == 'i' || ch == ')') && SymTable[sch][ch] == '>'){
//                        reduction(s);
//                    }
                }
                else if(SymTable[sch][ch] == '>'){ //规约
                    reduction(s);
                }
//                else if(sch == '#'){
//                    return;
//                }
                else {
                    System.out.println("E");
                    return;
                }
            }
            else{
                s.pop();
                sch = (char)s.peek();
                if(sch == '#' && ch == '#'){
                    return;
                }
                if(SymTable[sch][ch] == '<' || SymTable[sch][ch] == '='){ //进栈
                    s.push('N');
                    s.push(ch);
                    System.out.println("I"+ch);
                    i++;
//                    if((ch == 'i' || ch == ')') && SymTable[sch][ch] == '>'){
//                        reduction(s);
//                    }
                }
                else if(SymTable[sch][ch] == '>'){ //规约
                    s.push('N');
                    reduction(s);
                }
//                else if(sch == '#'){
//                    return;
//                }
                else System.out.println("E");
            }

//            if(ch == '#'){
//                reduction(s);
//                return;
//            }
        }
    }

    public static void reduction(Stack stack){
        char ch = (char)stack.peek();
        if(ch == 'i'){
            stack.pop();
            stack.push('N');
            System.out.println("R");
        }
        else if(ch == ')'){
            stack.pop();
            char ch1 = (char)stack.peek();
            if(ch1 == 'N'){
                stack.pop();
                char ch2 = (char) stack.peek();
                if(ch2 == '('){
                    stack.pop();
                    stack.push('N');
                    System.out.println("R");
                }
                else {
                    System.out.println("RE");
                    return;
                }
            }
            else{
                System.out.println("RE");
                return;
            }
        }
        else if(ch == 'N'){
            stack.pop();
            char ch1 = (char)stack.peek();
            if(ch1 == '+' || ch1 == '*'){
                stack.pop();
                char ch2 = (char) stack.peek();
                if(ch2 == 'N'){
                    stack.pop();
                    stack.push('N');
                    System.out.println("R");
                }
                else {
                    System.out.println("RE");
                    return;
                }
            }
            else {
                System.out.println("RE");
                return;
            }
        }
        else {
            System.out.println("RE");
            return;
        }
    }



    public static void main(String[] args) throws IOException {
        String name = args[0];
        //FileReader fr = new FileReader("/Users/wzy/Desktop/sf/a.txt");
        FileReader fr = new FileReader(name);
        BufferedReader br = new BufferedReader(fr);
        StringBuffer line = new StringBuffer(br.readLine());
        line.append('#');
        Stack stack = new Stack();

        InitialTable(SymTable);
        analyse(stack, line);

    }
}
