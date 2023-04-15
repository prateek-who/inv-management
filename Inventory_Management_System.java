import java.util.*;

public class Java_Activity_2 {
    public static void main(String[] args){
        All_display fin_obj = new All_display();
        fin_obj.dashboard_interaction();
    }
}

class Inventory {
    Scanner sc = new Scanner(System.in);
    HashMap<Integer, String> hm_name = new HashMap<>();
    HashMap<Integer, Float> hm_price = new HashMap<>();
    HashMap<Integer, Float> hm_weight = new HashMap<>();
    HashMap<Integer, Integer> hm_quantity = new HashMap<>();
    // Four hasmaps created for storing data. Keys for all hashmaps are the same.
    float Inventory_size = 0, Inventory_price = 0, Inventory_max = 2000; //Inventory max size is 2000. It can chenaged to a desired limit
    int pro_ID, pro_Quantity, choice, id_edit;
    float pro_Price, pro_Weight;
    String pro_Name;
    // pro - Product

    public void input_product() {
        try {
            System.out.println("Enter Product ID:");
            pro_ID = sc.nextInt();
            if (hm_name.containsKey(pro_ID)) {
                System.out.println("Product with similar ID already exists!");
            } //Checks if the ID already exists.
            else {
                System.out.println("Enter Product Name:");
                pro_Name = sc.next();
                System.out.println("Enter Product Price(Individual Price):");
                pro_Price = sc.nextFloat();
                System.out.println("Enter Product Weight(Single Unit)(in sq units.):");
                pro_Weight = sc.nextFloat();
                System.out.println("Enter Quantity:");
                pro_Quantity = sc.nextInt();

                if ((pro_Weight * pro_Quantity) + Inventory_size <= Inventory_max) {
                    hm_name.put(pro_ID, pro_Name);
                    hm_price.put(pro_ID, (pro_Price * pro_Quantity));
                    hm_weight.put(pro_ID, (pro_Weight * pro_Quantity));
                    hm_quantity.put(pro_ID, pro_Quantity);
                    Inventory_size = Inventory_size + (pro_Weight * pro_Quantity);
                    Inventory_price = Inventory_price + (pro_Price * pro_Quantity);
                    System.out.println("Product Added!");
                } else {
                    System.out.println("Inventory too full....please wait!");
                }
            }
        }catch (InputMismatchException e){
            System.out.println(e.getMessage()+" returned!\nTerminating!");
        }//try-catch block (Input Mismatch Exception)
    }

    public void edit_product() {
        try {
            System.out.println("Enter product ID to edit:");
            id_edit = sc.nextInt();
            if (hm_name.containsKey(id_edit)) {
                System.out.println("What parameter would you like to edit?\n1.Price\n2.Weight\n3.Quantity");
                choice = sc.nextInt();
                do {
                    switch (choice) {
                        case 1 -> {
                            System.out.println(hm_price.get(id_edit));
                            System.out.println("Enter new Price(Individual Price):");
                            pro_Price = sc.nextFloat();
                            Inventory_price = Inventory_price - hm_price.get(id_edit);
                            hm_price.replace(id_edit, (pro_Price * pro_Quantity));
                            Inventory_price = Inventory_price + (pro_Price * pro_Quantity);
                            System.out.println(hm_price.get(id_edit));
                        }

                        case 2 -> {
                            System.out.println("Enter new Weight(Single Unit):");
                            pro_Weight = sc.nextFloat();
                            Inventory_size = Inventory_size - hm_weight.get(id_edit);
                            if ((pro_Weight * pro_Quantity) + Inventory_size <= Inventory_max) {
                                hm_weight.replace(id_edit, (pro_Weight * pro_Quantity));
                                Inventory_size = Inventory_size + (pro_Weight * pro_Quantity);
                            } else {
                                Inventory_size = Inventory_size + hm_weight.get(id_edit);
                                System.out.println("Can't fit items in Inventory!!");
                            }
                        }

                        case 3 -> {
                            System.out.println("Enter new Quantity:");
                            pro_Quantity = sc.nextInt();
                            float individual_weight = hm_weight.get(id_edit) / hm_quantity.get(id_edit);
                            float individual_price = hm_price.get(id_edit) / hm_quantity.get(id_edit);
                            Inventory_size = Inventory_size - hm_weight.get(id_edit);
                            if ((individual_weight * pro_Quantity) + Inventory_size <= Inventory_max) {
                                hm_quantity.replace(id_edit, pro_Quantity);
                                Inventory_price = Inventory_price - hm_price.get(id_edit);
                                hm_price.replace(id_edit, (pro_Quantity * individual_price));
                                Inventory_price = Inventory_price + hm_price.get(id_edit);
                                hm_weight.replace(id_edit, (pro_Quantity * individual_weight));
                                Inventory_size = Inventory_size + hm_weight.get(id_edit);
                            } else {
                                Inventory_size = Inventory_size + hm_weight.get(id_edit);
                                System.out.println("Too heavy for Inventory!!");
                            }
                        }

                        default -> System.out.println("Wrong choice!");
                    }

                    System.out.println("What parameter would you like to edit?\n1.Price\n2.Weight\n3.Quantity\n4.Back");
                    choice = sc.nextInt();
                } while (choice == 1 || choice == 2 || choice == 3);
            } else {
                System.out.println("Wrong ID Entered!");
            }
        }catch (InputMismatchException e){
            System.out.println(e.getMessage()+" returned!\nTerminating!");
        }//try-catch block (Input Mismatch Exception)
    }

