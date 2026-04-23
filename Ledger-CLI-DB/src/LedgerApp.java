import java.util.Scanner;

public class LedgerApp{

    public static void menu(){

        System.out.println("1. Add Transaction");
        System.out.println("2. View All Transactions");
        System.out.println("3. View Current Balance");
        System.out.println("4. View Total Credit");
        System.out.println("5. View Total Debit");
        System.out.println("6. Search Transaction by Category");
        System.out.println("7. Delete Transaction by Transaction ID");
        System.out.println("8. Exit");
    }

    public static Transaction addNewTransaction(Scanner input){

        
        input.nextLine();

        System.out.println("Enter Transaction ID");
        int transactionId = input.nextInt();
        input.nextLine();

        System.out.println("Enter Type");
        String type = input.nextLine();

        System.out.println("Enter Amount");
        double amount = input.nextDouble();

        input.nextLine();

        System.out.println("Enter Category");
        String category = input.nextLine();

        System.out.println("Enter Description");
        String desc = input.nextLine();

        return new Transaction(transactionId,type,amount,category,desc);
    }

    public static void main(String[] args) {
       
        Scanner input = new Scanner(System.in);
        TransactionDAO transactionDAO = new TransactionDAO();

        while(true){

            menu();

            int userInput = input.nextInt();

            if(userInput == 1){

                Transaction newTrans = addNewTransaction(input);

                transactionDAO.addTransaction(newTrans);

            }

            else if(userInput == 2){

                transactionDAO.viewAllTransactions();
            }

            else if(userInput == 3){

                transactionDAO.viewCurrentBalance();
            }

            else if(userInput == 4){

                transactionDAO.viewTotalCredit();
            }

            else if(userInput == 5){

                transactionDAO.viewTotalDebit();
            }

            else if(userInput == 6){

                input.nextLine();

                System.out.println("Enter category");
                String category = input.nextLine();

                transactionDAO.searchTransactionByCategory(category);
            }

            
            else if(userInput == 7){

                System.out.println("Enter Transaction ID");
                int transId = input.nextInt();

                transactionDAO.deleteTransactionById(transId);
            }

            
            else if(userInput == 8){

                System.out.println("Exiting...");
                break;
            }

            else{

                System.out.println("Invalid choice");
            }
        }

        input.close();
    }
}