//
// Team 1: Spilled Coffee
// Create order item test objects from order CSV fields (no time)
//

public class OrderItem {
    private String date;
    private String cust_email;
    private String cust_location;
    private String product_id;
    private int quantity;   

    // OrderItem constructor
    public OrderItem (String date, String cust_email, String cust_location, String product_id, int quantity) {
        this.date = date;
        this.cust_email = cust_email;
        this.cust_location = cust_location;
        this.product_id = product_id;

        if (quantity <= 0){
            this.quantity = 0;
        }
        else {
            this.quantity = quantity;
        }

    }

    // Getters and setters
    public String getOrderDate() { return date; }
    public void setOrderDate(String newOrderDate) { date = newOrderDate; }

    public String getCustomerEmail() { return cust_email; }
    public void setCustomerEmail(String newCustomerEmail) { cust_email = newCustomerEmail; }

    public String getCustomerLocation() { return cust_location; }
    public void setCustomerLocation(String newCustomerLocation) { cust_location = newCustomerLocation; }

    public String getProductId() { return product_id; }
    public void setProductId(String newProductId) { product_id = newProductId; }

    public int getQuantity() { return quantity; }
    public void setQuantity (int newQuantity) { quantity = newQuantity; }

    @Override
    public String toString() {
        return  date +
            "," + cust_email +
            "," + cust_location +
            "," + product_id +
            "," + quantity;
    }
}