    public void delete_product() {
        try {
            do {
                System.out.println("Enter the 'Product ID' of the product to be deleted:");
                id_edit = sc.nextInt();

                if (hm_name.containsKey(id_edit)) {
                    hm_name.remove(id_edit);
                    Inventory_price=Inventory_price-hm_price.get(id_edit);
                    hm_price.remove(id_edit);
                    Inventory_size=Inventory_size- hm_weight.get(id_edit);
                    hm_weight.remove(id_edit);
                    hm_quantity.remove(id_edit);
                    System.out.println("Product Deleted!");
                } else {
                    System.out.println("No such Product exists!");
                }

                System.out.println("Would you like to delete more products?\n1.Yes\n2.No");
                choice = sc.nextInt();
            } while (choice == 1);
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage() + " returned\nWrong Input given!");
        }//try-catch block (Input Mismatch Exception)
    }
}

class Dashboard_display extends Inventory {
    public void display_product() {
        System.out.println("\t\t\t\t\t\t\t\tYour current Inventory:");
        System.out.println("ID " + "\t\t Product Price" + "\t\t   Product Weight" + "\t\t  Product Quantity" + "\t\t Product Name" );

        Iterator<Integer> iterator = hm_name.keySet().iterator(); //Iterator runs thourgh all elements in the keySet()-very nice :)
        while (iterator.hasNext()) {
            Integer key = iterator.next();
            System.out.println(key + "\t\t\t " + hm_price.get(key) + "\t\t\t\t  " + hm_weight.get(key) + "\t\t\t\t\t   " + hm_quantity.get(key) +  "\t\t\t\t\t " + hm_name.get(key) );
        }

        System.out.println("----------------------------------------------------------------------");
        System.out.println("Total Inventory Price:\t\t  "+Inventory_price);
        System.out.println("Total Inventory Weight:\t\t  "+Inventory_size);
        System.out.println("----------------------------------------------------------------------");
    }
}

class Meter_display extends Dashboard_display {
    String[][] ar = new String[3][22];
    String sp = " ",fill="+";
    int row, column,i;
    float percentage=0;

    public void fill_meter_display() {
        percentage = (Inventory_size / Inventory_max) * 100;
        System.out.println("Current Level of Storage:");
        for (row = 0; row < 3; row++) {
            for (column = 0; column < 22; column++) {
                for(i=0;i<=20;i++) {
                    if(percentage<=0){
                        ar[row][column] = sp;
                    }
                    else if(percentage<= 5){
                        if (row == 1 && column == 1)
                            ar[row][column] = fill;
                        else
                            ar[row][column] = sp;
                    }
                    else if(percentage>=i*5) {
                        if (row == 1 && column <= i)
                            ar[row][column] = fill;
                        else
                            ar[row][column] = sp;
                    }
                    // Loops and check what is the 'percentage' at. Keeps printing 'fill' until percentage >= i*5
                }
                if (row == 0 || row == 2) {
                    ar[row][column] = "~";
                }
                else if (column == 0 || column == 21) {
                    ar[row][column] = "$";
                }
                System.out.print(ar[row][column]);

            }
            System.out.println(" ");
        }
    }
    // This class is for printing the Storage-Meter
}

class All_display extends Meter_display {
    int cho;
    // This class acts as the dashboard for the program.
    public void dashboard_interaction() {
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\tHello! Welcome to Inventory Manager!");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\tInventory Capacity: "+Inventory_max+" sq units.");
        try {
            System.out.println("1.Input New Product\n2.Edit Existing Product\n3.Delete Existing Product\n4.Display Inventory");
            cho = sc.nextInt();
            do {
                switch (cho) {
                    case 1 -> {
                        input_product();
                        fill_meter_display();
                    }

                    case 2 -> {
                        edit_product();
                        fill_meter_display();
                    }

                    case 3 -> {
                        delete_product();
                        fill_meter_display();
                    }

                    case 4 -> {
                        display_product();
                        fill_meter_display();
                    }
                    default -> System.out.println("Wrong Input!");
                }

                System.out.println("---------------------------------------");
                System.out.println("1.Input New Product\n2.Edit Existing Product\n3.Delete Existing Product\n4.Display Inventory\n5.Exit");
                cho = sc.nextInt();
            } while (cho == 1 || cho == 2 || cho == 3 || cho == 4);
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage()+" returned\nProgram Terminated!");
        }
    }
}
