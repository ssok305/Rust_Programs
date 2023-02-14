<?php

    // Handles user queries

    class User {
        private $db;

        // Constructor
        public function __construct(){
            $this->db = new Database;
        }

        // Register user
        public function register($data){
            // Set query
            $this->db->query('INSERT INTO new_web_users (id, name, email, password) VALUES (:id, :name, :email, :password)');
            
            // Bind values
            $this->db->bind(':id', $data['email']);
            $this->db->bind(':name', $data['name']);
            $this->db->bind(':email', $data['email']);
            $this->db->bind(':password', $data['password']);

            // Execute
            if ($this->db->execute()){
                return true;
            } else {
                return false;
            }

        }

        // Login user
        public function login($email, $password){
            $this->db->query('SELECT * FROM new_web_users WHERE email = :email');
            $this->db->bind(':email', $email);
            $row = $this->db->single();
            $hashed_password = $row->password;
            if(password_verify($password, $hashed_password)){
                return $row;
            } else {
                return false;
            }
        }

        // Find user by email
        public function findUserByEmail($email){
            $this->db->query('SELECT * FROM new_web_users WHERE email = :email');
            
            // Bind value
            $this->db->bind(':email', $email);

            // Get single result
            $row = $this->db->single();

            // Check row
            if ($this->db->rowCount() > 0){
                return true;
            } else {
                return false;
            }
        }

        // Get user by id
        public function getUserById($id){
            $this->db->query('SELECT * FROM new_web_users WHERE id = :id');
            
            // Bind value
            $this->db->bind(':id', $id);

            // Get single result
            $row = $this->db->single();

            return $row;
        }
    }