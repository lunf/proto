package com.lunf.proto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.lunf.proto.dto.CustomerArray;
import com.lunf.proto.dto.CustomerJson;
import com.lunf.proto.dto.CustomerOuterClass;
import com.lunf.proto.dto.CustomerOuterClass.Customer;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

/**
 * Created by alexandra on 3/30/17.
 */
public class DataHelper {

    private static SecureRandom random = new SecureRandom();
    private static final int ITEM_SIZE = 10000;


    public static void generateProtocolBuffData(String fileName) {

        try {
            CustomerOuterClass.Customer customer = CustomerOuterClass.Customer.newBuilder()
                    .setId(4)
                    .setName("Jane")
                    .setEmail("jane@email.com")
                    .build();

            // Write the new customer back to disk.
            FileOutputStream output = new FileOutputStream(fileName);
            customer.writeTo(output);
            output.close();
        } catch (IOException ex) {
            System.out.println("Could not create sample data for protocol buff");
        }
    }

    public static void generateMassiveProtocolBuffData(String fileName) {


        try {

            FileOutputStream output = new FileOutputStream(fileName);

            CustomerArray.OuterCustomer.Builder arrayBuilder =  CustomerArray.OuterCustomer.newBuilder();

            for (int i = 0; i < ITEM_SIZE; i++) {
                Lorem lorem = LoremIpsum.getInstance();

                int id = random.nextInt();

                String name = lorem.getName();
                String email = lorem.getEmail();

                CustomerArray.InnerCustomer customerArray = CustomerArray.InnerCustomer.newBuilder()
                        .setId(id)
                        .setName(name)
                        .setEmail(email)
                        .build();

                arrayBuilder.addCustomers(customerArray);
            }
            arrayBuilder.build().writeTo(output);
            output.flush();
            output.close();

        } catch (IOException ex) {
            System.out.println("Could not create massive sample data for protocol buff");
        }
    }

    public static void generateMassiveJsonBuffData(String fileName) {

        List<CustomerJson> customerJsonList = new ArrayList<CustomerJson>();
        for (int i = 0; i < ITEM_SIZE; i++) {
            CustomerJson customerJson = new CustomerJson();

            Lorem lorem = LoremIpsum.getInstance();

            int id = random.nextInt();

            String name = lorem.getName();
            String email = lorem.getEmail();

            customerJson.setId(id);
            customerJson.setName(name);
            customerJson.setEmail(email);

            customerJsonList.add(customerJson);
        }

        try {
            FileOutputStream output = new FileOutputStream(fileName);

            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
            writer.writeValue(output, customerJsonList);
        } catch (IOException ex) {
            System.out.println("Could not create massive sample data for json");
        }
    }

    public static void generateStringData(String fileName) {
        String data = "14|Mary|marry@email.com";

        try {
            FileOutputStream output = new FileOutputStream(fileName);
            output.write(data.getBytes());
            output.flush();
            output.close();
        } catch (IOException ex) {
            System.out.println("Could not create sample data for string");
        }
    }

    public static CustomerOuterClass.Customer parse(FileInputStream fileInputStream) {

        Customer customer = null;
        try {
            customer =
                    Customer.parseFrom(fileInputStream);
        } catch (IOException ex) {
            System.out.println("Could not parse Customer");
        }

        return customer;
    }

    public static void printCustomer(Customer person) {

        System.out.println("Person ID: " + person.getId());
        System.out.println("Name: " + person.getName());
        System.out.println("E-mail address: " + person.getEmail());

    }

    public static void printCustomer(CustomerArray.InnerCustomer person) {

        System.out.println("Person ID: " + person.getId());
        System.out.println("Name: " + person.getName());
        System.out.println("E-mail address: " + person.getEmail());

    }

    public static void printCustomer(CustomerJson person) {

        System.out.println("Person ID: " + person.getId());
        System.out.println("Name: " + person.getName());
        System.out.println("E-mail address: " + person.getEmail());

    }

    public static CustomerArray.OuterCustomer parseArray(FileInputStream fileInputStream) {
        CustomerArray.OuterCustomer customerArray = null;

        try {
            customerArray = CustomerArray.OuterCustomer.parseFrom(fileInputStream);
        } catch (IOException ex) {
            System.out.println("Could not parse Customer Array");
        }

        return customerArray;
    }

    public static List<CustomerJson> parseJsonArray(FileInputStream fileInputStream) {
        ObjectMapper mapper = new ObjectMapper();

        List<CustomerJson> customerJsonList = null;
        try {

            customerJsonList = Arrays.asList(mapper.readValue(fileInputStream, CustomerJson[].class));

        } catch (IOException ex) {
            System.out.println("Could not parse Customer JSON Array");
        }

        return  customerJsonList;
    }
}
