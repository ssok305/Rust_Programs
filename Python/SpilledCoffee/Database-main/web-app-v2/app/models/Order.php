<?php

    // Handles order page queries

    class Order {

        private $db;

        // Constructor
        public function __construct(){
            $this->db = new Database;
        }

        // Get orders by user id
        public function getOrdersByUserId($user_id){
            $this->db->query('SELECT * FROM new_customer_orders WHERE user_id = :user_id ORDER BY ordered_at DESC;');
            $this->db->bind(':user_id', $user_id);
            $results = $this->db->resultSet();
            // Check row
            if ($this->db->rowCount() > 0){
                return $results;
            } else {
                return false;
            }
        }

        // Get one order by order id
        public function getOrderById($order_id){
            $this->db->query('SELECT * FROM new_customer_orders WHERE order_id = :order_id;');
            $this->db->bind(':order_id', $order_id);
            $row = $this->db->single();
            return $row;
        }

        // Get all order items of a certain id
        public function getOrderItemsbyId($order_id){
            $this->db->query('SELECT * FROM new_order_items WHERE order_id = :order_id;');
            $this->db->bind(':order_id', $order_id);
            $results = $this->db->resultSet();
            return $results;
        }

        // Get all order items
        public function getAllOrderItems(){
            $this->db->query('SELECT * FROM new_order_items;');
            $results = $this->db->resultSet();
            return $results;
        }

        // Get orders in a certain date range
        public function getOrdersByDateRange($start, $end){
            $this->db->query('SELECT * FROM new_customer_orders WHERE ordered_at >= :start and ordered_at <= :end');
            $this->db->bind(':start', $start);
            $this->db->bind(':end', $end);
            $results = $this->db->resultSet();
            return $results;
        }

        // Place an order from the checkout page
        public function place($data){
            $user_id = $data['user_id'];
            $street = $data['street'];
            $city = $data['city'];
            $state = $data['state'];
            $zip = $data['zip'];
            $quantity = $data['order_quantity'];
            $total = $data['order_total'];

            $this->db->query('INSERT INTO new_customer_orders (user_id, order_quantity, order_total,
                                shipping_street, shipping_city, shipping_state, shipping_zipcode)
                                VALUES (:user_id, :order_quantity, :order_total, :shipping_street,
                                :shipping_city, :shipping_state, :shipping_zipcode);');
            $this->db->bind(':user_id', $user_id);
            $this->db->bind(':order_quantity', $quantity);
            $this->db->bind(':order_total', $total);
            $this->db->bind(':shipping_street', $street);
            $this->db->bind(':shipping_city', $city);
            $this->db->bind(':shipping_state', $state);
            $this->db->bind(':shipping_zipcode', $zip);
            
            if ($this->db->execute()){
                $this->db->query('SELECT * FROM new_customer_orders WHERE order_id = LAST_INSERT_ID()');
                $order_id = $this->db->single();
                $id = $order_id->order_id;
                return $id;
            } else {
                return false;
            };

        }

        // Store order items of a certain order
        public function storeOrderItems($data){
            $cart = $data['cart'];
            $order_id = $data['order_id'];
            $user_id = $data['user_id'];
            
            $this->db->query('INSERT INTO new_order_items (user_id, order_id, product_id, quantity)
                                VALUES (:user_id, :order_id, :product_id, :quantity);');

            for ($i = 0; $i < count($cart); $i++) {
                $prod = $cart[$i][0];
                $quant = $cart[$i][1];
                $this->db->bind(':user_id', $user_id);
                $this->db->bind(':order_id', $order_id);
                $this->db->bind(':product_id', $prod);
                $this->db->bind(':quantity', $quant);
                if ($this->db->execute()){
                    $success = true;
                } else {
                    $success = false;
                }
            }
            return $success;
        }

        // Switch a certain order status to cancelled
        public function statusCancelled($order_id){
            $this->db->query('UPDATE new_customer_orders SET order_status = "CANCELLED" WHERE order_id = :order_id;');
            $this->db->bind('order_id', $order_id);
            if ($this->db->execute()){
                return true;
            } else {
                return false;
            }
        }

    }