-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 13, 2021 at 03:41 AM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 8.0.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `spilledcoffee_test`
--

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `order_id` varchar(20) NOT NULL,
  `order_date` date NOT NULL,
  `order_time` time NOT NULL,
  `order_status` varchar(20) NOT NULL DEFAULT 'PENDING',
  `usersId` int(8) NOT NULL,
  `order_quantity` int(8) NOT NULL,
  `order_total` decimal(8,2) NOT NULL,
  `shipping_street` varchar(128) NOT NULL,
  `shipping_city` varchar(30) NOT NULL,
  `shipping_state` varchar(2) NOT NULL,
  `shipping_zipcode` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `order_items`
--

CREATE TABLE `order_items` (
  `order_id` varchar(30) NOT NULL,
  `product_id` int(8) NOT NULL,
  `quantity` int(5) NOT NULL,
  `id` int(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `product_id` int(8) NOT NULL,
  `product_title` varchar(30) NOT NULL,
  `product_description` varchar(128) NOT NULL,
  `quantity` int(8) NOT NULL,
  `price` decimal(8,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`product_id`, `product_title`, `product_description`, `quantity`, `price`) VALUES
(1, 'Breakfast Blend', '16oz, whole bean. Medium roast. Classic flavor, balanced profile. Hints of chocolate.', 125, '13.90'),
(2, 'French Roast', '16 oz, whole bean. Dark roast. Chocolaty, slightly smokey.', 86, '14.50'),
(3, 'Colombian', '16 oz, whole bean. Medium roast. Complex and bright. Hints of fruit.', 94, '14.50'),
(4, 'Hazelnut Creme', '16 oz, whole bean. Light roast. Sweet and bright with hazelnut flavor.', 111, '15.20'),
(5, 'African Roots', '16 oz, whole bean. Medium roast. Bright and complex with notes of fruit.', 92, '14.50'),
(6, 'French Vanilla', '16 oz, whole bean. Light Roast. Sweet and smooth with vanilla flavor.', 85, '13.90');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `usersId` int(8) NOT NULL,
  `usersName` varchar(128) NOT NULL,
  `usersEmail` varchar(128) NOT NULL,
  `usersUid` varchar(128) NOT NULL,
  `usersPwd` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`usersId`, `usersName`, `usersEmail`, `usersUid`, `usersPwd`) VALUES
(2, 'BobbyBoy', 'bob@gmail.com', 'BobbyBoy', '$2y$10$1VeM2lesh66KM0GRgDoNSeuG3JfyRjha5ik9hTVQrFgSHv51caE1G'),
(3, 'JohnJohn', 'john@test.com', 'JohnJohn', '$2y$10$Ebwr2SMFp8q4nvkiCaaZw.WklKzQlm0OPTK9B9xZfJ7yc673/mVu6');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`order_id`);

--
-- Indexes for table `order_items`
--
ALTER TABLE `order_items`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`product_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`usersId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `order_items`
--
ALTER TABLE `order_items`
  MODIFY `id` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `product_id` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `usersId` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
