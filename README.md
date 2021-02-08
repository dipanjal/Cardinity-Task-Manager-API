# Cardinity-Task-Manager-API
A Sample Rest API Project for Cardinity Evaluation Test

[![LinkedIn][linkedin-shield]][linkedin-url]



<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgements">Acknowledgements</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

[![Product Name Screen Shot][product-screenshot]](https://example.com)

This is a sample REST API Service Cardinity Evaluation Test, has been developed in `spring-boot`.
It contains `spring-security` `jwt` `jpa-hibernate`

### Built With
* [Spring Boot](https://spring.io/projects/spring-boot)

### External Too
Since this is a REST API Service, We need an external `REST Client` to `Test` the API endpoints
* [Post Man](https://www.postman.com/)

<!-- GETTING STARTED -->
## Getting Started
We already have a `StartupLoader` to populate the  *Dummy Users* and *Roles*.<br>

* Username and Password in the `application.property` file

```properties
dummy.admin.username=dummy_admin
dummy.user.username=dummy_user
dummy.user2.username=dummy_user2

dummy.admin.password.common=admin
dummy.user.password.common=user
```

* Toggle `Hibernate` DDL Auto Generation by `create` or `update`
```properties
hibernate.hbm2ddl.auto=create
```


### Prerequisites

This is an example of how to list things you need to use the software and how to install them.
* npm
  ```sh
  npm install npm@latest -g
  ```

### Installation

1. Get a free API Key at [https://example.com](https://example.com)
2. Clone the repo
   ```sh
   git clone https://github.com/your_username_/Project-Name.git
   ```
3. Install NPM packages
   ```sh
   npm install
   ```
4. Enter your API in `config.js`
   ```JS
   const API_KEY = 'ENTER YOUR API';
   ```


<!-- CONTACT -->
## Contact

Dipanjal Maitra [@linkedin](https://www.linkedin.com/in/dipanjalmaitra/) - dipanjalmaitra@gmail.com





<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/dipanjalmaitra/
[product-screenshot]: images/screenshot.png
