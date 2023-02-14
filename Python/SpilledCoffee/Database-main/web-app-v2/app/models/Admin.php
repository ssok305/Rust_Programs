<?php

    // Handles admin page queries

    class Admin {

        private $db;

        // Constructor
        public function __construct(){
            $this->db = new Database;
        }

        // Get total items sold, 2 year default
        public function getTotalItemsSold($start = '2020-01-01', $end = '2021-12-31', $source = 'internal'){
            if ($source == 'internal') {
                $this->db->query('SELECT SUM(order_quantity) as total_quantity
                                    FROM new_customer_orders
                                    WHERE ordered_at >= :start and ordered_at <= :end and order_status = "PENDING"
                                ;');
            } else if ($source == 'external') {
                $this->db->query('SELECT SUM(order_quantity) as total_quantity
                                    FROM new_external_orders
                                    WHERE ordered_at >= :start and ordered_at <= :end and order_status = "PENDING"
                                ;');
            }
            $this->db->bind(':start', $start);
            $this->db->bind(':end', $end);
            $row = $this->db->single();
            return $row;
        }

        // Get top 10 products, 2 year default
        public function getTop10Products($start = '2020-01-01', $end = '2021-12-31', $source = 'internal'){
            if ($source == 'internal') {
                $this->db->query('SELECT new_order_items.product_id,
                                    SUM(new_order_items.quantity) as total_quantity
                                    FROM new_order_items
                                    RIGHT JOIN new_customer_orders on
                                        new_customer_orders.order_id = new_order_items.order_id
                                    WHERE new_customer_orders.ordered_at >= :start and new_customer_orders.ordered_at <= :end and new_customer_orders.order_status = "PENDING"
                                    GROUP BY new_order_items.product_id
                                    ORDER BY total_quantity DESC
                                    LIMIT 10
                                ;');
            } else if ($source == 'external') {
                $this->db->query('SELECT product_id,
                                    SUM(order_quantity) as total_quantity
                                    FROM new_external_orders
                                    WHERE ordered_at >= :start and ordered_at <= :end and order_status = "PENDING"
                                    GROUP BY product_id
                                    ORDER BY total_quantity DESC
                                    LIMIT 10
                                ;');
            }
            $this->db->bind(':start', $start);
            $this->db->bind(':end', $end);
            $results = $this->db->resultSet();
            return $results;
        }

        // Get top 25 products, 2 year default
        public function getTop25Products($start = '2020-01-01', $end = '2021-12-31', $source = 'internal'){
            if ($source == 'internal') {
                $this->db->query('SELECT new_order_items.product_id,
                                    SUM(new_order_items.quantity) as total_quantity
                                    FROM new_order_items
                                    RIGHT JOIN new_customer_orders on
                                        new_customer_orders.order_id = new_order_items.order_id
                                    WHERE new_customer_orders.ordered_at >= :start and new_customer_orders.ordered_at <= :end and new_customer_orders.order_status = "PENDING"
                                    GROUP BY new_order_items.product_id
                                    ORDER BY total_quantity DESC
                                    LIMIT 25
                                ;');
            } else if ($source == 'external') {
                $this->db->query('SELECT product_id,
                                    SUM(order_quantity) as total_quantity
                                    FROM new_external_orders
                                    WHERE ordered_at >= :start and ordered_at <= :end and order_status = "PENDING"
                                    GROUP BY product_id
                                    ORDER BY total_quantity DESC
                                    LIMIT 25
                                ;');
            }
            $this->db->bind(':start', $start);
            $this->db->bind(':end', $end);
            $results = $this->db->resultSet();
            return $results;
        }

        // Get top 10 customers, 2 year default
        public function getTop10Customers($start = '2020-01-01', $end = '2021-12-31', $source = 'internal'){
            if ($source == 'internal') {
                $this->db->query('SELECT user_id,
                                    SUM(order_quantity) as total_quantity
                                    FROM new_customer_orders
                                    WHERE ordered_at >= :start and ordered_at <= :end and order_status = "PENDING"
                                    GROUP BY user_id
                                    ORDER BY total_quantity DESC
                                    LIMIT 10
                                ;');
            } else if ($source == 'external') {
                $this->db->query('SELECT user_id,
                                    SUM(order_quantity) as total_quantity
                                    FROM new_external_orders
                                    WHERE ordered_at >= :start and ordered_at <= :end and order_status = "PENDING"
                                    GROUP BY user_id
                                    ORDER BY total_quantity DESC
                                    LIMIT 10
                                ;');
            }
            $this->db->bind(':start', $start);
            $this->db->bind(':end', $end);
            $results = $this->db->resultSet();
            return $results;
        }

        // Get top 25 customers, 2 year default
        public function getTop25Customers($start = '2020-01-01', $end = '2021-12-31', $source = 'internal'){
            if ($source == 'internal') {
                $this->db->query('SELECT user_id,
                                    SUM(order_quantity) as total_quantity
                                    FROM new_customer_orders
                                    WHERE ordered_at >= :start and ordered_at <= :end and order_status = "PENDING"
                                    GROUP BY user_id
                                    ORDER BY total_quantity DESC
                                    LIMIT 25
                                ;');
            } else if ($source == 'external') {
                $this->db->query('SELECT user_id,
                                    SUM(order_quantity) as total_quantity
                                    FROM new_external_orders
                                    WHERE ordered_at >= :start and ordered_at <= :end and order_status = "PENDING"
                                    GROUP BY user_id
                                    ORDER BY total_quantity DESC
                                    LIMIT 25
                                ;');
            }
            $this->db->bind(':start', $start);
            $this->db->bind(':end', $end);
            $results = $this->db->resultSet();
            return $results;
        }

    }