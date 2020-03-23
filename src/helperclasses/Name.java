package helperclasses;

public class Name {
    String first_name;
    String middle_name;
    String last_name;

    public Name(){
        first_name = "";
        middle_name = "";
        last_name = "";
    }
    public void printName(){
        if(middle_name.length()==0){
            System.out.println(first_name +" "+last_name);
        }else{
            System.out.println(first_name +" "+middle_name+" "+last_name);
        }
    }

}

