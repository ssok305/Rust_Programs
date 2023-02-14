<?php

    class Product {

        private $db;

        // Constructor
        public function __construct(){
            $this->db = new Database;
        }

        // Get all products
        public function getAllProducts(){
            $this->db->query('SELECT * FROM new_inventory');
            $results = $this->db->resultSet();
            return $results;
        }

        // Get one product by id
        public function getProductById($id){
            $this->db->query('SELECT * FROM new_inventory WHERE product_id = :id');
            $this->db->bind(':id', $id);
            $row = $this->db->single();
            return $row;
        }

        // Reduce inventory item by a certain quantity, used in cart
        public function reduceQuantity($id, $quant){
            $this->db->query('UPDATE new_inventory SET quantity = quantity - :quant WHERE product_id = :id;');
            $this->db->bind(':id', $id);
            $this->db->bind(':quant', $quant);
            if ($this->db->execute()){
                return true;
            } else {
                return false;
            };
        }

        // Add quantity back to inventory item, used in cart
        public function restoreQuantity($id, $quant){
            $this->db->query('UPDATE new_inventory SET quantity = quantity + :quant WHERE product_id = :id;');
            $this->db->bind(':id', $id);
            $this->db->bind(':quant', $quant);
            if ($this->db->execute()){
                return true;
            } else {
                return false;
            };
        }


    }