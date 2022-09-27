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
[![Contributors][contributors-shield]][contributors-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]




<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/othneildrew/Best-README-Template">
    <img src="frontend/src/assets/logo.png" alt="Logo" width="250" height="80">
  </a>

<h3 align="center">Admin panel</h3>

  <p align="center">
    The admin panel for sumting to manage their contrubutions and orders
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

Sumting admin panel is build to manage the orders and contributions of the sumting platform. It is build with the following technologies:


<p align="right">(<a href="#readme-top">back to top</a>)</p>



### Built With

In this section is a list of the used Frameworks/Libaries and technologies.
* [![Vue][Vue.js]][Vue-url]
* [![Vue][Spring-url]][spring-url]


<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

Read the <a href="#installation">Installation</a> section to get a local copy up and running.

### Prerequisites

* Docker (https://docs.docker.com/get-docker/)
* Docker-compose (https://docs.docker.com/compose/install/)

### Installation

1. Clone our repository with the tag @latest
   
 ```sh
   git clone https://gitlab.fdmci.hva.nl/se-ewa/2022-2023-1/sum-1.git
   ```

2. Use the MakeFile command to install the project as production ready
   ```sh
   make setup.production
   ```
3. When the installation is done you can start the project with the following command
   ```sh
   make adminpanel.start
   ```
   This will launch the Dockercontainer and start the project on port 8080
4. You can now access the project on localhost:8080 if you are running the project on your local machine

5. To stop the project use the following command
   ```sh
   make adminpanel.stop
   ```
   
<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage


_For more examples, please refer to the [Documentation](https://example.com)_

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ROADMAP -->
## Roadmap

- [x] Mockup designs
- [x] Create Empty frameworks frontend and backend
- [ ] Build Layout
- [ ] Connect Database

See the [open issues](https://gitlab.fdmci.hva.nl/se-ewa/2022-2023-1/sum-1/-/issues) for a full list of proposed features (and known issues).

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Team 1 - [@Sum-1](ht) - Contact on teams or discord

Project Link: [sumting admin panel](https://gitlab.fdmci.hva.nl/se-ewa/2022-2023-1/sum-1)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

Use this space to list resources you find helpful and would like to give credit to. I've included a few of my favorites to kick things off!

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
[contributors-shield]: https://img.shields.io/github/contributors/othneildrew/Best-README-Template.svg?style=for-the-badge
[contributors-url]: https://github.com/othneildrew/Best-README-Template/graphs/contributors
[stars-shield]: https://img.shields.io/github/stars/othneildrew/Best-README-Template.svg?style=for-the-badge
[stars-url]: https://github.com/othneildrew/Best-README-Template/stargazers
[issues-shield]: https://img.shields.io/github/issues/othneildrew/Best-README-Template.svg?style=for-the-badge
[issues-url]: https://github.com/othneildrew/Best-README-Template/issues
[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=for-the-badge
[license-url]: https://github.com/othneildrew/Best-README-Template/blob/master/LICENSE.txt

[product-screenshot]: https://uploads-ssl.webflow.com/61b75f7704ba6b6a2966c019/62ecefa33da29e9074f95292_PlanetVFF.png
[Vue.js]: https://img.shields.io/badge/Vue.js-35495E?style=for-the-badge&logo=vuedotjs&logoColor=4FC08D
[Vue-url]: https://vuejs.org/
[Spring-url]: https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot
[Spring-boot]:https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"
