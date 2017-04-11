package com.lunf.proto;


import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.lunf.proto.dto.CustomerArray;
import com.lunf.proto.dto.CustomerJson;
import com.lunf.proto.dto.CustomerOuterClass.Customer;

public class Main {

    private static final String protoFileName = "sample.data";
    private static final String stringFileName = "string.data";
    private static final String largeProtoFileName = "large_sample.data";
    private static final String largeJsonFileName = "large_sample.json";

    public static void main(String[] args) throws Exception {
	    // write your code here
        System.out.println(">>> Proto Sample <<<");

        processString();
        System.out.println(">>> ======================= <<<");

        processProtoBuff();

        System.out.println(">>> ======================= <<<");

        processLargeProtoBuff();

        System.out.println(">>> ======================= <<<");
        processLargeJson();

        System.out.println(">>> ======================= <<<");
    }

    private static void processString() throws Exception {
        System.out.println(">>> String Process Start <<<");
        // Check the existing sample file otherwise generate it.
        boolean isFile = new File(stringFileName).isFile();
        if (!isFile) {
            DataHelper.generateStringData(stringFileName);
        }

        long startTime = System.currentTimeMillis();
        byte[] encoded = Files.readAllBytes(Paths.get(stringFileName));

        System.out.println(new String(encoded));

        System.out.println("Total time >>> " + (System.currentTimeMillis() - startTime));
        System.out.println(">>> String Process End <<<");
    }

    private static void processProtoBuff() throws Exception {
        System.out.println(">>> Proto Process Start <<<");
        // Check the existing sample file otherwise generate it.
        boolean isFile = new File(protoFileName).isFile();
        if (!isFile) {
            DataHelper.generateProtocolBuffData(protoFileName);
        }


        FileInputStream fileInputStream = new FileInputStream(protoFileName);
        long startTime = System.currentTimeMillis();
        // Read Customer from file
        Customer customer = DataHelper.parse(fileInputStream);
        DataHelper.printCustomer(customer);
        System.out.println("Total time >>> " + (System.currentTimeMillis() - startTime));
        System.out.println(">>> Proto Process End <<<");
    }

    private static void processLargeProtoBuff() throws Exception {
        System.out.println(">>> Large Proto Process Start <<<");
        // Check the existing sample file otherwise generate it.
        boolean isFile = new File(largeProtoFileName).isFile();
        if (!isFile) {
            DataHelper.generateMassiveProtocolBuffData(largeProtoFileName);
        }


        FileInputStream fileInputStream = new FileInputStream(largeProtoFileName);
        long startTime = System.currentTimeMillis();
        // Read Customer from file
        CustomerArray.OuterCustomer customer = DataHelper.parseArray(fileInputStream);
        System.out.println("Size >>>" + customer.getCustomersList().size());
        DataHelper.printCustomer(customer.getCustomersList().get(0));
        System.out.println("Total time >>> " + (System.currentTimeMillis() - startTime));
        System.out.println(">>> Proto Process End <<<");
    }

    private static void processLargeJson() throws Exception {
        System.out.println(">>> Large JSON Process Start <<<");
        // Check the existing sample file otherwise generate it.
        boolean isFile = new File(largeJsonFileName).isFile();
        if (!isFile) {
            DataHelper.generateMassiveJsonBuffData(largeJsonFileName);
        }


        FileInputStream fileInputStream = new FileInputStream(largeJsonFileName);
        long startTime = System.currentTimeMillis();
        // Read Customer from file
        List<CustomerJson> customerJsonList = DataHelper.parseJsonArray(fileInputStream);
        System.out.println("Size >>>" + customerJsonList.size());
        DataHelper.printCustomer(customerJsonList.get(0));
        System.out.println("Total time >>> " + (System.currentTimeMillis() - startTime));
        System.out.println(">>> JSON Process End <<<");
    }
}
