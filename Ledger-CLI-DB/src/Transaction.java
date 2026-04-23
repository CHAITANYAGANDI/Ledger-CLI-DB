public class Transaction {

    private int transactionId;
    private String type;
    private double amount;
    private String category;
    private String description;

    public Transaction(int transactionId, String type, double amount, String category, String description){

        setTransactionId(transactionId);
        setType(type);
        setAmount(amount);
        setCategory(category);
        setDescription(description);
    }


    public void setTransactionId(int transactionId){

        if(transactionId>0){

            this.transactionId = transactionId;

        }
        
    }

    public int getTransactionId(){

        return transactionId;
    }
    

    public void setType(String type){

        if(type!= null &&(type.trim().equalsIgnoreCase("credit")||type.trim().equalsIgnoreCase("DEBIT"))){

            this.type = type;
        }
    }

    public String getType(){

        return type;
    }

    public void setAmount(double amount){

        if(amount > 0.0){

            this.amount = amount;
        }
    }

    public double getAmount(){

        return amount;
    }

    public void setCategory(String category){

        if(category!=null && !(category.trim().isEmpty())){
            
            this.category = category.trim();
        }
    }

    public String getCategory(){

        return category;
    }

    public void setDescription(String description){

        if(description!=null && !(description.trim().isEmpty())){
            
            this.description = description.trim();
        }
    }

    public String getDescription(){

        return description;
    }

    public void displayTransactionInfo(){

        System.out.println("Transaction ID: "+getTransactionId());
        System.out.println("Type: "+getType());
        System.out.println("Amount: "+getAmount());
        System.out.println("Category: "+getCategory());
        System.out.println("Description: "+getDescription());
    }

}
