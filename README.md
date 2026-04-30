# Cafe Andorid App

## Table of contents
* [General info](#general-info)
* [Screenshots](#screenshots)
* [Functionality](#functionality)
* [Technologies](#technologies)
* [Setup](#setup)
* [License](#license)

## General info

Café App is a modern mobile application designed to enhance the coffee ordering experience. Users can easily browse a dynamic menu, view detailed product information, customize their orders, and place them through a smooth and intuitive interface.

## Screenshots

<img src="images/login.jpeg" width="250"> <img src="images/signup.jpeg" width="250">
<img src="images/forgot_password.jpeg" width="250"> 
<img src="images/enter_code.jpeg" width="250">
<img src="images/home_screen.jpeg" width="250"> 
<img src="images/see_all.jpeg" width="250"> 
<img src="images/details.jpeg" width="250"> 
<img src="images/cart.jpeg" width="250"> 
<img src="images/settings.jpeg" width="250"> 

## Functionality

* User Authentication:
  - A secure authentication system that enables users to create new accounts and log in efficiently, ensuring their data and activity are safely managed.

* Password Recovery via OTP:
   - Users can recover their accounts through a secure OTP (One-Time Password) verification process, providing a reliable and user-friendly way to reset forgotten passwords.

* Menu Browsing by Categories:
  - The application provides a well-structured menu divided into clear categories, allowing users to quickly explore available items, view details, and make selections without friction.

* Cart Management:
  - Users can easily add items to the cart, update quantities, or remove products with a seamless and responsive experience. The cart dynamically calculates totals and reflects changes in real time to ensure accuracy and convenience.
 
* Payment Gateway Integration (Paymob):
  - The app integrates with the Paymob payment gateway to support secure and smooth online transactions, offering users a trusted checkout experience.

## Technologies

#### Languages:
- Kotlin 

#### User interface structure:
- Jetback Compose

#### Architecture patterns:
- MVI, I choose mvi for better sperations of concern. each layer can handle it's purpose efficiency. model is data layer which contains business logic, view is ui layer and it's responsability for render ui only and the last is intent. intent which means user action on the screen.

#### Libraries:
- Constraint Layout: flexible and responsive UI design system for complex layouts.
- Hilt:              dependency injection library for simpler and scalable code management.
- DataStore:         modern data storage solution for handling key-value data efficiently.
- Retrofit:          type-safe HTTP client for seamless API communication.
- Coil:              lightweight image loading library optimized for Android.
- Paging 3:          efficient pagination library for loading large datasets smoothly.

## Setup

- To run this project, install it by download or clone.

#### System requirements
- Android Studio Panda 2 | 2025.3.2 or above
- Minimum sdk v24
- Target sdk v36
- Compile sdk v36

## License

```html
All Rights Reserved Licence 

Copyright (c) 2026 Ahmed Zaki

This software and its associated source code, documentation, and any related files are the exclusive intellectual property of the author, Ahmed Zaki. All rights are fully reserved.
No part of this software may be copied, modified, reproduced, distributed, sublicensed, or used in any form without prior written permission from the author.

This includes, but is not limited to:

Using the code in personal or commercial projects
Modifying or adapting the source code
Redistributing the software in original or altered form
Publishing, selling, or sharing the code publicly or privately
Using portions of the code in other applications or repositories

Any unauthorized use of this software is strictly prohibited and may result in legal action.
This software is provided solely for viewing purposes unless explicit permission is granted by the author.
