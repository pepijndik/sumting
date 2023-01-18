<!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->
<a name="readme-top"></a>
<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
[![Latest Release](https://gitlab.fdmci.hva.nl/se-ewa/2022-2023-1/sum-1/-/badges/release.svg)](https://gitlab.fdmci.hva.nl/se-ewa/2022-2023-1/sum-1/-/releases)


[![coverage report](https://gitlab.fdmci.hva.nl/se-ewa/2022-2023-1/sum-1/badges/main/coverage.svg)](https://gitlab.fdmci.hva.nl/se-ewa/2022-2023-1/sum-1/-/commits/main)
[![MIT License][license-shield]][license-url]
[![pipeline status](https://gitlab.fdmci.hva.nl/se-ewa/2022-2023-1/sum-1/badges/main/pipeline.svg)](https://gitlab.fdmci.hva.nl/se-ewa/2022-2023-1/sum-1/-/commits/main)




<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/othneildrew/Best-README-Template">
    <img src="frontend/src/assets/logo.png" alt="Logo" width="250" height="80">
  </a>

<h3 align="center">Admin panel</h3>

  <p align="center">
    The admin panel for SumTing to manage their contrubutions and orders
    <br />
    <a href=""><strong>Explore the docs »</strong></a>
    <br />
    <a href="https://gitlab.fdmci.hva.nl/se-ewa/2022-2023-1/sum-1/-/issues/new">Report Bug</a>
    ·
    <a href="https://gitlab.fdmci.hva.nl/se-ewa/2022-2023-1/sum-1/-/issues/new">Request Feature</a>
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
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
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

[![Sumting admin panel][product-screenshot]](https://sumting.org)

SumTing admin panel is built to manage the orders and contributions of the SumTing platform. It is built with the following technologies:


<p align="right">(<a href="#readme-top">back to top</a>)</p>



### Built With

In this section we list our used Frameworks/Libaries and technologies.
* [![Vue][Vue.js]][Vue-url]
* [![Vue][Spring-url]][spring-url]


<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

Read the <a href="#installation">Installation</a> section to get a local copy up and running.

### Prerequisites

* Docker (https://docs.docker.com/get-docker/)
* Docker-compose (https://docs.docker.com/compose/install/)
### Production Installation

1. Download the latest release from the [releases page]() This will only be a Dockercompose file. With preconfigured settings to deploy our application.
   Run the following command to build the latest version of the application.
   ```sh
   docker-compose build
   ```
2.
   When the installation is done you can start the project with the following command
   ```sh
   docker-compose up -d
   ```
   This will launch the Dockercontainers and start the project on port 8080 You can now access the project frontend on :8080,
   the backend is available on :8083.

5. To stop the project use the following command
   ```sh
   docker-compose down
   ```
6. The final step is to Link these containers to a propertied domain. 
This can be done by using a reverse proxy like Nginx. You can find a tutorial on how to do this here: https://www.digitalocean.com/community/tutorials/how-to-set-up-nginx-server-blocks-virtual-hosts-on-ubuntu-16-04

7. When they are linked you can access the project on your domain.
Test backend: ```http GET *:/8083/help```


<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
### Dev Installation

1. Clone our repository with the tag @latest, and go into the directory
   
 ```sh
   git clone https://gitlab.fdmci.hva.nl/se-ewa/2022-2023-1/sum-1.git
   ```
2. Use the Docker compose command to build up the Containers. Make sure Docker desktop is running!
   ```sh
   docker-compose build
   ```
3. When the installation is done you can start the project with the following command
   ```sh
   docker-compose up -d
   ```
   This will launch the Dockercontainers and start the project on port 8080
4. You can now access the project frontend on localhost:8080, the backend is avaible on localhost:8083 if you are running the project on your local machine

5. To stop the project use the following command
   ```sh
   docker-compose down
   ```
Test backend: localhost:8083/help

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->

## Usage


_For more examples, please refer to the [Documentation](https://example.com)_

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ROADMAP -->
## Roadmap

- [x] Mockup designs
- [x] Create Empty frameworks frontend and backend
- [X] Build Layout
- [X] Connect Database
- [X] Crud Orders
- [ ] Crud Clients
- [X] View Projects

See the [open issues](https://gitlab.fdmci.hva.nl/se-ewa/2022-2023-1/sum-1/-/issues) for a full list of proposed features (and known issues).

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- User stories and methods made by members -->
## User stories and methods made by members

 ## Pepijn

| Userstory | Method |
| ----------- | ----------- |
| As developer i wanted to have a search component | Frontend: Searchabledropdown |
| As admin i wanted to secure my account trough 2fa  | Frontend: VerifyCode, Backend Rest: Authcontroller  
| As admin i wanted a global overview of my projects  | Frontend: ListProjects, Backend Rest: ProjectController  
| As develpor i wanted to have a paginated view for the project table | Frontend: ListProjects & Pagination, Backend Rest: ProjectController & Project interface

 ## Kaan

| Userstory | Method |
| ----------- | ----------- |
| As an admin, I want to be able to see all the order lines in the dasboard, so i have an clear overview on the orderlines.| Frontend: Dashboard,   BarGraph,DashboardServiceApi, Backend: DashboardController, DashboardRepository, Graph model
| As an admin I want to be able to edit my orders so I can fix any mistakes I made | Backend: OrderController
| As an admin I want to be able to delete my orders so i can remove orders from the database. | Backend: OrderController, OrderLineRepository
| As an administrator, I want to be able to see an overview of the statistics of all the orders, so I can estimate how well things are going|Frontend: Dashboard, OrderLineGraph, DashboardServiceApi, Backend: DashboardController, OrderLineRepository


 ## Colin

| Userstory | Method |
| ----------- | ----------- |
| As an admin, I want to be able to select one or multiple projects when creating an order | Frontend: OrderSubItem, OrderTotalCostSubItem & OrderCreate |
| As an admin, I want to be able to search for an order by its client and project  | Frontend: OrderView, Backend Rest: OrderController, OrderRepository |
| As an admin I want a neat application, so that I have a clear overview of the application | Frontend: NavigationDropdownItem, SubDropdownitem, NavigationItem & AdminLayout |


## Dia

| Userstory | Method                |
| ----------- |-----------------------|
| As an admin, I want to be able to edit a Batch, so that I can change any data stored from said batch | Frontend: BatchEdit, BatchApiService Backend: BatchController |
| As an admin, I want to be able to select a project when creating a batch, so that I can see create a batch of that specific project | Frontend: BatchCreate |
| As an admin, I want to be able to view all batches, so that I can search for a specific one | Frontend: BatchView, BatchRow, BatchApiService Backend: BatchController |
| As an admin, I want to have a delete button on each batch when viewing them all, so that I can delete a batch that I don't need anymore | Frontend: BatchView, BatchRow, BatchApiService Backend: BatchController |
| As an admin, I want to see a list of all the order lines from the selected project for the batch that I'm making, so that I can select or deselect individual orders lines | Frontend: BatchCreate, OrderApiService Backend: OrderController |
| As an admin, I want to be able to create a Batch by filling in the Create batch page, so that I have a new batch to work on | Frontend: BatchCreate, BatchApiService Backend: BatchController |
| As an admin, I want be able to input the amount of contributions I want for the batch that I'm creating so that I don't have to select all orders individually | Frontend: BatchCreate |
| As an admin, I want to be able to input an description for the batch that I'm creating, so that the email that goes to the clients has content | Frontend: BatchCreate |


See the [open issues](https://gitlab.fdmci.hva.nl/se-ewa/2022-2023-1/sum-1/-/issues) for a full list of proposed features (and known issues).



<!-- LICENSE -->
## License
This application is licensed under the closed licenses. 

A closed license is a type of license that restricts the use, modification, and distribution of software. It is often used to protect the intellectual property rights of the software creator and ensure that the software is only used in the manner intended by the creator.
In the case of the Sumting Admin Panel application, a closed license would likely be used to prevent unauthorized use or distribution of the software. This means that users of the Sumting Admin Panel would be required to purchase a license in order to use the software and would not be able to modify or distribute the software without permission from us.
A closed license is often accompanied by terms and conditions that outline the specific ways in which the software can be used, as well as any penalties for misuse. These terms and conditions may include provisions for updates and maintenance, as well as limitations on the number of users or devices on which the software can be installed.
Overall, a closed license serves to protect the rights of the software creator and ensure that the software is used responsibly. 

It is important for users of the Sumting Admin Panel to carefully read and understand the terms of the closed license in order to avoid any potential legal issues. See the full license at:
[License](https://gitlab.fdmci.hva.nl/se-ewa/2022-2023-1/sum-1/-/blob/main/LICENSE)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Team 1 - [@Sum-1](ht) - Contact on teams or discord

Project Link: [SumTing admin panel](https://gitlab.fdmci.hva.nl/se-ewa/2022-2023-1/sum-1)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ACKNOWLEDGMENTS -->
## Acknowledgments
* [Choose an Open Source License](https://choosealicense.com)
* [GitHub Emoji Cheat Sheet](https://www.webpagefx.com/tools/emoji-cheat-sheet)
* [Malven's Flexbox Cheatsheet](https://flexbox.malven.co/)
* [Malven's Grid Cheatsheet](https://grid.malven.co/)
* [Img Shields](https://shields.io)
* [Font Awesome](https://fontawesome.com)
* [React Icons](https://react-icons.github.io/react-icons/search)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-url]: https://gitlab.fdmci.hva.nl/se-ewa/2022-2023-1/sum-1/-/project_members
[issues-shield]: https://img.shields.io/gitlab/issues/open/se-ewa/2022-2023-1/sum-1?gitlab_url=https%3A%2F%2Fgitlab.fdmci.hva.nl

[issues-url]: https://img.shields.io/gitlab/issues/open:se-ewa/2022-2023-1/sum-1?gitlab_url=https://gitlab.fdmci.hva.nl/

[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=for-the-badge
[license-url]: https://gitlab.fdmci.hva.nl/se-ewa/2022-2023-1/sum-1/-/blob/main/LICENSE

[product-screenshot]: https://uploads-ssl.webflow.com/61b75f7704ba6b6a2966c019/62ecefa33da29e9074f95292_PlanetVFF.png
[Vue.js]: https://img.shields.io/badge/Vue.js-35495E?style=for-the-badge&logo=vuedotjs&logoColor=4FC08D
[Vue-url]: https://vuejs.org/
[Spring-url]: https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot
[Spring-boot]:https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"
