import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class TransactionDAO {

    public void addTransaction(Transaction transaction){

        Transaction existingTransaction = searchTransactionByID(transaction.getTransactionId());

        if(existingTransaction!=null){

            System.out.println("Transaction already exists");
            return;
        }

        String query = "INSERT INTO transactions(transaction_id, type, amount, category, description) VALUES (?,?,?,?,?)";

        try(Connection conn = DBConnection.getConnection();
        PreparedStatement statement = conn.prepareStatement(query)){

            statement.setInt(1,transaction.getTransactionId());
            statement.setString(2, transaction.getType());
            statement.setDouble(3,transaction.getAmount());
            statement.setString(4,transaction.getCategory());
            statement.setString(5, transaction.getDescription());

            int rowsInserted = statement.executeUpdate();

            if(rowsInserted > 0){

                System.out.println("Transaction successfully completed");
            }
        }

        catch(SQLException e){

            System.out.println(e.getMessage());
        }
    }

    public Transaction searchTransactionByID(int transaction_id){

        String query = "SELECT * FROM transactions WHERE transaction_id = ?";

        try(Connection conn = DBConnection.getConnection();
        PreparedStatement statement = conn.prepareStatement(query);){

            statement.setInt(1, transaction_id);

            try(ResultSet result = statement.executeQuery();){

                if(result.next()){

                    return new Transaction(
                        result.getInt("transaction_id"),
                        result.getString("type"),
                        result.getDouble("amount"),
                        result.getString("category"),
                        result.getString("description")
                    );
    
                }
            }
        }

        catch(SQLException e){

            System.out.println(e.getMessage());
        }

        return null;

    }

    public void viewAllTransactions(){

        String query = "SELECT * FROM transactions";
        boolean found = false;

        try(Connection conn = DBConnection.getConnection();
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet result = statement.executeQuery();){

            while(result.next()){

                Transaction transaction = new Transaction(
                    result.getInt("transaction_id"),
                    result.getString("type"),
                    result.getDouble("amount"),
                    result.getString("category"),
                    result.getString("description")
                );

                transaction.displayTransactionInfo();
                System.out.println();
                found =  true;

            }

            if(!found){

                System.out.println("No Transaction found");
            }

        }

        catch(SQLException e){

            System.out.println(e.getMessage());
        }
    }

    public void viewCurrentBalance(){

        String query = """
        SELECT COALESCE(SUM(
            CASE 
                WHEN type = 'CREDIT' THEN amount
                WHEN type = 'DEBIT' THEN -amount
                ELSE 0
            END 
        ),0) AS current_balance
        FROM transactions """;


        try(Connection conn = DBConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet result = statement.executeQuery();){
                
                if(result.next()){
                    
                    System.out.println("Current Balance: "+result.getDouble("current_balance"));

                }
                
            }
        
            catch(SQLException e){

                System.out.println(e.getMessage());
            }
    }

    public void viewTotalCredit(){

        String query = "SELECT COALESCE(SUM(amount),0) AS total_credit FROM transactions WHERE type = 'CREDIT'";


        try(Connection conn = DBConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet result = statement.executeQuery();){
                
                if(result.next()){

                    System.out.println("Total Credit Balance: "+result.getDouble("total_credit"));

                }
                
            }
        
            catch(SQLException e){

                System.out.println(e.getMessage());
            }
    }

    public void viewTotalDebit(){

        
        String query = "SELECT COALESCE(SUM(amount),0) AS total_debit FROM transactions WHERE type = 'DEBIT'";


        try(Connection conn = DBConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet result = statement.executeQuery();){

                if(result.next()){

                    System.out.println("Total Debit Balance: "+result.getDouble("total_debit"));

                }
                
            }
        
            catch(SQLException e){

                System.out.println(e.getMessage());
            }

    }

    public void searchTransactionByCategory(String category){

        String query = "SELECT * FROM transactions WHERE category = ?";
        boolean found = false;

        try(Connection conn = DBConnection.getConnection();
        PreparedStatement statement = conn.prepareStatement(query);
        ){

            statement.setString(1, category);

            try(ResultSet result = statement.executeQuery();){

                while(result.next()){

                    Transaction transaction = new Transaction(
                        result.getInt("transaction_id"),
                        result.getString("type"),
                        result.getDouble("amount"),
                        result.getString("category"),
                        result.getString("description")
                    );

                    transaction.displayTransactionInfo();
                    System.out.println();
                    found =  true;

                }

                if(!found){

                    System.out.println("No transactions found");
                }

            }
        }

        catch(SQLException e){

            System.out.println(e.getMessage());
        }

   }

   public void deleteTransactionById(int transaction_id){

        String query = "DELETE FROM transactions WHERE transaction_id = ?";

        try(Connection conn = DBConnection.getConnection();
        PreparedStatement statement = conn.prepareStatement(query)){

            statement.setInt(1, transaction_id);

            int result = statement.executeUpdate();

            if(result>0){

                System.out.println("Transaction deleted successfully");
            }

            else{

                System.out.println("Transaction not found");
            }
        }

        catch(SQLException e){

            System.out.println(e.getMessage());
        }
   }

}
