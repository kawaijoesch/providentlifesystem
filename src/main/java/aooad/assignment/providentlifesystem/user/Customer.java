package aooad.assignment.providentlifesystem.user;

import aooad.assignment.providentlifesystem.collection.PolicyCollection;
import aooad.assignment.providentlifesystem.policy.Policy;
import aooad.assignment.providentlifesystem.policy.decorator.Rider;

import java.util.*;

public class Customer extends User {

    private static int last_id = 0;
    public static Map<Integer, Customer> customerMap = new HashMap<>();

    public PolicyCollection policyCollection = new PolicyCollection();
    private String address;

    public Customer(String name, String address) {
        super(name, last_id++);
        this.address = address;
    }

    public static void register(Customer customer) {
        customerMap.put(customer.getId(), customer);
        System.out.println("Registering Customer " + customer.getName() + "...");
    }

    public void cancelByCustomer() { }

    public void menu() {
        int option = -1;

        while(option != 0) {
            ConsoleHelper.printSegment("Customer Menu");
            option = ConsoleHelper.question("Options", List.of("View Customer Policies"));

            switch(option) {
                case 1:
                    viewPolicies();
                    break;
            }
        }

    }

    private void viewPolicies() {
        int option = -1;

        while(option != 0) {
            List<String> options = new ArrayList<>();

            for(int i = 0; i < policyCollection.size(); i++) {
                Policy policy = policyCollection.get(i);
                List<Rider> riderList = policy.getPolicies();
                StringBuilder sb = new StringBuilder();

                sb.append(policy.getPremium().isOutstanding() ? "<Outstanding>\n" : "\n");
                sb.append(riderList.get(0).toString());

                options.add(sb.toString());
            }

            option = ConsoleHelper.question("View Policies (Enter PolicyID to pay for premium)", options);
            payPolicy(policyCollection.get(option - 1));
        }
    }

    private void payPolicy(Policy policy) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter credit number (16 digits): ");
        policy.getPremium().creditCardPayment(scanner.nextLine());
    }

    public static int selectCustomer() {
        int option = -1;
        List<String> list = new ArrayList<>();

        for(int i = 0; i < customerMap.size(); i++) {
            list.add(customerMap.get(i).getName());
        }

        option = ConsoleHelper.question("Select Customer", list);
        if(option != 0) return option - 1;
        return -1;
    }

}
