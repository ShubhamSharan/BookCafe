package helperclasses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static helperclasses.inputFunctions.getInput;

public class Address {
    String address_name;
    String city;
    String state;
    String zip;

    public Address(String an, String ct, String st, String zp){
        address_name = an;
        city = ct;
        state = st;
        zip = zp;
    }
    public static Address makeAddress() throws IOException {
        System.out.println("Address");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String addressName = getInput(br, "Address name : ");
        String city = getInput(br, "City : ");
        String state = getInput(br, "State : ");
        String zip = getInput(br, "Zip : ");
        return new Address(addressName,city,state,zip);
    }
}
